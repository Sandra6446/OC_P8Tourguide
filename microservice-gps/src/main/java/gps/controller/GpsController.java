package gps.controller;

import com.jsoniter.output.JsonStream;
import gps.service.GpsService;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class GpsController {

    @Autowired
    private GpsService gpsService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @RequestMapping("/getUserLocation")
    public String getLocation(@RequestParam UUID userId) {
        VisitedLocation visitedLocation = gpsService.getUserLocation(userId);
        return JsonStream.serialize(visitedLocation.location);
    }

}
