package it.antanysavage.sm.player.swt.players;

import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.Composite;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.swt.views.WebUrlComposite;

public class WeatherPlayer extends WebUrlPlayer{
	
	private static Logger logger = LoggerFactory.getLogger(WeatherPlayer.class);
	
	private static final String WEATHER_URL = "html/weather/weather2.html";

	public WeatherPlayer(IMaster playerManager, Composite composite) {
		super(playerManager, composite);
		URL url = getClass().getClassLoader().getResource(WEATHER_URL);
		try {
			((WebUrlComposite) composite).setLang(LocaleManager.getLocale().getLanguage());
			((WebUrlComposite) composite).navigateTo(url.toURI().toString(), getPlayerMaster().getScreenManager().getPlayer().getWeatherLatlng());
			
		} catch (URISyntaxException e) {
			logger.error("error occurred ", e);
		}		
	}
	
	@Override
	public void setMedia(Media media) {
		super.setMedia(media);
		//setWeatherLatlng(getPlayerMaster().getScreenManager().getPlayer().getWeatherLatlng());
	}

	public void setWeatherLatlng(String weatherLatlng) {		
		((WebUrlComposite) composite).setCoords(weatherLatlng);	
	}	

}
