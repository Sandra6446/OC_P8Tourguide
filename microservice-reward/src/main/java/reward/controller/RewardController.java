package reward.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reward.service.RewardService;

import java.util.UUID;

@RestController
public class RewardController {

    @Autowired
    RewardService rewardService;

    /**
     * Gets the home page
     *
     * @return The string "Welcome to microservice-reward!"
     */
    @RequestMapping("/")
    public String index() {
        return "Welcome to microservice-reward!";
    }

    /**
     * Gets the user's reward points corresponding to an attraction
     *
     * @param attractionId : The id of the concerned location
     * @param userId       : The id of the concerned user
     * @return A ResponseEntity with a json string containing the number of reward points
     */
    @RequestMapping("/getRewards")
    public ResponseEntity<String> getRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
        int rewardPoints = rewardService.getRewardPoints(attractionId, userId);
        return ResponseEntity.ok(JsonStream.serialize(rewardPoints));
    }

}
