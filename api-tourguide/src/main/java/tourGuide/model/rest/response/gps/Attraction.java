package tourGuide.model.rest.response.gps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents an attraction
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Attraction extends Location {

    /**
     * The name of the attraction
     */
    private String attractionName;
    /**
     * The city of the attraction
     */
    private String city;
    /**
     * The state of the attraction
     */
    private String state;
    /**
     * The id of the attraction
     */
    private UUID attractionId;

}
