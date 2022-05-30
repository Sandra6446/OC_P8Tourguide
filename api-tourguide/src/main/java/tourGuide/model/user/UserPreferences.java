package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import tourGuide.model.user.dto.UserPreferencesDto;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {

    private int attractionProximity = Integer.MAX_VALUE;
    private CurrencyUnit currency = Monetary.getCurrency("USD");
    private Money lowerPricePoint = Money.of(0, currency);
    private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);
    private int tripDuration = 1;
    private int ticketQuantity = 1;
    private int numberOfAdults = 1;
    private int numberOfChildren = 0;

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
