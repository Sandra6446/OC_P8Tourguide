package trip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {

    private final Logger logger = LoggerFactory.getLogger(TripService.class);

    private final TripPricer tripPricer = new TripPricer();

    /**
     * Gets the providers corresponding to user's preferences
     *
     * @param apiKey        : The api key
     * @param attractionId  : The attraction id
     * @param adults        : The number of adults
     * @param children      : The number of children
     * @param nightsStay    : The trip duration
     * @param rewardsPoints : The reward points corresponding to this trip
     * @return A list of Provider
     */
    public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
        logger.debug("getPrice");
        return tripPricer.getPrice(apiKey,attractionId,adults,children,nightsStay,rewardsPoints);
    }
}
