package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.rest.response.gps.Attraction;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.model.user.dto.UserPreferencesDto;
import tourGuide.service.TourGuideService;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TourGuideService tourGuideService;
    private User user;

    @BeforeClass
    public static void setup() {
        InternalTestHelper.setInternalUserNumber(1);
    }

    @Before
    public void setupPerTest() {
        user = tourGuideService.getAllUsers().get(0);
    }

    @Test
    public void getLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getLocation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(JsonStream.serialize(user.getLastVisitedLocation().getLocation().getLatitude())))
                .andExpect(jsonPath("$.longitude").value(JsonStream.serialize(user.getLastVisitedLocation().getLocation().getLongitude())));

        //Test with an unknown user
        mockMvc.perform(MockMvcRequestBuilders.get("/getLocation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", "Username inexistant"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getNearbyAttractions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getNearbyAttractions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        //Test with an unknown user
        mockMvc.perform(MockMvcRequestBuilders.get("/getNearbyAttractions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", "Username inexistant"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRewards() throws Exception {
        UserReward userReward = new UserReward(user.getLastVisitedLocation(), new Attraction(), 5);
        user.setUserRewards(Collections.singletonList(userReward));
        mockMvc.perform(MockMvcRequestBuilders.get("/getRewards")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonStream.serialize(user.getUserRewards())));

        //Test with an unknown user
        mockMvc.perform(MockMvcRequestBuilders.get("/getRewards")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", "Username inexistant"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTripDeals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonStream.serialize(user.getTripDeals())));

        //Test with an unknown user
        mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", "Username inexistant"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePreferences() throws Exception {
        UserPreferencesDto userPreferencesDto = new UserPreferencesDto(null, "", 50, 1000, 7, 4, 2, 2);
        mockMvc.perform(MockMvcRequestBuilders.put("/updatePreferences")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", user.getUserName())
                        .content(String.valueOf(userPreferencesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripDuration").value(7))
                .andExpect(jsonPath("$.attractionProximity").value(Integer.MAX_VALUE));

        //Test with an unknown user
        mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userName", "updatePreferences")
                        .content(String.valueOf(userPreferencesDto)))
                .andExpect(status().isNotFound());
    }
}