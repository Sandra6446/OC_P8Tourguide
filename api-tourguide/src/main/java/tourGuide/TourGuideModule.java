package tourGuide;

import com.jsoniter.spi.JsoniterSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Configuration
public class TourGuideModule {

	@Bean
	public void jsonStreamConfigure() {
        JsoniterSpi.registerTypeDecoder(Date.class, iter -> Date.parse(iter.readString()));
	}
	
}
