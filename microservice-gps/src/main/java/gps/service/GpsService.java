package gps.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
public class GpsService {

    private final GpsUtil gpsUtil = new GpsUtil();

    public VisitedLocation getUserLocation(UUID userId) throws ParseException {
        return gpsUtil.getUserLocation(userId);
    }

    public List<Attraction> getAttractions() {
        return gpsUtil.getAttractions();
    }
}
