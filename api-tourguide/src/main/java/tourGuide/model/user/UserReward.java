package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.VisitedLocation;

import javax.validation.constraints.NotNull;

/**
 * Represents a user's reward
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserReward {

    /**
     * The visited location corresponding to the reward
     */
    @NotNull
    private VisitedLocation visitedLocation;
    /**
     * The attraction corresponding to the reward
     */
    @NotNull
    private Attraction attraction;
    /**
     * The points number
     */
    private int rewardPoints;

}
