package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tourGuide.exception.NotFoundException;
import tourGuide.model.rest.response.gps.VisitedLocation;
import tourGuide.model.rest.response.trip.Provider;
import tourGuide.model.user.User;
import tourGuide.model.user.dto.UserPreferencesDto;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TourGuideService tourGuideService;

    /**
     * Get the last location of a user
     *
     * @param userName : The concerned username
     * @return A json string with a Location
     * @see tourGuide.model.rest.response.gps.Location
     */
    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return JsonStream.serialize(visitedLocation.getLocation());
    }

    /**
     * Get the 5 nearest attractions of a user's location
     *
     * @param userName : The concerned username
     * @return A json string with the 5 nearest attractions of the user's last location
     * @see tourGuide.model.rest.response.gps.Attraction
     */
    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return JsonStream.serialize(tourGuideService.getNearByAttractions(getUser(userName), visitedLocation).get());
    }

    /**
     * Get the reward points of a user
     *
     * @param userName : The concerned username
     * @return A json string with user rewards
     * @see tourGuide.model.user.UserReward
     */
    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {
        return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }

    /**
     * Get trips matching the user's preferences
     *
     * @param userName : The concerned username
     * @return A json string with a list of trip provider
     * @see Provider
     */
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) throws ExecutionException, InterruptedException {
        List<Provider> providers = tourGuideService.getTripDeals(getUser(userName)).get();
        return JsonStream.serialize(providers);
    }

    /**
     * Update user's preferences
     *
     * @param userName           : The concerned username
     * @param userPreferencesDto : The new preferences
     * @return A json string with the new preferences
     * @see tourGuide.model.user.UserPreferences
     */
    @PutMapping("/updatePreferences")
    public String updatePreferences(@RequestParam String userName, @RequestBody UserPreferencesDto userPreferencesDto) {
        User user = getUser(userName);
        tourGuideService.updatePreferences(user, userPreferencesDto);
        return JsonStream.serialize(user.getUserPreferences());
    }

    /**
     * Get a user from a userName
     *
     * @param userName : The concerned username
     * @return A user
     */
    private User getUser(String userName) throws IllegalArgumentException {
        User user = tourGuideService.getUser(userName);
        if (user != null) {
            return user;
        } else {
            logger.error(String.format("UserName %s not found", userName));
            throw new NotFoundException(userName);
        }
    }
}