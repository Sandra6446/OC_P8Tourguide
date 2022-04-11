package trip;

import com.jsoniter.spi.JsoniterSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class TripModule {

	@Bean
	public void jsonStreamConfigure() {
		JsoniterSpi.registerTypeEncoder(UUID.class, (obj, stream) -> stream.writeVal(obj.toString()));
	}
	
}
