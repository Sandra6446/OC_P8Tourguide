package gps;

import com.jsoniter.spi.JsoniterSpi;
import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Configuration
public class GpsModule {
	
	@Bean
	public GpsUtil getGpsUtil() {
		Locale.setDefault(Locale.ENGLISH);
		return new GpsUtil();
	}

	@Bean
	public void jsonStreamConfigure() {
		JsoniterSpi.registerTypeEncoder(UUID.class, (obj, stream) -> stream.writeVal(obj.toString()));
		JsoniterSpi.registerTypeEncoder(Date.class, (obj, stream) -> stream.writeVal(obj.toString()));
	}
	
}
