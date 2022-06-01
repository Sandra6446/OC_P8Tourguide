package tourGuide.model.rest.response.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a location visited by a user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocation {

    /**
     * The user's id
     */
    private UUID userId;
    /**
     * The visited location
     */
    private Location location;
    /**
     * The date of the visit
     */
    private Date timeVisited;

}
