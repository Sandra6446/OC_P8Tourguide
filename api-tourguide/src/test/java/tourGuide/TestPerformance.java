package tourGuide;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
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
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestPerformance {

    /*
     * A note on performance improvements:
     *
     *     The number of users generated for the high volume tests can be easily adjusted via this method:
     *
     *     		InternalTestHelper.setInternalUserNumber(100000);
     *
     *
     *     These tests can be modified to suit new solutions, just as long as the performance metrics
     *     at the end of the tests remains consistent.
     *
     *     These are performance metrics that we are trying to hit:
     *
     *     highVolumeTrackLocation: 100,000 users within 15 minutes:
     *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
     *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */

    @Autowired
    private GpsClient gpsClient;
    @Autowired
    private RewardsService rewardsService;
    @Autowired
    private TripClient tripClient;

    private final Logger logger = LoggerFactory.getLogger(TestPerformance.class);

    @Ignore
    @Test
    public void highVolumeTrackLocation() throws ExecutionException, InterruptedException {
        // Users should be incremented up to 100,000, and test finishes within 15 minutes
        InternalTestHelper.setInternalUserNumber(1000);
        TourGuideService tourGuideService = new TourGuideService(gpsClient, rewardsService,tripClient);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<User> allUsers = tourGuideService.getAllUsers();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<CompletableFuture<VisitedLocation>> futures = new ArrayList<>();
        for (User user : allUsers) {
            user.clearVisitedLocations();
            futures.add(tourGuideService.trackUserLocation(user));
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        all.get();
        logger.info("Number of CompletableFutures : " + futures.size());
        for (User user : allUsers) {
            assertTrue(user.getVisitedLocations().size() > 0);
        }
        stopWatch.stop();

        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

    @Ignore
    @Test
    public void highVolumeGetRewards() throws ExecutionException, InterruptedException {
        // Users should be incremented up to 100,000, and test finishes within 20 minutes
        InternalTestHelper.setInternalUserNumber(100000);
        TourGuideService tourGuideService = new TourGuideService(gpsClient, rewardsService,tripClient);

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        Attraction attraction = gpsClient.readAttractions().get(0);
        List<User> allUsers = tourGuideService.getAllUsers();

        for (User user : allUsers) {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
        }

        StopWatch stopWatch = new StopWatch();
        logger.info("Start watching");
        stopWatch.start();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (User user : allUsers) {
            futures.add(CompletableFuture.runAsync(() -> {
                rewardsService.calculateRewards(user);
            }, executorService));
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        all.get();
        logger.info("Number of CompletableFutures : " + futures.size());
        for (User user : allUsers) {
            assertTrue(user.getUserRewards().size() > 0);
        }
        stopWatch.stop();

        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}
