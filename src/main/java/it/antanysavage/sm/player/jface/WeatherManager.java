package it.antanysavage.sm.player.jface;

import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.swt.views.WebUrlComposite;
import it.antanysavage.sm.player.util.Init;

public class WeatherManager extends ApplicationWindow {

	private static final Logger logger = LoggerFactory.getLogger(WeatherManager.class);	
	private static final String WEATHER_URL = "html/weather/gmap.html";
	private static final MessageFormat mf = new MessageFormat("{0,number,##.##},{1,number,##.##}", Locale.US);

	private Player                   player;
	private WebUrlComposite webUrlComposite;
	private String latlng;

	public WeatherManager(Player theplayer) {
		super( null );
		this.player = theplayer;
		this.latlng = theplayer.getWeatherLatlng();
	}
	
	public Player getPlayer() {
		return player;
	}
	

	@Override
	public boolean close() {
		if ( ! StringUtils.equalsIgnoreCase(player.getWeatherLatlng(), this.latlng)) {			 
			player.setWeatherLatlng(this.latlng);
		}		
		getShell().setVisible(false);
		return false;
	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setText( 
				LocaleManager.getText( LocaleManager.APP_TITLE ) + " | Weather"
				);
		getShell().setImage(Player.LOGO_IMAGE );

		webUrlComposite = new WebUrlComposite(parent, SWT.NONE);
		
		URL url = getClass().getClassLoader().getResource(WEATHER_URL);
		try {
			webUrlComposite.navigateTo(url.toURI().toString(), player.getWeatherLatlng() );
		} catch (URISyntaxException e) {
			logger.error("error occurred ", e);
		}
		
		new CustomFunction (this, webUrlComposite.getBrowser(), "theJavaFunction");

		parent.setSize(400, 400);

		return parent;
	}
	
	public void setCoords(String weatherLatlng) {
		logger.info("(lat,lng) : " + weatherLatlng);
		webUrlComposite.setCoords(weatherLatlng);
	}

	public static void main(String[] args) {
		Init.init();

		WeatherManager wwin = new WeatherManager(null);
		wwin.setBlockOnOpen(true);		
		wwin.open();
		Display.getCurrent().dispose();
	}

	// Called by JavaScript
	class CustomFunction extends BrowserFunction {

		private WeatherManager wm;

		CustomFunction (WeatherManager wm, Browser browser, String name) {
			super (browser, name);
			this.wm = wm;
		}

		public Object function (Object[] args) {
			logger.info("(lat,lng) : " + args[0]);
			if ( StringUtils.isNotEmpty((String) args[0]) )
				wm.setLatlng((String)args[0]);
			return null;
		}
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;		
	}

}
