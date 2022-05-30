package tourGuide.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourGuide.client.GpsClient;
import tourGuide.client.TripClient;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.Location;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.rest.response.trip.Provider;
import tourGuide.model.user.NearByAttraction;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;
import tourGuide.model.user.dto.UserPreferencesDto;
import tourGuide.tracker.Tracker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TourGuideService {
    private final Logger logger = LoggerFactory.getLogger(TourGuideService.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(60);
    public Tracker tracker;
    boolean testMode = true;

    @Autowired
    private GpsClient gpsClient;
    @Autowired
    private RewardsService rewardsService;
    @Autowired
    private TripClient tripClient;

    @Value("${tracking}")
    private boolean tracking;

    public TourGuideService(GpsClient gpsClient, RewardsService rewardsService, TripClient tripClient) {
        this.rewardsService = rewardsService;
        this.gpsClient = gpsClient;
        this.tripClient = tripClient;
        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            initializeInternalUsers();
            logger.debug("Finished initializing users");
        }
        if (tracking) {
            tracker = new Tracker(this);
            addShutDownHook();
        } else {
            logger.info("Tracker disabled");
        }
    }

    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }

    public VisitedLocation getUserLocation(User user) throws ExecutionException, InterruptedException {
        logger.debug("TourGuideService.getUserLocation");
        return (user.getVisitedLocations().size() > 0 ?
                user.getLastVisitedLocation() :
                trackUserLocation(user).get());
    }

    public User getUser(String userName) {
        return internalUserMap.get(userName);
    }

    public List<User> getAllUsers() {
        return internalUserMap.values().stream().collect(Collectors.toList());
    }

    public void addUser(User user) {
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }

    public CompletableFuture<List<Provider>> getTripDeals(User user) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("TourGuideService.getTripDeals");
            List<Provider> providers = tripClient.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                    user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
            user.setTripDeals(providers);
            return providers;
        }, executorService);
    }

    public CompletableFuture<VisitedLocation> trackUserLocation(User user) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("TourGuideService.trackUserLocation");
            VisitedLocation visitedLocation = gpsClient.readVisitedLocation(user.getUserId());
            user.addToVisitedLocations(visitedLocation);
            CompletableFuture.runAsync(() -> rewardsService.calculateRewards(user));
            return visitedLocation;
        }, executorService);
    }

    public CompletableFuture<List<NearByAttraction>> getNearByAttractions(User user, VisitedLocation visitedLocation) {
        logger.debug("TourGuideService.getNearbyAttractions");
        return CompletableFuture.supplyAsync(() -> {
            List<Attraction> attractions = gpsClient.readAttractions();
            List<NearByAttraction> nearByAttractions = new ArrayList<>();
            for (Attraction attraction : attractions) {
                nearByAttractions.add(new NearByAttraction(attraction.getAttractionName(), attraction.getLatitude(), attraction.getLongitude(), visitedLocation.getLocation(), rewardsService.getDistance(attraction, visitedLocation.getLocation()), rewardsService.getRewardPoints(attraction, user)));
            }
            return nearByAttractions.stream().sorted(Comparator.comparing(NearByAttraction::getProximityInMiles)).limit(5).collect(Collectors.toList());
        }, executorService);
    }

    public Hashtable<String, Location> getRecentLocations() {
        List<User> allUsers = getAllUsers();
        Hashtable<String, Location> recentLocations = new Hashtable<>();
        allUsers.forEach(user -> recentLocations.put(user.getUserId().toString(), user.getLastVisitedLocation().getLocation()));
        return recentLocations;
    }

    public void updatePreferences(User user, UserPreferencesDto userPreferencesDto) {
        user.updateUserPreferences(userPreferencesDto);
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }

    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/
    private static final String tripPricerApiKey = "test-server-api-key";
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    private final Map<String, User> internalUserMap = new HashMap<>();

    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    private void generateUserLocationHistory(User user) {
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
