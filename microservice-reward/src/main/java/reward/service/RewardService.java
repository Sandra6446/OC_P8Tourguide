package reward.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardService {

    private final Logger logger = LoggerFactory.getLogger(RewardService.class);

    private final RewardCentral rewardCentral = new RewardCentral();

    /**
     * Gets the user's reward points corresponding to an attraction
     *
     * @param attractionId : The id of the concerned location
     * @param userId       : The id of the concerned user
     * @return An integer corresponding to the number of reward points
     */
    public int getRewardPoints(UUID attractionId, UUID userId) {
        logger.debug("getRewardPoints");
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }
}
