package it.antanysavage.sm.player.bundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class LocaleManager {

	private static Logger logger = LoggerFactory.getLogger(LocaleManager.class);

	private static String BUNDLE_NAME = "bundle/labels";

	private static Locale LOCALE  ;//= Locale.getDefault();

	private static  ResourceBundle RESOURCE_BUNDLE ;//= ResourceBundle.getBundle(BUNDLE_NAME, locale);
	private static  ResourceBundle RESOURCE_BUNDLE_DFLT = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());

	

	public static synchronized String getText(String key) throws MissingResourceException {
		if ( RESOURCE_BUNDLE == null ) {
			if ( LOCALE == null ) {
				LOCALE = Locale.getDefault();
			}
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, LOCALE);
		}
		
		try {
			if ( RESOURCE_BUNDLE.containsKey( key ) )
				return RESOURCE_BUNDLE.getString(key);
			
			return RESOURCE_BUNDLE_DFLT.getString(key);
			
		} catch (MissingResourceException e) {
			logger.error("missing key [" + key +"]");
			return key;
		}
	}



	public static void changeLocale( String localeString) {
		logger.info("changing locale ..");
		try {
			LocaleManager.LOCALE = new Locale(localeString);
			LocaleManager.RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, LocaleManager.LOCALE);
		} catch (Exception e) {
			logger.error("error changing locale to " + localeString, e);
		}
		logger.info("locale changed to " + LocaleManager.RESOURCE_BUNDLE.getLocale().getLanguage() );
	}

	public static Locale getLocale() {
		return LOCALE;
	}

	/*
	 * ========================================================================
	 * 									KEYS
	 * ========================================================================
	 */

	public static final String  APP_TITLE                                   = "app.title"; 
    
    
	// menu keys                                                          
	public static final String  APP_MENU_FILE                                 = "app.menu.file";
	public static final String  APP_MENU_FILE_EXIT                            = "app.menu.file.exit";
	public static final String  APP_MENU_FILE_SAVE                            = "app.menu.file.save";
	public static final String  APP_MENU_FILE_PREFS                           = "app.menu.file.prefs";
                                                                        
	public static final String  APP_MENU_SEQUENCE                             = "app.menu.sequence";
	public static final String  APP_MENU_SEQUENCE_MANAGER                     = "app.menu.sequence.manager";
	public static final String  APP_MENU_SEQUENCE_MANAGER_TOOLTIP             = "app.menu.sequence.manager.tooltip";
                                                                        
	public static final String  APP_MENU_HELP                                 = "menu.help";
	public static final String  APP_MENU_HELP_ABOUT                           = "menu.help.about";
	public static final String  APP_MENU_HELP_MANUAL                           = "menu.help.manual";
	
	
                                                                        
	public static final String APP_GROUP_SEQUENCES                        = "app.video.sequence.group.sequences";
                                                                        
	public static final String APP_GROUP_VIDEOS_SEQUENCE                  = "app.video.sequence.group.videos.sequence";
                                                                        
	public static final String APP_GROUP_SCREEN                           = "app.group.screen";
                                                                        
	public static final String APP_GROUP_SCREEN_LOCK                      = "app.group.screen.lock"                      ;
	public static final String APP_GROUP_SCREEN_FADE                      = "app.group.screen.fade";
	public static final String APP_GROUP_SCREEN_VIEW                      = "app.group.screen.view"                      ;
                                                                        
	public static final String APP_GROUP_SCREEN_SIZE                      = "app.group.screen.size"                      ;
	public static final String APP_GROUP_SCREEN_SIZE_WIDTH                = "app.group.screen.size.width"                ;
	public static final String APP_GROUP_SCREEN_SIZE_HEIGHT               = "app.group.screen.size.height"               ;
                                                                        
	public static final String APP_GROUP_SCREEN_LOCATION                  = "app.group.screen.location"                  ;
	public static final String APP_GROUP_SCREEN_LOCATION_LEFT             = "app.group.screen.location.left"             ;
	public static final String APP_GROUP_SCREEN_LOCATION_TOP              = "app.group.screen.location.top"              ;
	public static final String APP_GROUP_SCREEN_APPLY_MODIFIES            = "app.group.screen.apply.modifies";
	public static final String APP_GROUP_SCREEN_REFER                     = "app.group.screen.refer";
	public static final String APP_GROUP_SCREEN_FONT                      = "app.group.screen.font";
	public static final String APP_GROUP_SCREEN_FONT_TIME                 = "app.group.screen.font.time";
	public static final String APP_GROUP_SCREEN_FONT_DATE                 = "app.group.screen.font.date";
	public static final String APP_GROUP_SCREEN_WATCH_BACKGROUND          = "app.group.screen.watch.background";
	public static final String APP_GROUP_SCREEN_WATCH_BACKGROUND_IMAGE    = "app.group.screen.watch.background.image";
	
                                                                        
	public static final String APP_GROUP_ACTIVATION                       = "app.group.activation"                       ;
	public static final String APP_GROUP_ACTIVATION_TIMING                = "app.group.activation.timing";
	public static final String APP_GROUP_ACTIVATION_TIMING_ALLDAY         = "app.group.activation.timing.allday"         ;
	public static final String APP_GROUP_ACTIVATION_TIMING_TIMED          = "app.group.activation.timing.timed"          ;
	public static final String APP_GROUP_ACTIVATION_TIMING_TIMED_FROM     = "app.group.activation.timing.timed.from"     ;
	public static final String APP_GROUP_ACTIVATION_TIMING_TIMED_TO       = "app.group.activation.timing.timed.to"       ;
	public static final String APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE       = "app.group.activation.when.not.active";
	public static final String APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_BLACK = "app.group.activation.when.not.active.black" ;
	public static final String APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_WATCH = "app.group.activation.when.not.active.watch" ;
	
	public static final String APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_IMAGE = "app.group.activation.when.not.active.image" ;
	
	public static final String APP_GROUP_ACTIVATION_TIMING_ACTIVE         = "app.group.activation.timing.active" ;
                                                                        
	public static final String APP_GROUP_SEQUENCE                         = "app.group.sequence"                         ;
	public static final String APP_GROUP_SEQUENCE_CHOOSE                  = "app.group.sequence.choose"                  ;
	public static final String APP_GROUP_SEQUENCE_NUMBER_OF_VIDEO         = "app.group.sequence.number.of.video"         ;
	public static final String APP_GROUP_SEQUENCE_DURATION                = "app.group.sequence.duration"                ;
	public static final String APP_GROUP_SEQUENCE_NAME                    = "app.group.sequence.name"                    ;
	public static final String APP_VIDEO_SEQUENCE_GROUP_SEQUENCES         = "app.video.sequence.group.sequences"         ;
	public static final String APP_VIDEO_SEQUENCE_GROUP_VIDEOS_SEQUENCE   = "app.video.sequence.group.videos.sequence"   ;
	
                                                                        
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_0          = "app.video.sequence.table.column.0"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_1          = "app.video.sequence.table.column.1"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_2          = "app.video.sequence.table.column.2"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_3          = "app.video.sequence.table.column.3"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_4          = "app.video.sequence.table.column.4"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_5          = "app.video.sequence.table.column.5"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_6          = "app.video.sequence.table.column.6"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_7          = "app.video.sequence.table.column.7"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_8          = "app.video.sequence.table.column.8"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_9          = "app.video.sequence.table.column.9"          ;
	public static final String APP_VIDEO_SEQUENCE_TABLE_COLUMN_10         = "app.video.sequence.table.column.10"          ;
	
	public static final String APP_VIDEO_SEQUENCE_BUTTON_UP_TOOLTIP       = "app.video.sequence.button.up.tooltip";
	public static final String APP_VIDEO_SEQUENCE_BUTTON_DOWN_TOOLTIP     = "app.video.sequence.button.down.tooltip";

	public static final String MODEL_SEQUENCE_DESCRIPTION       = "model.sequence.description";
	public static final String MODEL_SEQUENCE_MEDIA_DESCRIPTION = "model.sequence.media.description";	
	public static final int    MODEL_SEQUECE_MEDIA_NUMBERS      = 5; 
	public static final String MODEL_SEQUECE_MEDIA_0            = "model.sequece.media.0";
	public static final String MODEL_SEQUECE_MEDIA_1            = "model.sequece.media.1";
	public static final String MODEL_SEQUECE_MEDIA_2            = "model.sequece.media.2";
	public static final String MODEL_SEQUECE_MEDIA_3            = "model.sequece.media.3";
	public static final String MODEL_SEQUECE_MEDIA_4            = "model.sequece.media.4";
	// weather
	public static final String MODEL_SEQUECE_MEDIA_5            = "model.sequece.media.5";
	// ftp video  
	public static final String MODEL_SEQUECE_MEDIA_6            = "model.sequece.media.6";
	// ftp img
	public static final String MODEL_SEQUECE_MEDIA_7            = "model.sequece.media.7";
	public static final String MODEL_SEQUECE_MEDIA_8            = "model.sequece.media.8";

	public static final String SM_MENU_SEQUENCE         = "sm.menu.sequence";
	public static final String SM_MENU_SEQUENCE_NEW     = "sm.menu.sequence.new";
	public static final String SM_MENU_SEQUENCE_SAVE    = "sm.menu.sequence.save";
	public static final String SM_MENU_SEQUENCE_OPEN    = "sm.menu.sequence.open";
	public static final String SM_MENU_SEQUENCE_QUIT    = "sm.menu.sequence.quit";
	public static final String SM_MENU_SEQUENCE_SAVE_AS = "sm.menu.sequence.save.as";
	
	public static final String SM_MENU_MEDIA              = "sm.menu.media";
	public static final String SM_MENU_MEDIA_NEW          = "sm.menu.media.new";		
	public static final String SM_MENU_MEDIA_DELETE       = "sm.menu.media.delete";	
	public static final String SM_MENU_MEDIA_CHOOSE_FILE  = "sm.menu.media.choose.file";	
	public static final String SM_MENU_MEDIA_ADD          = "sm.menu.media.add";	
	public static final String SM_MENU_MEDIA_ADDED        = "sm.menu.media.added";	
	public static final String SM_MENU_MEDIA_CLOSE        = "sm.menu.media.close";
	public static final String SM_MENU_MEDIA_DAY_CALENDAR = "sm.menu.media.day.calendar";
	public static final String SM_MENU_MEDIA_DAY_DATES    = "sm.menu.media.day.dates";
	public static final String SM_MENU_MEDIA_DAY_START    = "sm.menu.media.day.start";	
	public static final String SM_MENU_MEDIA_DAY_END      = "sm.menu.media.day.end";	
	public static final String SM_MENU_MEDIA_MODIFY       = "sm.menu.media.modify";
	public static final String SM_MENU_MEDIA_CHANGE       = "sm.menu.media.change";
	
	
	public static final String WARNINGS_ARE_YOU_SURE       = "warnings.are.you.sure";
	
	public static final String ERRORS_SEQUENCE_IN_USE        = "errors.sequence.in.use";	
	public static final String ERRORS_SEQUENCE_NO_SELECTION  = "errors.sequence.no.selection";	
	public static final String ERRORS_VIDEO_NO_SELECTION     = "errors.video.no.selection";	
	public static final String ERRORS_SEQUENCE_LOADING       = "errors.sequence.loading";
	public static final String ERRORS_SEQUENCE_NOT_FOUND     = "errors.sequence.not.found";	
	public static final String ERRORS_TIME                   = "errors.time";	
	public static final String ERRORS_NO_SELECTED_FILE_PHOTO =  "errors.no.selected.file.photo";	
	public static final String ERRORS_NO_SELECTED_FILE_VIDEO =  "errors.no.selected.file.video";
	public static final String ERRORS_INSERT_MEDIA           = "errors.insert.media";	
	public static final String ERRORS_SEQUENCE_EMPTY         = "errors.sequence.empty"       ;
	public static final String ERRORS_PLAYER_ALREADY_PLAYING = "errors.player.already.playing";	
	public static final String ERRORS_NO_QUICKTIME           = "errors.no.quicktime";
	public static final String ERRORS_DATE                   = "errors.date";

	public static final String ERRORS_NO_SELECTED_MEDIA_TYPE = "errors.no.selected.media_type";

	

	

}
