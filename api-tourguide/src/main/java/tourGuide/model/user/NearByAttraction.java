package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.rest.response.gps.Location;

/**
 * Represents an attraction near a user
 */
@AllArgsConstructor
@Getter
@Setter
public class NearByAttraction {

    /**
     * The name of the attraction
     */
    private String name;
    /**
     * The latitude of the attraction
     */
    private double latitude;
    /**
     * The longitude of the attraction
     */
    private double longitude;
    /**
     * The user's location
     */
    private Location userLocation;
    /**
     * The distance between the user and the attraction
     */
    private double proximityInMiles;
    /**
     * The reward points corresponding to the attraction
     */
    private int rewardPoints;

}
