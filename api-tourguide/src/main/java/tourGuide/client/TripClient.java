package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.model.rest.response.trip.Provider;

import java.util.List;
import java.util.UUID;

/**
 * Makes the link with microservice trip
 */
@FeignClient(name = "microservice-trip", url = "${feign.client.url.tripUrl}")
public interface TripClient {

    /**
     * Gets the providers corresponding to user's preferences
     *
     * @param apiKey        : The api key
     * @param attractionId  : The attraction id
     * @param adults        : The number of adults
     * @param children      : The number of children
     * @param nightsStay    : The trip duration
     * @param rewardsPoints : The reward points corresponding to this trip
     * @return A list of Provider
     * @see tourGuide.model.user.UserPreferences
     * @see tourGuide.model.user.UserReward
     */
    @RequestMapping(value = "/getPrice", produces = "application/json")
    List<Provider> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId, @RequestParam int adults, @RequestParam int children, @RequestParam int nightsStay, @RequestParam int rewardsPoints);

}
