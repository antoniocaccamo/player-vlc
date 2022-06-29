package it.antanysavage.sm.player.prefs;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

import java.text.MessageFormat;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ScreenManagerPrefPage extends PreferencePage {
	
	

	public static final MessageFormat APP_PLAYER_I_SIZE_WIDTH 				= new MessageFormat("app.player.{0}.size.width");

	public static final MessageFormat APP_PLAYER_I_SIZE_HEIGHT 				= new MessageFormat("app.player.{0,number,#}.size.height");

	public static final MessageFormat APP_PLAYER_I_LOCATION_X 				= new MessageFormat("app.player.{0,number,#}.location.x");

	public static final MessageFormat APP_PLAYER_I_LOCATION_Y 				= new MessageFormat("app.player.{0,number,#}.location.y");

	public static final MessageFormat APP_PLAYER_I_SEQUENCE_FILE 			= new MessageFormat("app.player.{0,number,#}.sequence.file");

	public static final MessageFormat APP_PLAYER_I_SCREEN_LOCK 				= new MessageFormat("app.player.{0,number,#}.screen.lock");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_FADE  			= new MessageFormat("app.player.{0,number,#}.screen.fade");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_ALPHA 			= new MessageFormat("app.player.{0,number,#}.screen.alpha");

	public static final MessageFormat APP_PLAYER_I_SCREEN_VIEW 				= new MessageFormat("app.player.{0,number,#}.screen.view");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_FONT_TIME 		= new MessageFormat("app.player.{0,number,#}.screen.font.time");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_FONT_DATE 		= new MessageFormat("app.player.{0,number,#}.screen.font.date");

	public static final MessageFormat APP_PLAYER_I_ALL_DAY 					= new MessageFormat("app.player.{0,number,#}.activation.allDay");

	public static final MessageFormat APP_PLAYER_I_TIMED 					= new MessageFormat("app.player.{0,number,#}.activation.timed");

	public static final MessageFormat APP_PLAYER_I_TIMED_FROM 				= new MessageFormat("app.player.{0,number,#}.activation.timed.from");

	public static final MessageFormat APP_PLAYER_I_TIMED_TO 				= new MessageFormat("app.player.{0,number,#}.activation.timed.to");	

	public static final MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_WATCH	= new MessageFormat("app.player.{0,number,#}.not.active.watch");

	public static final MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_BLACK	= new MessageFormat("app.player.{0,number,#}.not.active.black");
	
	public static final MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_IMAGE	= new MessageFormat("app.player.{0,number,#}.not.active.image");

	public static final MessageFormat APP_PLAYER_I_WATCH_IMAGE_FILE      	= new MessageFormat("app.player.{0,number,#}.watch.image.file");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_COLOR_TIME     	= new MessageFormat("app.player.{0,number,#}.screen.color.time");
	
	public static final MessageFormat APP_PLAYER_I_SCREEN_COLOR_DATE    	= new MessageFormat("app.player.{0,number,#}.screen.color.date");
	
	public static final MessageFormat APP_PLAYER_I_WEATHER_LATLNG  			= new MessageFormat("app.player.{0,number,#}.weather.latlng");
	
	public static final MessageFormat APP_PLAYER_I_LOOP_NUMBER              = new MessageFormat("app.player.{0,number,#}.loop.number");
	
	public static final String COLOR_SEPARATOR = "|";

	private int index;

	private Player player;

	public ScreenManagerPrefPage(Player player, int i) {
		super("Antonio...#" +i);
		setTitle("ScreenManagerPrefPage #"+i);
		this.index = i;
		this.player = player;
	}

	@Override
	protected Control createContents(Composite arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
