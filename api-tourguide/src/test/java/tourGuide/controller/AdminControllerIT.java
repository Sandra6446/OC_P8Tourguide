package tourGuide.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.output.JsonStream;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.rest.response.gps.Location;
import tourGuide.model.user.User;
import tourGuide.service.TourGuideService;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminControllerIT {

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
        User user = tourGuideService.getAllUsers().get(0);
        String UUID = user.getUserId().toString();
        Location currentLocation = user.getLastVisitedLocation().getLocation();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getAllCurrentLocations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ObjectMapper om = new ObjectMapper();
        Hashtable<String, Location> current = om.readValue(response, new TypeReference<Hashtable<String, Location>>() {});

        assertEquals(JsonStream.serialize(currentLocation.getLatitude()),JsonStream.serialize(current.get(UUID).getLatitude()));
        assertEquals(JsonStream.serialize(currentLocation.getLongitude()),JsonStream.serialize(current.get(UUID).getLongitude()));
    }
}