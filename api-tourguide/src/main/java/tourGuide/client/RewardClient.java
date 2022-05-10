package tourGuide.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "microservice-reward", url = "${feign.client.url.rewardUrl}")
public interface RewardClient {

    @RequestMapping(value = "/getRewards", produces = "application/json")
    int readRewards(@RequestParam UUID attractionId, @RequestParam UUID userId);

}
