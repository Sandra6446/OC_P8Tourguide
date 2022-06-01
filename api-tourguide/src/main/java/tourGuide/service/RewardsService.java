package tourGuide.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.client.GpsClient;
import tourGuide.client.RewardClient;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.Location;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages rewards
 */
@Service
@RequiredArgsConstructor
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private final Logger logger = LoggerFactory.getLogger(RewardsService.class);
    @Autowired
    private GpsClient gpsClient;
    @Autowired
    private RewardClient rewardClient;
    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    private int attractionProximityRange = 200;

    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }

    /**
     * Calculates user's rewards
     *
     * @param user : The concerned user
     * @see UserReward
     */
    public void calculateRewards(User user) {
        logger.debug("RewardsService.calculateRewards");
        List<Attraction> attractions = gpsClient.readAttractions();

        CopyOnWriteArrayList<VisitedLocation> locations = new CopyOnWriteArrayList<>(user.getVisitedLocations());

        for (VisitedLocation visitedLocation : locations) {
            for (Attraction attraction : attractions) {
                if (user.getUserRewards().stream().filter(r -> r.getAttraction().getAttractionName().equals(attraction.getAttractionName())).count() == 0) {
                    if (nearAttraction(visitedLocation, attraction)) {
                        user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
                    }
                }
            }
        }
    }

    /**
     * Checks if an attraction is in the area of a location
     *
     * @param attraction : The concerned attraction
     * @param location   : The concerned location
     * @return A boolean : true if the attraction is in the area of the location, false otherwise
     */
    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        return getDistance(attraction, location) > attractionProximityRange ? false : true;
    }

    /**
     * Checks if a visited location is near an attraction
     *
     * @param visitedLocation : The concerned location
     * @param attraction      : The concerned attraction
     * @return A boolean : true if visited location is near the attraction, false otherwise
     */
    private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return getDistance(attraction, visitedLocation.getLocation()) > proximityBuffer ? false : true;
    }


    /**
     * Gets the user's reward points corresponding to an attraction
     *
     * @param attraction : The concerned location
     * @param user       : The concerned user
     * @return The number of reward points
     */
    public int getRewardPoints(Attraction attraction, User user) {
        return rewardClient.readRewards(attraction.getAttractionId(), user.getUserId());
    }

    /**
     * Gets the distance in miles between two locations
     *
     * @param loc1 : The first location
     * @param loc2 : The second location
     * @return : The distance in miles between the two locations
     */
    public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

}
