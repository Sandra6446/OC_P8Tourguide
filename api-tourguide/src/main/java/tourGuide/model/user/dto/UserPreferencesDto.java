package tourGuide.model.user.dto;

import com.jsoniter.output.JsonStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents user's trip preferences
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDto {

    /**
     * The proximity required
     */
    private Integer attractionProximity = null;
    /**
     * The currency required
     */
    private String currencyCode = "";
    /**
     * The lower price required
     */
    private Integer lowerPrice = null;
    /**
     * The high price required
     */
    private Integer highPrice = null;
    /**
     * The duration required
     */
    private Integer tripDuration = 1;
    /**
     * The ticket quantity required
     */
    private Integer ticketQuantity = 1;
    /**
     * The number of adults required
     */
    private Integer numberOfAdults = 1;
    /**
     * The number of children required
     */
    private Integer numberOfChildren = 0;

    @Override
    public String toString() {
        return JsonStream.serialize(this);
    }
}
