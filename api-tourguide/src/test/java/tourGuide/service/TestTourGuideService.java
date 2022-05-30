package tourGuide.service;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.client.GpsClient;
import tourGuide.client.TripClient;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.rest.response.trip.Provider;
import tourGuide.model.user.NearByAttraction;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.dto.UserPreferencesDto;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestTourGuideService {

    private Logger logger = LoggerFactory.getLogger(TestTourGuideService.class);

    @Autowired
    private GpsClient gpsClient;
    @Autowired
    private RewardsService rewardsService;
    @Autowired
    private TripClient tripClient;

    private User user;
    private TourGuideService tourGuideService;

    @BeforeClass
    public static void setup() {
        InternalTestHelper.setInternalUserNumber(0);
    }

    @Before
    public void setupPerTest() {
        tourGuideService = new TourGuideService(gpsClient, rewardsService, tripClient);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    }

    @Test
    public void getUserLocation() throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
        assertEquals(visitedLocation.getUserId(), user.getUserId());
    }

    @Test
    public void addUser() {
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        assertEquals(user, tourGuideService.getUser(user.getUserName()));
        assertEquals(user2, tourGuideService.getUser(user2.getUserName()));

        // Test to add an existent userName
        User user3 = new User(UUID.randomUUID(), "jon", "000", "jon3@tourGuide.com");
        tourGuideService.addUser(user3);
        assertEquals(user, tourGuideService.getUser(user3.getUserName()));
    }

    @Test
    public void getAllUsers() {
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        List<User> allUsers = tourGuideService.getAllUsers();

        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void trackUser() throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
        assertEquals(user.getUserId(), visitedLocation.getUserId());
    }

    @Test
    public void getNearbyAttractions() throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
        List<NearByAttraction> nearByAttractions = tourGuideService.getNearByAttractions(user, visitedLocation).get();
        assertEquals(5, nearByAttractions.size());
    }

    @Test
    public void getTripDeals() throws ExecutionException, InterruptedException {
        List<Provider> providers = tourGuideService.getTripDeals(user).get();
        assertEquals(5, providers.size());
    }

    @Test
    public void updatePreferences() {
        UserPreferencesDto userPreferencesDto1 = new UserPreferencesDto(null, null, 50, 1000, 7, 4, 2, 2);
        UserPreferences userPreferences = new UserPreferences(Integer.MAX_VALUE,Monetary.getCurrency("USD"),Money.of(50,Monetary.getCurrency("USD")),Money.of(1000,Monetary.getCurrency("USD")),7,4,2,2);
        tourGuideService.updatePreferences(user,userPreferencesDto1);
        assertEquals(userPreferences,user.getUserPreferences());



        UserPreferencesDto userPreferencesDto2 = new UserPreferencesDto(3000, "EUR", null, null, null, null, null, null);
        userPreferences.setAttractionProximity(3000);
        userPreferences.setCurrency(Monetary.getCurrency("EUR"));
        userPreferences.setLowerPricePoint(Money.of(50,Monetary.getCurrency("EUR")));
        userPreferences.setHighPricePoint(Money.of(1000,Monetary.getCurrency("EUR")));
        tourGuideService.updatePreferences(user,userPreferencesDto2);
        assertEquals(userPreferences,user.getUserPreferences());
    }
}
