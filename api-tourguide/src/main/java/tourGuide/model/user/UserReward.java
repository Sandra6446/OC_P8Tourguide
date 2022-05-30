package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.rest.response.gps.VisitedLocation;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserReward {

    @NotNull
    private VisitedLocation visitedLocation;
    @NotNull
    private Attraction attraction;
    private int rewardPoints;

}
