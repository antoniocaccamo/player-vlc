package it.antanysavage.sm.player.prefs;


import java.text.MessageFormat;
import java.util.Locale;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;


public class PlayerPrefPage extends PreferencePage {

	private Player player;

	public static final String      APP_COMPUTER = "app.computer";
	
	public static final String      APP_SEND_ALL_MAIL   = "app.send.all.mail";

	public static final String      APP_ENABLED_LOG     = "app.enabled.log";

	public static final String      APP_LOCALE = "app.locale";

	public static final String          APP_VERSION = "app.version"; 
	public static final String     APP_VERSION_DATE = "app.version.date";
	public static final String     APP_VERSION_INFO = "app.last.update.info";

	public static final String      APP_FTP_REFRESH = "app.ftp.refresh";

	public static final String       APP_WIDTH =  "app.size.width";

	public static final String      APP_HEIGHT = "app.size.height";

	public static final String  APP_LOCATION_X = "app.location.x";

	public static final String  APP_LOCATION_Y = "app.location.y";

	public static final String  APP_MPLAYER_PATH             = "app.mplayer.path";
	public static final String  APP_MPLAYER_OPTIONS          = "app.mplayer.options";
	public static final String  APP_MPLAYER_WAIT_VIDEO_START = "app.mplayer.wait.video.start";


	public static final String APP_TIME_LABEL_RATIO = "app.time.label.ratio";
	public static final String APP_DATE_LABEL_RATIO = "app.date.label.ratio";

	public static final String APP_PLAYER_VIDEO_WINDOWS_NUMBER = "app.player.video.windows.number";

	public static final String APP_UPDATE_SITE = "app.update.site";

	public static final String APP_PLAYER_WEATHER_LATLNG = "app.player.weather.latlng";


	/**
	 * Default value of FTP refresh rate
	 */
	public static final double FTP_REFRESH_DEFAULT = 3.0F;

	

	private Combo languageCombo ;

	public PlayerPrefPage(Player player) {
		super("Antonio...");
		setTitle(LocaleManager.getText(LocaleManager.APP_MENU_FILE_PREFS));
		this.player = player;

	}


	protected Control createContents(Composite parent) {

		parent.getShell().setLocation( player.getShell().getLocation() );
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		IPreferenceStore store = getPreferenceStore();

		Label l = new Label(composite, SWT.NONE);
		l.setText( "Linguaggio");

		this.languageCombo = new Combo(composite, SWT.NONE);
		this.languageCombo.setItems( 
				new String[] {
						"Italiano",
						"English" ,
						"Espanol"
				}
				);
		if ( Locale.ENGLISH.getLanguage().equals( store.contains(APP_LOCALE) ) ) {
			languageCombo.select(1);
		} else {
			if ( "es".equals( store.contains(APP_LOCALE) ) ){
				languageCombo.select(2);
			} else {
				languageCombo.select(0);
			}
		}

		parent.pack();
		return parent;
	}


	/**
	 * Called when user clicks Restore Defaults
	 */
	protected void performDefaults() {
		IPreferenceStore preferenceStore = getPreferenceStore();

		String s = preferenceStore.getString(APP_LOCALE);
		LocaleManager.changeLocale(s);
	}


	@Override
	public boolean performCancel() {
		return false;
	}

}
