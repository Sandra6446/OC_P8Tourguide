package gps.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GpsService {

    private final GpsUtil gpsUtil;

    public GpsService(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }

    public VisitedLocation getUserLocation(UUID userId) {
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);
        return visitedLocation;
    }
}
