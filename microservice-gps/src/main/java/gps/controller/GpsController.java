package gps.controller;

import com.jsoniter.output.JsonStream;
import gps.service.GpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
public class GpsController {

    @Autowired
    private GpsService gpsService;

    @RequestMapping("/")
    public String index() {
        return "Welcome to microservice-gps!";
    }

    @RequestMapping("/getUserLocation")
    public ResponseEntity getLocation(@RequestParam UUID userId) throws ParseException {
        VisitedLocation visitedLocation = gpsService.getUserLocation(userId);
        return ResponseEntity.ok(JsonStream.serialize(visitedLocation));
    }

    @RequestMapping("/getAttractions")
    public ResponseEntity getAttractions() {
        List<Attraction> attractions = gpsService.getAttractions();
        return ResponseEntity.ok(JsonStream.serialize(attractions));
    }

}
