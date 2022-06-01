package gps.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GpsService {

    private final Logger logger = LoggerFactory.getLogger(GpsService.class);

    private final GpsUtil gpsUtil = new GpsUtil();

    /**
     * Gets the last location of a user
     *
     * @param userId : The id of the concerned user
     * @return A VisitedLocation
     */
    public VisitedLocation getUserLocation(UUID userId) {
        logger.debug("getLocation");
        return gpsUtil.getUserLocation(userId);
    }

    /**
     * Gets all known attractions
     *
     * @return A list of Attraction
     */
    public List<Attraction> getAttractions() {
        logger.debug("getAttractions");
        return gpsUtil.getAttractions();
    }
}
