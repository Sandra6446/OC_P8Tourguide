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

    @RequestMapping("/")
    public String index() {
        return "Welcome to microservice-reward!";
    }

    @RequestMapping("/getRewards")
    public ResponseEntity getRewardPoints (@RequestParam UUID attractionId, @RequestParam UUID userId) {
        int rewardPoints = rewardService.getRewardPoints(attractionId,userId);
        return ResponseEntity.ok(JsonStream.serialize(rewardPoints));
    }

}
