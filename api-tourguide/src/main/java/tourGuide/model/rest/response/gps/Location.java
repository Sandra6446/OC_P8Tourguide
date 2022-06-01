package tourGuide.model.rest.response.gps;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a location with a latitude and a longitude
 */
@NoArgsConstructor
@Data
public class Location {

    /**
     * The longitude of the location
     */
    private double longitude;
    /**
     * The latitude of the location
     */
    private double latitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
