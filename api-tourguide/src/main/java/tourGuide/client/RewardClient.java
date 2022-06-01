package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Makes the link with microservice reward
 */
@FeignClient(name = "microservice-reward", url = "${feign.client.url.rewardUrl}")
public interface RewardClient {

    /**
     * Gets the user's rewards corresponding to an attraction
     *
     * @param attractionId : The id of the concerned attraction
     * @param userId       : The id of the concerned user
     * @return : A number of reward points
     */
    @RequestMapping(value = "/getRewards", produces = "application/json")
    int readRewards(@RequestParam UUID attractionId, @RequestParam UUID userId);

}
