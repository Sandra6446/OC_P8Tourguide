package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.VisitedLocation;

import java.util.List;
import java.util.UUID;

/**
 * Makes the link with microservice gps
 */
@FeignClient(name = "microservice-gps", url = "${feign.client.url.gpsUrl}")
public interface GpsClient {

    /**
     * Reads a user's visited location
     *
     * @param userId : The id of the concerned user
     * @return A VisitedLocation
     * @see tourGuide.model.user.User
     */
    @RequestMapping(value = "/getUserLocation", produces = "application/json")
    VisitedLocation readVisitedLocation(@RequestParam UUID userId);

    /**
     * Reads all the attractions
     *
     * @return A list of Attraction
     */
    @RequestMapping(value = "/getAttractions", produces = "application/json")
    List<Attraction> readAttractions();

}
