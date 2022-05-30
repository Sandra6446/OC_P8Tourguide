package tourGuide;

import com.jsoniter.spi.JsoniterSpi;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Configuration
public class TourGuideModule {

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
		JsoniterSpi.registerTypeEncoder(Money.class,(obj, stream) -> stream.writeVal(obj.toString()));
		JsoniterSpi.registerTypeEncoder(Currency.class,(obj, stream) -> stream.writeVal(obj.toString()));
	}
	
}
