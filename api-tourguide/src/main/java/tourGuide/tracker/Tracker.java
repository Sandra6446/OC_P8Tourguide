package tourGuide.tracker;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tourGuide.model.user.User;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.concurrent.*;

/**
 * Represents a tracker
 */
public class Tracker extends Thread {
    private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final TourGuideService tourGuideService;
    private final Logger logger = LoggerFactory.getLogger(Tracker.class);
    private boolean stop = false;

    public Tracker(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
        executorService.submit(this);
    }

    /**
     * Assures to shut down the Tracker thread
     */
    public void stopTracking() {
        stop = true;
        executorService.shutdownNow();
    }

    /**
     * Runs the user's location tracker
     */
    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        while (true) {
            if (Thread.currentThread().isInterrupted() || stop) {
                logger.debug("Tracker stopping");
                break;
            }
            List<User> users = tourGuideService.getAllUsers();
            logger.debug("Begin Tracker. Tracking " + users.size() + " users.");
            stopWatch.start();
            users.forEach(u -> {
                try {
                    tourGuideService.trackUserLocation(u).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            stopWatch.stop();
            logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
            stopWatch.reset();
            try {
                logger.debug("Tracker sleeping");
                TimeUnit.SECONDS.sleep(trackingPollingInterval);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
