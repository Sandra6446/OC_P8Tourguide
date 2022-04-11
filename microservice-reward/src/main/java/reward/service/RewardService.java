package reward.service;

import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardService {

    private final RewardCentral rewardCentral = new RewardCentral();

    public int getRewardPoints(UUID attractionId, UUID userId) {
        return rewardCentral.getAttractionRewardPoints(attractionId,userId);
    }
}
