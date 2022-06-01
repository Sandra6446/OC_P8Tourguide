package tourGuide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.service.TourGuideService;

@RestController
public class HomeController {

    @Autowired
    private TourGuideService tourGuideService;

    /**
     * Gets the home page
     *
     * @return The string "Greetings from TourGuide!"
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

}
