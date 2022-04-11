package tourGuide.model.rest.response.trip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Provider {
    private String name;
    private double price;
    private UUID tripId;

    public Provider(UUID tripId, String name, double price) {
        this.name = name;
        this.tripId = tripId;
        this.price = price;
    }
}
