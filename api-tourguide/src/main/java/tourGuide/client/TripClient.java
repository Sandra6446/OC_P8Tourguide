package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.model.rest.response.trip.Provider;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "microservice-trip", url = "${url.trip}")
public interface TripClient {

    @RequestMapping(value = "/getPrice", produces = "application/json")
    List<Provider> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId, @RequestParam int adults, @RequestParam int children, @RequestParam int nightsStay, @RequestParam int rewardsPoints);

}
