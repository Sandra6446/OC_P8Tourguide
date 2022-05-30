package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.rest.response.gps.Location;

@AllArgsConstructor
@Getter
@Setter
public class NearByAttraction {

    private String name;
    private double latitude;
    private double longitude;
    private Location userLocation;
    private double proximityInMiles;
    private int rewardPoints;

}
