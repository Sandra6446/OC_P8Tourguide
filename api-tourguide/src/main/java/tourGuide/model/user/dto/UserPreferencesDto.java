package tourGuide.model.user.dto;

import com.jsoniter.output.JsonStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDto {

    private Integer attractionProximity = null;
    private String currencyCode = "";
    private Integer lowerPrice = null;
    private Integer highPrice = null;
    private Integer tripDuration = 1;
    private Integer ticketQuantity = 1;
    private Integer numberOfAdults = 1;
    private Integer numberOfChildren = 0;

    /*
    @Override
    public String toString() {
        return new StringJoiner(", ","{","}")
                .add("\"attractionProximity\":" + attractionProximity)
                .add("\"currencyCode\":" + "'" + currencyCode + "'")
                .add("\"lowerPrice\":" + lowerPrice)
                .add("\"highPrice\":" + highPrice)
                .add("\"tripDuration\":" + tripDuration)
                .add("\"ticketQuantity\":" + ticketQuantity)
                .add("\"numberOfAdults\":" + numberOfAdults)
                .add("\"numberOfChildren\":" + numberOfChildren)
                .toString();
    }

     */

    @Override
    public String toString() {
        return JsonStream.serialize(this);
    }
}
