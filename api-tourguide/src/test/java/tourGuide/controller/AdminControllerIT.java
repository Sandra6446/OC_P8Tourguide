package tourGuide.controller;

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
import tourGuide.service.TourGuideService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TourGuideService tourGuideService;

    @BeforeClass
    public static void setup() {
        InternalTestHelper.setInternalUserNumber(10);
    }

    @Test
    public void getAllCurrentLocations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllCurrentLocations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.%s",tourGuideService.getAllUsers().get(2).getUserId().toString())).value(tourGuideService.getAllUsers().get(2).getLastVisitedLocation().getLocation()));
    }
}