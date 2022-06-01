package tourGuide.model.user;

import lombok.Data;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.rest.response.trip.Provider;
import tourGuide.model.user.dto.UserPreferencesDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user
 */
@Data
public class User {
    /**
     * The user's id
     */
    private final UUID userId;
    /**
     * The username
     */
    private final String userName;
    /**
     * The user's phone number
     */
    private String phoneNumber;
    /**
     * The user's email
     */
    private String emailAddress;
    /**
     * The user's latest location
     */
    private Date latestLocationTimestamp;
    /**
     * The user's visited locations
     */
    private List<VisitedLocation> visitedLocations = new ArrayList<>();
    /**
     * The user's rewards
     */
    private List<UserReward> userRewards = new ArrayList<>();
    /**
     * The user's preferences
     */
    private UserPreferences userPreferences = new UserPreferences();
    /**
     * The user's trip deals
     */
    private List<Provider> tripDeals = new ArrayList<>();

    public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Adds a visited location
     *
     * @param visitedLocation : The location to be added
     */
    public void addToVisitedLocations(VisitedLocation visitedLocation) {
        visitedLocations.add(visitedLocation);
    }

    /**
     * Clears visited locations
     */
    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    /**
     * Adds a reward
     *
     * @param userReward : The reward to be added
     */
    public void addUserReward(UserReward userReward) {
        userRewards.add(userReward);
    }

    /**
     * Gets last visited location
     *
     * @return The user's last visited locations
     */
    public VisitedLocation getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }

    /**
     * Update preferences
     *
     * @param userPreferencesDto : The preferences to be updated
     */
    public void updateUserPreferences(UserPreferencesDto userPreferencesDto) {
        this.getUserPreferences().update(userPreferencesDto);
    }
}
