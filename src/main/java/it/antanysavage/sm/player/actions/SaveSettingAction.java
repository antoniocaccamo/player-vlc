package it.antanysavage.sm.player.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.model.ScreenSetting;
import it.antanysavage.sm.player.prefs.PlayerPrefPage;
import it.antanysavage.sm.player.prefs.ScreenManagerPrefPage;
import it.antanysavage.sm.player.util.Utils;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.MessageBox;

public class SaveSettingAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(SaveSettingAction.class );

	private Player smPlayer;

	public SaveSettingAction(Player smPlayer) {
		super( LocaleManager.getText(LocaleManager.APP_MENU_FILE_SAVE) , AS_PUSH_BUTTON);
		this.smPlayer = smPlayer;
	}

	public void run() {
		try {
			File f = new File(Player.PREFS );
			if ( f.exists() ) {
				File fdest = new File(f.getPath() + Utils.debugDate(Calendar.getInstance().getTime()));
				f.renameTo(fdest);
			}
			if ( ! f.exists() && ! f.createNewFile()) {
				MessageBox box = new MessageBox(this.smPlayer.getShell());
				box.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
				box.setMessage("Can't create preference file " + Player.PREFS);
				logger.error("Can't create preference file " + Player.PREFS);
				box.open();
				return;
			}
			if ( ! f.canWrite() ) {
				MessageBox box = new MessageBox(this.smPlayer.getShell());
				box.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
				box.setMessage("Can't write to preference file " + Player.PREFS);	
				logger.error("Can't write to file : " + f.getCanonicalPath() );
				box.open();
				return;
			}

			FileWriter fw = new FileWriter(f );
			StringBuffer sb = new StringBuffer();

			sb.append("######################################## "+ System.getProperty("line.separator"));
			sb.append("#                                      # "+ System.getProperty("line.separator"));
			sb.append("#   AT ADV PLayer preference's file    # "+ System.getProperty("line.separator"));
			sb.append("#                                      # "+ System.getProperty("line.separator"));
			sb.append("######################################## "+ System.getProperty("line.separator") + System.getProperty("line.separator"));	

			sb.append("# Saved @ " + Utils.debugDate(Calendar.getInstance().getTime()) + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Computer name " + System.getProperty("line.separator")  );
			sb.append(PlayerPrefPage.APP_COMPUTER     + " = " + Player.COMPUTER  + System.getProperty("line.separator") + System.getProperty("line.separator"));
						
			sb.append("# Version "+ System.getProperty("line.separator"));                        
			sb.append(PlayerPrefPage.APP_VERSION      + " = " + (Utils.isAnEmptyString(Player.VERSION)      ? "" : Player.VERSION ) + System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_VERSION_DATE + " = " + (Utils.isAnEmptyString(Player.VERSION_DATE) ? "" : Player.VERSION_DATE ) + System.getProperty("line.separator") + System.getProperty("line.separator"));

//			sb.append("# Update site "+ System.getProperty("line.separator"));
//			sb.append(PlayerPrefPage.APP_UPDATE_SITE + " = " + Player.UPDATE_SITE + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : send all mail " + System.getProperty("line.separator")  );
			sb.append(PlayerPrefPage.APP_SEND_ALL_MAIL     + " = " + Player.APP_SEND_ALL_MAIL  + System.getProperty("line.separator") + System.getProperty("line.separator"));

			
			sb.append("# Application : Enabled Log "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_ENABLED_LOG + " = " + smPlayer.isEnabledLog() + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : locale "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_LOCALE + " = " + LocaleManager.getLocale().getLanguage() + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : FTP remote media refresh site (part of hours) "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_FTP_REFRESH + " = " + Player.APP_FTP_REFRESH + System.getProperty("line.separator") + System.getProperty("line.separator"));




			//			sb.append( PlayerPrefPage.APP_TIME_LABEL_RATIO + " = " + Player.APP_TIME_LABEL_RATIO + System.getProperty("line.separator"));
			//			sb.append( PlayerPrefPage.APP_DATE_LABEL_RATIO + " = " + Player.APP_DATE_LABEL_RATIO + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : size "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_WIDTH + " = " + this.smPlayer.getShell().getSize().x + System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_HEIGHT + " = " + this.smPlayer.getShell().getSize().y + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : location "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_LOCATION_X + " = " + this.smPlayer.getShell().getLocation().x + System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_LOCATION_Y + " = " + this.smPlayer.getShell().getLocation().y + System.getProperty("line.separator") + System.getProperty("line.separator"));

			sb.append("# Application : mplayer location and options "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_MPLAYER_PATH    + " = " + Player.MPLAYER_PATH.replace("\\", "/") + System.getProperty("line.separator") );
			sb.append(PlayerPrefPage.APP_MPLAYER_OPTIONS + " = " + Player.MPLAYER_OPTIONS + System.getProperty("line.separator") );
			sb.append(PlayerPrefPage.APP_MPLAYER_WAIT_VIDEO_START + " = " + Player.APP_MPLAYER_WAIT_VIDEO_START + System.getProperty("line.separator") + System.getProperty("line.separator"));
			

			sb.append("# Application : number of players "+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_PLAYER_VIDEO_WINDOWS_NUMBER + " = " + this.smPlayer.playerSetting.size() + System.getProperty("line.separator") + System.getProperty("line.separator") );

			sb.append("# Application : wheather location (lat,long)"+ System.getProperty("line.separator"));
			sb.append(PlayerPrefPage.APP_PLAYER_WEATHER_LATLNG + " = " + smPlayer.getWeatherLatlng() + System.getProperty("line.separator") + System.getProperty("line.separator"));

			for( int i = 0; i < this.smPlayer.playerSetting.size(); i++ ) {

				Integer[] idx = new Integer[1];
				idx[0] = new Integer(i+1);

				ScreenSetting ps = (ScreenSetting) this.smPlayer.playerSetting.get(i);

				sb.append( System.getProperty("line.separator") );
				sb.append("# Player # (" + ( i +1 ) + ") settings "+ System.getProperty("line.separator"));
				sb.append("# ========================= "+ System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : size"+ System.getProperty("line.separator"));								
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SIZE_WIDTH.format(idx) + " = " + ps.getSize().x + System.getProperty("line.separator"));				
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SIZE_HEIGHT.format(idx) + " = " + ps.getSize().y + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : location"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_X.format(idx) + " = " + ps.getLocation().x + System.getProperty("line.separator"));				
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_Y.format(idx) + " = " + ps.getLocation().y + System.getProperty("line.separator") + System.getProperty("line.separator"));
				String s = null ;
				if ( ps.getProgramFile() != null ) {
					s = ps.getProgramFile().getAbsolutePath();
					s = s.replace("\\", "/");
				}

				sb.append("# Player # (" + ( i +1 ) + ") : sequence file"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SEQUENCE_FILE.format(idx) + " = " + ( s == null ? "" : s ) + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : loop number"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_LOOP_NUMBER.format(idx) + " = " +  ps.getLoopNumber() + System.getProperty("line.separator") + System.getProperty("line.separator"));


				sb.append("# Player # (" + ( i +1 ) + ") : screen lock"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_LOCK.format(idx) + " = " + ps.isLock() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : fade"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FADE.format(idx) + " = " + ps.isFade() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : alpha"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_ALPHA.format(idx) + " = " + ps.getAlpha() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : screen visibility"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_VIEW.format(idx) + " = " + ps.isViewScreen() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : font for time"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FONT_TIME.format(idx) + " = " + ps.getTimeLabelFontData().toString() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : color for time"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_COLOR_TIME.format(idx) + " = " 
						+ ps.getTimeLabelFontColor().red   + ScreenManagerPrefPage.COLOR_SEPARATOR 
						+ ps.getTimeLabelFontColor().green + ScreenManagerPrefPage.COLOR_SEPARATOR
						+ ps.getTimeLabelFontColor().blue   
						+ System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : font for date"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FONT_DATE.format(idx) + " = " + ps.getDateLabelFontData().toString() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : color for date"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_COLOR_DATE.format(idx) + " = "
						+ ps.getDateLabelFontColor().red   + ScreenManagerPrefPage.COLOR_SEPARATOR 
						+ ps.getDateLabelFontColor().green + ScreenManagerPrefPage.COLOR_SEPARATOR
						+ ps.getDateLabelFontColor().blue   
						+ System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : all day scheduling"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_ALL_DAY.format(idx) + " = " + ps.isAllDay() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : timed scheduling"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_TIMED.format(idx) + " = " + ps.isTimed() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : timed scheduling : from"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_TIMED_FROM.format(idx) + " = " + (ps.getFrom() == null ? "" : Utils.getTimeAsString(ps.getFrom()) )+ System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : timed scheduling : to"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_TIMED_TO.format(idx) + " = " + ( ps.getTo() == null ? "" : Utils.getTimeAsString(ps.getTo()) )+ System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : when is not active : show black screen"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_BLACK.format(idx) + " = " + ps.isWhenNotActiveBlack() + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : when is not active : show watch"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_WATCH.format(idx) + " = " + ps.isWhenNotActiveWatch() + System.getProperty("line.separator") + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : when is not active : show watch image"+ System.getProperty("line.separator"));
				sb.append(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_IMAGE.format(idx) + " = " + ps.isWhenNotActiveImage() + System.getProperty("line.separator") + System.getProperty("line.separator") + System.getProperty("line.separator"));

				sb.append("# Player # (" + ( i +1 ) + ") : whatch image file"+ System.getProperty("line.separator"));
				sb.append( ScreenManagerPrefPage.APP_PLAYER_I_WATCH_IMAGE_FILE.format(idx) + " = " + (ps.getWatchImageFile() == null ? "" : ps.getWatchImageFile().getAbsolutePath().replace("\\", "/")) + System.getProperty("line.separator") + System.getProperty("line.separator") );

			}

			fw.write( sb.toString() );
			fw.close();
		} catch (IOException e) {
			logger.error("error occurred ", e);
		}
	}

}
