package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import tourGuide.model.user.dto.UserPreferencesDto;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * Represents user's trip preferences
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {

    /**
     * The proximity required
     */
    private int attractionProximity = Integer.MAX_VALUE;
    /**
     * The currency required
     */
    private CurrencyUnit currency = Monetary.getCurrency("USD");
    /**
     * The lower price required
     */
    private Money lowerPricePoint = Money.of(0, currency);
    /**
     * The high price required
     */
    private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);
    /**
     * The duration required
     */
    private int tripDuration = 1;
    /**
     * The ticket quantity required
     */
    private int ticketQuantity = 1;
    /**
     * The number of adults required
     */
    private int numberOfAdults = 1;
    /**
     * The number of children required
     */
    private int numberOfChildren = 0;

    /**
     * Updates the user's preferences
     *
     * @param userPreferencesDto : The new preferences
     */
    public void update(UserPreferencesDto userPreferencesDto) {
        if (userPreferencesDto.getAttractionProximity() != null) {
            this.setAttractionProximity(userPreferencesDto.getAttractionProximity());
        }
        if (userPreferencesDto.getCurrencyCode() != null) {
            if (!userPreferencesDto.getCurrencyCode().isEmpty()) {
                CurrencyUnit currencyUnit = Monetary.getCurrency(userPreferencesDto.getCurrencyCode());
                this.setCurrency(currencyUnit);
                this.setLowerPricePoint(Money.of(this.getLowerPricePoint().getNumber(), currencyUnit));
                this.setHighPricePoint(Money.of(this.getHighPricePoint().getNumber(), currencyUnit));
            }
        }
        if (userPreferencesDto.getLowerPrice() != null) {
            this.setLowerPricePoint(Money.of(userPreferencesDto.getLowerPrice(), currency));
        }
        if (userPreferencesDto.getHighPrice() != null) {
            this.setHighPricePoint(Money.of(userPreferencesDto.getHighPrice(), currency));
        }
        if (userPreferencesDto.getTripDuration() != null) {
            this.setTripDuration(userPreferencesDto.getTripDuration());
        }
        if (userPreferencesDto.getTicketQuantity() != null) {
            this.setTicketQuantity(userPreferencesDto.getTicketQuantity());
        }
        if (userPreferencesDto.getNumberOfAdults() != null) {
            this.setNumberOfAdults(userPreferencesDto.getNumberOfAdults());
        }
        if (userPreferencesDto.getNumberOfChildren() != null) {
            this.setNumberOfChildren(userPreferencesDto.getNumberOfChildren());
        }
    }
}
