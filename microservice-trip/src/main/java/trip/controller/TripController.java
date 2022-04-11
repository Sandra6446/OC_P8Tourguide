package trip.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trip.service.TripService;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

@RestController
public class TripController {

    @Autowired
    TripService tripService;

    @RequestMapping("/")
    public String index() {
        return "Welcome to microservice-trip!";
    }

    @RequestMapping("/getPrice")
    public ResponseEntity getPrice (@RequestParam String apiKey, @RequestParam UUID attractionId, @RequestParam int adults, @RequestParam int children, @RequestParam int nightsStay, @RequestParam int rewardsPoints) {
        List<Provider> providers = tripService.getPrice(apiKey,attractionId,adults,children,nightsStay,rewardsPoints);
        return ResponseEntity.ok(JsonStream.serialize(providers));
    }

}
