package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.service.TourGuideService;

@RestController
public class AdminController {

    @Autowired
    private TourGuideService tourGuideService;

    /**
     * Get a list of every user's most recent location as JSON
     *
     * @return A json string with all userId and their last location
     */
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        return JsonStream.serialize(tourGuideService.getRecentLocations());
    }

}
