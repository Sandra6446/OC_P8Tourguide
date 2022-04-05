package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.model.rest.response.Attraction;
import tourGuide.model.rest.response.VisitedLocation;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "microservice-gps", url = "${url.client}")
public interface GpsClient {

    @RequestMapping(value = "/getUserLocation", produces = "application/json")
    VisitedLocation readVisitedLocation(@RequestParam UUID userId);

    @RequestMapping(value = "/getAttractions", produces = "application/json")
    List<Attraction> readAttractions();

}
