package tourGuide.model.rest.response.trip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a trip
 */
@Data
@NoArgsConstructor
public class Provider {

    /**
     * The name of the trip
     */
    private String name;
    /**
     * The price of the trip
     */
    private double price;
    /**
     * The id of the trip
     */
    private UUID tripId;

}
