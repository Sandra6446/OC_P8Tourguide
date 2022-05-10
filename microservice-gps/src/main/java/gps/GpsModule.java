package gps;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.extra.JdkDatetimeSupport;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Decoder;
import com.jsoniter.spi.Encoder;
import com.jsoniter.spi.JsonException;
import com.jsoniter.spi.JsoniterSpi;
import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		JsoniterSpi.registerTypeEncoder(Date.class, (obj, stream) -> {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
					Locale.ENGLISH);
			Date parsedDate = null;
			try {
				parsedDate = sdf.parse(obj.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat newSdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
			stream.writeVal(newSdf.format(parsedDate));
		});
	}
	
}
