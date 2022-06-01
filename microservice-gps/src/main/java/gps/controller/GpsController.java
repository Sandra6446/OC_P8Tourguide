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

    /**
     * Gets the home page
     *
     * @return The string "Welcome to microservice-gps!"
     */
    @RequestMapping("/")
    public String index() {
        return "Welcome to microservice-gps!";
    }

    /**
     * Gets the last location of a user
     *
     * @param userId : The id of the concerned user
     * @return A ResponseEntity with a json string containing a VisitedLocation
     */
    @RequestMapping("/getUserLocation")
    public ResponseEntity<String> getLocation(@RequestParam UUID userId) throws ParseException {
        VisitedLocation visitedLocation = gpsService.getUserLocation(userId);
        return ResponseEntity.ok(JsonStream.serialize(visitedLocation));
    }

    /**
     * Gets all known attractions
     *
     * @return A ResponseEntity with a json string containing a list of Attraction
     */
    @RequestMapping("/getAttractions")
    public ResponseEntity<String> getAttractions() {
        List<Attraction> attractions = gpsService.getAttractions();
        return ResponseEntity.ok(JsonStream.serialize(attractions));
    }

}
