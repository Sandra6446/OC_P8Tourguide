package tourGuide;

import com.jsoniter.spi.JsoniterSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

@Configuration
public class TourGuideModule {

	@Bean
	public void jsonStreamConfigure() {
		JsoniterSpi.registerTypeEncoder(UUID.class, (obj, stream) -> stream.writeVal(obj.toString()));
		JsoniterSpi.registerTypeEncoder(Date.class, (obj, stream) -> stream.writeVal(obj.toString()));
	}
	
}
