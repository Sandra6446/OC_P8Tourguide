package gps.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GpsService {

    private final GpsUtil gpsUtil = new GpsUtil();

    public VisitedLocation getUserLocation(UUID userId) {
        return gpsUtil.getUserLocation(userId);
    }
}
