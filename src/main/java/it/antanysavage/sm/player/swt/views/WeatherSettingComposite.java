package it.antanysavage.sm.player.swt.views;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.model.ScreenSetting;


public class WeatherSettingComposite extends Composite {
	
	private static final int WEATHEAR_PERIOD = 3 * 60 * 60  * 1000 ; // 3 hours
	
	private static Logger logger = LoggerFactory.getLogger(WeatherSettingComposite.class);
	
	private static final String INDEX = "html/geo.html";
			 
	private Browser browser;
	private String weatherLatLng = ScreenSetting.DEFAULT_WEATHER_LATLNG;
	
	protected Timer weatherTimer = new Timer();

	public WeatherSettingComposite(Composite parent) {
		super(parent, SWT.NONE);
		getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);

		try {

			FillLayout thisLayout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			this.setLayout(thisLayout);

			browser = new Browser(this, SWT.NONE);
			logger.info("set initial weather page to : " + INDEX);
			URL url = getClass().getClassLoader().getResource(INDEX);
			try {
				browser.setUrl(url.toString());
				pack();
			} catch (Exception e) {
				logger.error("error occurred", e);
				e.printStackTrace();
			}
			
			
			
//			browser.addControlListener(new ControlListener() {
//
//				public void controlResized(ControlEvent e) {
//					logger.error("controlResized to " + browser.getSize() );
//					browser.execute("document.getElementById('container').style.width= "+ (browser.getSize().x )   +";");
//					browser.execute("document.getElementById('container').style.height= "+ (browser.getSize().y )   +";");					
//				}
//
//				public void controlMoved(ControlEvent e) {
//				}
//			});
			
			weatherTimer.scheduleAtFixedRate( new WeatherTask(), 500, WEATHEAR_PERIOD);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refreshWeatherConditions(final String latlng ) {
		Display.getDefault().syncExec(
				new Runnable() {					
					public void run() {
						
						if ( browser.execute("refreshWeatherConditions('" + latlng+ "');") ) 	
                            logger.info("calling browser js funtion refreshWeatherConditions('" + latlng+ "') correctly");
                        else
                            logger.error("calling browser js funtion refreshWeatherConditions('" + latlng+ "') with ERROR");
					}
				}
		);
				
	}
	
	public void refresh() {
		Display.getDefault().syncExec(
				new Runnable() {					
					public void run() {
						browser.refresh();					
					}
				}
		);
				
	}


	public void setWeatherLatLng(String weatherLatLng) {
		this.weatherLatLng = weatherLatLng;		
		logger.info("setting weather Lat Lng : " + weatherLatLng);
	}
	
	
	protected class WeatherTask extends TimerTask {
		@Override
		
		public void run() {
			if ( StringUtils.isNotEmpty( StringUtils.trim(weatherLatLng) ) )
				refreshWeatherConditions(weatherLatLng);
		}
	}
	
	
//	public static void main(String[] args) {
//		try {
//
//			InputStream is = Player.class.getClassLoader().getResourceAsStream(Player.LOG4J);
//			Properties p = new Properties();
//			p.load(is);
//			PropertyConfigurator.configure(p);
//
//		} catch (Exception e) {
//			MessageDialog.openError(Display.getDefault().getActiveShell(), 
//					LocaleManager.getText(LocaleManager.APP_TITLE), 					
//					"Can't find log4j configuration"
//					);
//			System.exit(1);
//		}
//		showGUI(); 
//	}

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		WeatherSettingComposite inst = new WeatherSettingComposite(shell);
		
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.setSize(300, 270);
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}


}
