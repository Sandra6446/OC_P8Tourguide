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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private final Logger logger = LoggerFactory.getLogger(RewardsService.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Autowired
    GpsClient gpsClient;
    @Autowired
    RewardClient rewardClient;
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

    public void calculateRewards(User user) {
        logger.debug("RewardsService.calculateRewards");
        List<VisitedLocation> userLocations = user.getVisitedLocations();
        List<Attraction> attractions = gpsClient.readAttractions();

        for(VisitedLocation visitedLocation : userLocations) {
            for(Attraction attraction : attractions) {
                if(user.getUserRewards().stream().filter(r -> r.attraction.getAttractionName().equals(attraction.getAttractionName())).count() == 0) {
                    if(nearAttraction(visitedLocation, attraction)) {
                        user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
                    }
                }
            }
        }
    }

    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        return getDistance(attraction, location) > attractionProximityRange ? false : true;
    }


    private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return getDistance(attraction, visitedLocation.getLocation()) > proximityBuffer ? false : true;
    }


    public int getRewardPoints(Attraction attraction, User user) {
        logger.debug("RewardsService.getRewardPoints");
        return rewardClient.readRewards(attraction.getAttractionId(), user.getUserId());
    }

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
