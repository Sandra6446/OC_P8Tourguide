package tourGuide.model.rest.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tourGuide.model.rest.CustomDateDeserializer;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocation {

    private UUID userId;

    private Location location;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date timeVisited;

}
