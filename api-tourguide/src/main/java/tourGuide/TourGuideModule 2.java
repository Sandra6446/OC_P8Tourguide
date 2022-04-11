package tourGuide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.service.RewardsService;

import java.util.Locale;

@Configuration
public class TourGuideModule {
	
	@Bean
	public GpsUtil getGpsUtil() {
		Locale.setDefault(Locale.ENGLISH);
		return new GpsUtil();
	}
	
}
