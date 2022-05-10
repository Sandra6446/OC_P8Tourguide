package tourGuide.model.rest.response.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocation {

    private UUID userId;
    private Location location;
    private Date timeVisited;

}
