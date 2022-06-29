package it.antanysavage.sm.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.exolab.castor.util.LocalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.actions.AboutAction;
import it.antanysavage.sm.player.actions.ComputerNameAction;
import it.antanysavage.sm.player.actions.EnableLogAction;
import it.antanysavage.sm.player.actions.ExitAction;
import it.antanysavage.sm.player.actions.ManualAction;
import it.antanysavage.sm.player.actions.NewsManagerAction;
import it.antanysavage.sm.player.actions.ProgramManagerAction;
import it.antanysavage.sm.player.actions.SaveSettingAction;
import it.antanysavage.sm.player.actions.SendAllMailAction;
import it.antanysavage.sm.player.actions.SendTestEmailAction;
import it.antanysavage.sm.player.actions.WeatherManagerAction;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.installation.Check;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.WeatherManager;
import it.antanysavage.sm.player.model.ScreenSetting;
import it.antanysavage.sm.player.prefs.PlayerPrefPage;
import it.antanysavage.sm.player.prefs.ScreenManagerPrefPage;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.model.Resource;
import it.antanysavage.sm.player.util.Init;
import it.antanysavage.sm.player.util.Utils;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

//import quicktime.QTException;
public class Player extends ApplicationWindow {

    public static String SETTINGS_PATH = System.getProperty("user.home") + File.separator + ".at" + File.separator + "player";

    public static String VERSION = "1.3";

    public static String VERSION_DATE = "20140601";

    public static final String PREFS = "prefs.properties";

    public static final String LOGO = "images/logo.jpg";

    private static final String BCKG = "images/background.jpg";

    public static final String LOG4J = "log4j/log4j.properties";

    private static final String RUNNING_MODE = "RUNNING_MODE";

    private static final String DEMO = "images/at.demo.jpeg";

    public static boolean MPLAYER_MODE = false;
    private static String MPLAYER_MODE_STRING = "MPLAYER";

	//	protected static final int VIEW_DEMO_MILLIS = 2000; 
    protected static final int APP_WIDTH = 350;

    protected static final int APP_HEIGHT = 550;

    protected static final int APP_LOCATION_X = 100;

    protected static final int APP_LOCATION_Y = 200;

    public static int APP_TIME_LABEL_RATIO;

    public static int APP_DATE_LABEL_RATIO;

    public static String NEWS_MODE = "NEWS";
    
    public static boolean ENABLE_NEWS = false;

    public static final String[] VIDEO_FILTERS = {
        "*.avi;*.flv;*.mov;*.mp4;*.mpeg;*.mpg;*.wmv",
        "*.flv",
        "*.avi",
        "*.mov",
        "*.mp4",
        "*.mpeg",
        "*.mpg",
        "*.wmv",
        "*.*"
    };

    public static final String[] PHOTO_FILTERS = {
        "*.bmp;*.jpeg;*.jpg;*.png;*.tiff",
        "*.bmp",
        "*.jpeg",
        "*.jpg",
        "*.png",
        "*.tiff",
        "*.*"
    };

    public static final String REG_KEY = "AT ADV Player";
    public static final String REG_KEY_VALUE = "xxxxxxxxxxxxx";

    private Point size = null;

    private Point location = null;

    private int numberOfVideoWindows = 1;

    public CTabFolder tabFolder = null;

    private ScreenManagerComposite videoWindowManager = null;

	//private SequenceManager sequenceManager ;//= new SequenceManager( this ) ;
    private StatusLineManager slm = new StatusLineManager();

    protected HashMap<String, Program> sequencesLoaded = new HashMap<String, Program>();

    public ArrayList<ScreenSetting> playerSetting = new ArrayList<ScreenSetting>();

    static private ArrayList<ScreenManagerComposite> listOfScreensManager = new ArrayList<ScreenManagerComposite>();

    private ProgramManager programManager;

    private MenuManager news_menu;

    private NewsManager newsManager;

    private boolean enabledLog;

    private WeatherManager weatherManager;

	//	public PreferenceStore preferenceStore;
    //
    //	public PreferenceManager preferenceManager;
    //
    //	private PreferenceNode preferenceNode;
	//	private StatusLineManager slm = new StatusLineManager();
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    public static String MPLAYER_PATH = null;

    public static String MPLAYER_OPTIONS = "";

    public static final long TIMING_PERIOD = 2000;  // 2sec	

    public static final boolean WEATHER_ENABLED = false;

    public static final String SEQUENCE_FILE_EXT = ".xseq";
    public static final String SEQUENCE_FILE_EXT_FILTER = "*.xseq";

    private static final String NO_CHECK = "NO_CHECK";

    public static final String FTP_MODE_CHECK = "FTP_MODE";

    public static String VERSION_INFO = null;

    public static Image LOGO_IMAGE = null;

    public static Image BCKG_IMAGE = null;

    public static Image DEMO_IMAGE = null;

    public static Media DEMO_IMAGE_MEDIA = null;

    public static boolean DEMO_MODE = false;

    public static double APP_FTP_REFRESH;

    public static boolean FTP_ENABLED = false;

    public static String UPDATE_SITE;

    public static File REMOTE_VIDEO_PATH;

    public static File REMOTE_PROGRAM_PATH;

    private static File UPDATE_PATH;

    public static boolean FTP_MODE = false;

    private String weatherLatlng;
    
    public static String COMPUTER = null;

	public static boolean APP_SEND_ALL_MAIL;

	public static int APP_MPLAYER_WAIT_VIDEO_START;    

    public Player() {
        super(null);
        
        new NativeDiscovery().discover();
        LibXUtil.initialise();

		//		setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.MIN ); 
        loadPreferences(Player.PREFS);

        //		sequenceManager = new SequenceManager( this ) ;
        programManager = new ProgramManager(this);
        newsManager    = new NewsManager(this);
        weatherManager = new WeatherManager(this);

        this.addMenuBar();
		//		addStatusLine();

    }

    public HashMap<String, Program> getSequencesLoaded() {
        return sequencesLoaded;
    }

	//	public SequenceManager getSequenceManager() {
    //		return sequenceManager;
    //	}
    protected Control createContents(Composite parent) {
        logger.info("creating contents ..");
        if (Player.FTP_MODE) {
            getShell().setText(LocaleManager.getText(LocaleManager.APP_TITLE) + " FTP");
        } else {
            getShell().setText(LocaleManager.getText(LocaleManager.APP_TITLE));
        }

        try {

            Player.LOGO_IMAGE = new Image(getShell().getDisplay(), getClass().getClassLoader().getResourceAsStream(Player.LOGO));
            getShell().setImage(Player.LOGO_IMAGE);
            logger.debug("logo image loaded");

			//			url = getClass().getClassLoader().getResource(SMP.BCKG);
            //			logger.info("loading " + SMP.BCKG + " | url : " + url.toURI() );
            //			f = new File(url.toURI());						
            Player.BCKG_IMAGE = new Image(getShell().getDisplay(), getClass().getClassLoader().getResourceAsStream(Player.BCKG));

            if (!Utils.isAnEmptyString(System.getProperty(Player.RUNNING_MODE))) {
                Player.DEMO_IMAGE = new Image(
                        getShell().getDisplay(), getClass().getClassLoader().getResourceAsStream(Player.DEMO)
                );
                Player.DEMO_IMAGE_MEDIA = new Media(new Resource(Player.DEMO_IMAGE));
                logger.info("running in demo mode");
                Player.DEMO_MODE = true;
            }
            logger.debug("bckg image loaded");

            /*if (Utils.isAnEmptyString(Player.MPLAYER_PATH)) {
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        LocaleManager.getText(LocaleManager.APP_TITLE),
                        "Error : missing parameter " + PlayerPrefPage.APP_MPLAYER_PATH + "\n"
                        + "Application will be terminated."
                );
                System.exit(1);
            }*/

            if (StringUtils.isEmpty(System.getProperty(Player.NO_CHECK))) {

                if (!Player.DEMO_MODE && !Check.checkValidation()) {

                    MessageDialog.openError(Display.getDefault().getActiveShell(),
                            LocaleManager.getText(LocaleManager.APP_TITLE),
                            "Invalid copy of AT ADV Player.\n\n"
                            + "Application will be terminated."
                    );
                    System.exit(1);
                }
            }

            Player.FTP_ENABLED = Player.isFTPEnabled();

            //			Player.ENABLE_NEWS = true;
            logger.info("*** Player.FTP_MODE    is " + Player.FTP_MODE);
            logger.info("*** Player.FTP_ENABLED is " + Player.FTP_ENABLED);
            logger.info("*** Player.ENABLE_NEWS is " + Player.ENABLE_NEWS);

        } catch (Exception e) {
            logger.error("error occurred ", e);
        }
        parent.setSize(this.size);
        parent.setLocation(this.location);

		//		if ( this.numberOfVideoWindows > 1 ) {
        tabFolder = new CTabFolder(parent, SWT.NONE);
        tabFolder.setBorderVisible(true);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
        for (int i = 0; i < this.numberOfVideoWindows; i++) {
            CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
            tabItem.setText("Screen Manager #" + (i + 1));
            ScreenManagerComposite screenManager = new ScreenManagerComposite(tabFolder, this, i);
            tabItem.setControl(screenManager);
            screenManager.setPlayerSetting((ScreenSetting) playerSetting.get(i));
            listOfScreensManager.add(screenManager);
            screenManager.setWeatherLatlng(weatherLatlng);

        }

        this.programManager.create();
        this.newsManager.create();
        this.weatherManager.create();
        weatherManager.setCoords(weatherLatlng);

        if (numberOfVideoWindows > 1) {
            Set<String> sequencesKeys = getSequencesLoaded().keySet();
            for (int i = 0; i < listOfScreensManager.size(); i++) {
                Combo sequenceCombo = listOfScreensManager.get(i).getSequenceCombo();
                Iterator<String> it = sequencesKeys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    if (sequenceCombo.indexOf(key) == -1) {
                        sequenceCombo.add(key);
                    }
                }
            }
        }

		//		parent.pack();
        return parent;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Init.init();

        extractInfo();
        
        getLogFile();
        
//        getZipLogFile();

        try {
            Properties props = LocalConfiguration.getInstance().getProperties();
            props.setProperty("org.exolab.castor.indent", "true");
            Player wwin = new Player();

            wwin.setBlockOnOpen(true);
            wwin.open();
            //			wwin.getContents().forceFocus();
            Display.getCurrent().dispose();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error occurred ", e);
            System.exit(1);
        } catch (Throwable t) {
            t.printStackTrace();
            logger.error("error occurred",t);
            System.exit(1);
        }
        

    }

    public boolean close() {
        logger.info("stop playing all playerMaster");
        for (int i = 0; i < tabFolder.getItemCount(); i++) {
            CTabItem ti = tabFolder.getItem(i);
            ScreenManagerComposite vmc = (ScreenManagerComposite) ti.getControl();
            vmc.getPlayerMaster().stop();
        }
        logger.info("... done");

        logger.info("saving sequences " + sequencesLoaded.keySet());
        Iterator<String> it = sequencesLoaded.keySet().iterator();

        while (it.hasNext()) {
            Program program = sequencesLoaded.get(it.next());
            logger.info("\t* " + program);
            SequenceFileManager.write(program);
        }
        logger.info("... done");
        logger.info(LocaleManager.getText(LocaleManager.APP_TITLE) + " closed");
        System.exit(0);
        return false;
    }

    private void loadPreferences(String prefs) {

        Point size = null;
        Point location = null;

        PreferenceManager preferenceManager = new PreferenceManager();

        PlayerPrefPage preferencePage = new PlayerPrefPage(this);

        PreferenceNode preferenceNode = new PreferenceNode("prefs", preferencePage);
        preferenceManager.addToRoot(preferenceNode);

        PreferenceStore preferenceStore = new PreferenceStore(prefs);

        try {

            preferenceStore.load();
                        
            String cn = preferenceStore.getString(PlayerPrefPage.APP_COMPUTER);
            if ( StringUtils.isNotEmpty(cn) ) {
            	Player.COMPUTER = cn;
            } else {
            	try {
            		Player.COMPUTER =  InetAddress.getLocalHost().getHostName();
            	} catch (Exception e ) {
            		e.printStackTrace(System.err);
            		Player.COMPUTER = System.getenv("COMPUTERNAME");
            	}            
            }
            
            Player.APP_SEND_ALL_MAIL = preferenceStore.getBoolean(PlayerPrefPage.APP_SEND_ALL_MAIL);

            boolean enabledLog = preferenceStore.getBoolean(PlayerPrefPage.APP_ENABLED_LOG);

            enableLog(enabledLog);

            logger.info("AT ADV Player ");
            logger.info("version @ " + Player.VERSION + " " + VERSION_DATE);

            String locale = preferenceStore.getString(PlayerPrefPage.APP_LOCALE);
            if (StringUtils.isEmpty(locale)) {
                LocaleManager.changeLocale(Locale.getDefault().toString());
            } else {
                LocaleManager.changeLocale(locale);
            }

            String updateSite = preferenceStore.getString(PlayerPrefPage.APP_UPDATE_SITE);
            if (!Utils.isAnEmptyString(updateSite)) {
                Player.UPDATE_SITE = updateSite;
            } else {
                Player.UPDATE_SITE = "http://www.arttechonline.com/atadvplayer/jupdate/update.php";
            }

            /*
             String version = preferenceStore.getString(PlayerPrefPage.APP_VERSION);
             if ( ! Utils.isAnEmptyString(version) ) {
             Player.VERSION = version;
             }

             String versionDate = preferenceStore.getString(PlayerPrefPage.APP_VERSION_DATE);
             if ( ! Utils.isAnEmptyString(versionDate) ) {
             Player.VERSION_DATE = versionDate;
             }
             */
            double ftpRefresh = preferenceStore.getDouble(PlayerPrefPage.APP_FTP_REFRESH);
            if (ftpRefresh == 0) {
                ftpRefresh = PlayerPrefPage.FTP_REFRESH_DEFAULT;
            }
            Player.APP_FTP_REFRESH = ftpRefresh;

            String versionInfo = preferenceStore.getString(PlayerPrefPage.APP_VERSION_INFO);
            if (!Utils.isAnEmptyString(versionInfo)) {
                Player.VERSION_INFO = versionInfo;
            }

			//			Map<String, String> env = System.getenv();
            //	        for (String envName : env.keySet()) {
            //	            System.out.format("%s = %s%n", envName, env.get(envName));
            //	        }
            //	        
            //	        System.out.println("user.dir : " + System.getProperty("user.dir"));
            String mplayer = preferenceStore.getString(PlayerPrefPage.APP_MPLAYER_PATH);

            if (!Utils.isAnEmptyString(mplayer)) {
                //				File file = new File(  System.getProperty("user.dir") + "/" + mplayer);
                File file = new File(mplayer);
                if (file.exists() && file.isFile() && file.canExecute()) {
                    Player.MPLAYER_PATH = mplayer;
                }
            }

            String options = preferenceStore.getString(PlayerPrefPage.APP_MPLAYER_OPTIONS);
            if (!Utils.isAnEmptyString(options)) {
                Player.MPLAYER_OPTIONS = options;
            }
            
            Player.APP_MPLAYER_WAIT_VIDEO_START = preferenceStore.getInt(PlayerPrefPage.APP_MPLAYER_WAIT_VIDEO_START);
            if ( Player.APP_MPLAYER_WAIT_VIDEO_START == 0 ){
            	Player.APP_MPLAYER_WAIT_VIDEO_START = 2;
            }

            Player.APP_TIME_LABEL_RATIO = preferenceStore.getInt(PlayerPrefPage.APP_TIME_LABEL_RATIO);
            Player.APP_DATE_LABEL_RATIO = preferenceStore.getInt(PlayerPrefPage.APP_DATE_LABEL_RATIO);

            int width = preferenceStore.getInt(PlayerPrefPage.APP_WIDTH);
            if (width == 0) {
                width = Player.APP_WIDTH;
            }
            int heigth = preferenceStore.getInt(PlayerPrefPage.APP_HEIGHT);
            if (heigth == 0) {
                heigth = Player.APP_HEIGHT;
            }
            size = new Point(width, heigth);

            int x = preferenceStore.getInt(PlayerPrefPage.APP_LOCATION_X);
			//			if ( x == 0 ){
            //				x = Player.APP_LOCATION_X;
            //			}			
            int y = preferenceStore.getInt(PlayerPrefPage.APP_LOCATION_Y);
			//			if ( y == 0 ){
            //				y = Player.APP_LOCATION_Y;
            //			}			
            location = new Point(x, y);

            int numOfWindows = preferenceStore.getInt(PlayerPrefPage.APP_PLAYER_VIDEO_WINDOWS_NUMBER);
            if (logger.isDebugEnabled()) {
                preferenceStore.list(System.out);
            }
            
            weatherLatlng = preferenceStore.getString(PlayerPrefPage.APP_PLAYER_WEATHER_LATLNG);
            if ( StringUtils.isEmpty(weatherLatlng)){
            	weatherLatlng = " 45.08,7.40";
            }

            if (numOfWindows > 0) {
                this.numberOfVideoWindows = numOfWindows;
                for (int i = 1; i <= numOfWindows; i++) {
                    ScreenSetting setting = new ScreenSetting();
                    Integer[] idx = new Integer[1];
                    idx[0] = new Integer(i);
                    Point p = null;

                    int pw = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_SIZE_WIDTH.format(idx));
                    int ph = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_SIZE_HEIGHT.format(idx));
                    if (pw > 0 && ph > 0) {
                        p = new Point(pw, ph);
                        setting.setSize(p);
                    }

					//					int a = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_X.format(idx)) ;
                    //					int b = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_Y.format(idx) );
                    //
                    //					if ( a > 0 && b > 0 ) {
                    //						p = new Point(a, b);
                    //					}
                    int pt = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_X.format(idx));
                    int pl = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_LOCATION_Y.format(idx));
                    //					if ( pt > 0 && pl > 0) {
                    p = new Point(pt, pl);
                    setting.setLocation(p);
                    //					}

                    String s = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_SEQUENCE_FILE.format(idx));
                    //					ClassLoader.getSystemResource(s).toExternalForm();
                    if (!Utils.isAnEmptyString(s)) {
                        File f = new File(s);
                        setting.setProgramFile(f);
                    }
                    
                    int loop = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_LOOP_NUMBER.format(idx));
                    setting.setLoopNumber(loop);

                    boolean lock = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_LOCK.format(idx));
                    setting.setLock(lock);

                    boolean fade = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FADE.format(idx));
                    setting.setFade(fade);

                    int alpha = preferenceStore.getInt(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_ALPHA.format(idx));
                    setting.setAlpha(alpha);

                    boolean view = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_VIEW.format(idx));
                    setting.setViewScreen(view);

                    boolean allDay = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_ALL_DAY.format(idx));
                    setting.setAllDay(allDay);

                    boolean timed = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_TIMED.format(idx));
                    setting.setTimed(timed);

                    String from = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_TIMED_FROM.format(idx));
                    String to = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_TIMED_TO.format(idx));
                    Date df = Utils.getTimeShort(from);
                    Date dt = Utils.getTimeShort(to);
                    if (timed) {
                        if (df != null && dt != null) {
							//							if ( df.after(dt) ){
                            //								timed = false;
                            //								logger.error("timed not enabled : error in timing : from [" + Utils.getTimeAsString(df) +"] to [" + Utils.getTimeAsString(dt)+"]");
                            //							} else {
                            logger.debug("timed : from [" + Utils.getTimeAsString(df) + "] to [" + Utils.getTimeAsString(dt) + "]");
                            setting.setFrom(df);
                            setting.setTo(dt);
                            //							}
                        }
                    }

                    boolean black = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_BLACK.format(idx));
                    setting.setWhenNotActiveBlack(black);

                    boolean watch = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_WATCH.format(idx));
                    setting.setWhenNotActiveWatch(watch);

                    boolean img = preferenceStore.getBoolean(ScreenManagerPrefPage.APP_PLAYER_I_WHEN_NOT_ACTIVE_IMAGE.format(idx));
                    setting.setWhenNotActiveImage(img);

                    String timeFontDataString
                            = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FONT_TIME.format(idx));
                    if (!Utils.isAnEmptyString(timeFontDataString)) {
                        try {
                            FontData timeFontData = new FontData(timeFontDataString);
                            setting.setTimeLabelFontData(timeFontData);
                        } catch (Exception e) {
                            logger.error("time font date error", e);
                        }
                    }

                    String dateFontDataString
                            = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_FONT_DATE.format(idx));
                    if (!Utils.isAnEmptyString(dateFontDataString)) {
                        try {
                            FontData dateFontData = new FontData(dateFontDataString);
                            setting.setDateLabelFontData(dateFontData);
                        } catch (Exception e) {
                            logger.error("date font date error", e);
                        }
                    }

                    String watchImageString
                            = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_WATCH_IMAGE_FILE.format(idx));
                    if (!Utils.isAnEmptyString(watchImageString)) {
                        File watchImageFile = new File(watchImageString);
                        if (watchImageFile.exists() && watchImageFile.isFile() && watchImageFile.canRead()) {
                            setting.setWatchImageFile(watchImageFile);
                        }
                    }

                    String timeColor
                            = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_COLOR_TIME.format(idx));
                    if (!Utils.isAnEmptyString(timeColor)) {
                        StringTokenizer st = new StringTokenizer(timeColor, ScreenManagerPrefPage.COLOR_SEPARATOR);
                        if (st.countTokens() == 3) {
                            try {
                                String rs = st.nextToken();
                                String gs = st.nextToken();
                                String bs = st.nextToken();

                                int red = Integer.parseInt(rs);
                                int green = Integer.parseInt(gs);
                                int blue = Integer.parseInt(bs);

                                RGB rgb = new RGB(red, green, blue);
                                setting.setTimeLabelFontColor(rgb);
                            } catch (NumberFormatException e) {
                                logger.error("time color error", e);
                            }
                        }
                    }

                    String dateColor
                            = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_SCREEN_COLOR_DATE.format(idx));
                    if (!Utils.isAnEmptyString(dateColor)) {
                        StringTokenizer st = new StringTokenizer(dateColor, ScreenManagerPrefPage.COLOR_SEPARATOR);
                        if (st.countTokens() == 3) {
                            try {
                                String rs = st.nextToken();
                                String gs = st.nextToken();
                                String bs = st.nextToken();

                                int red = Integer.parseInt(rs);
                                int green = Integer.parseInt(gs);
                                int blue = Integer.parseInt(bs);

                                RGB rgb = new RGB(red, green, blue);
                                setting.setDateLabelFontColor(rgb);
                            } catch (NumberFormatException e) {
                                logger.error("time color error", e);
                            }
                        }
                    }

					//					String weatherLatLng = preferenceStore.getString(ScreenManagerPrefPage.APP_PLAYER_I_WEATHER_LATLNG.format(idx));
                    //					setting.setWeatherLatLng(weatherLatLng);
                    this.playerSetting.add(setting);
                }
            } else {
                this.numberOfVideoWindows = 1;
                this.playerSetting.add(new ScreenSetting());
            }

        } catch (IOException e) {
            logger.error("preferences' file not found", e);
            size = new Point(Player.APP_WIDTH, Player.APP_HEIGHT);
            location = new Point(Player.APP_LOCATION_X, Player.APP_LOCATION_Y);
            this.numberOfVideoWindows = 1;
            this.playerSetting.add(new ScreenSetting());
        }

//        if (StringUtils.isEmpty(Player.MPLAYER_PATH)) {
//            FileDialog fd = new FileDialog(new Shell(Display.getDefault()));
//            fd.setText("Find MPlayer path ..");
//            fd.setFileName("mplayer.exe");
//            fd.setFilterPath(Player.SETTINGS_PATH);
//            Player.MPLAYER_PATH = fd.open();
//        }

        if (!Utils.isAnEmptyString(System.getProperty(Player.NEWS_MODE))) {
            Player.ENABLE_NEWS = true;
        }

        this.setSize(size);
        this.setLocation(location);

    }

    public void enableLog(boolean enabledLog) {
        this.enabledLog = enabledLog;
//        Enumeration<Logger> enumeration = LogManager.getCurrentLoggers();
//        while (enumeration.hasMoreElements()) {
//            Logger log = enumeration.nextElement();
//            if (!this.enabledLog) {
//                log.setLevel(Level.OFF);
//            } else {
//                log.setLevel(Level.INFO);
//            }
//        }
    }

    public boolean isEnabledLog() {
        return enabledLog;
    }

    public void setSize(Point size) {
        this.size = size;
        logger.info("setting size to " + size);
    }

    public void setLocation(Point location) {
        this.location = location;
        logger.info("setting location to " + location);
    }

    protected MenuManager createMenuManager() {
        MenuManager main_menu = new MenuManager(null);

        MenuManager file_menu = new MenuManager(LocaleManager.getText(LocaleManager.APP_MENU_FILE));
        //		file_menu.add( new PreferenceAction(this) );
        file_menu.add(new EnableLogAction(this));
        file_menu.add( new SendAllMailAction(this));
        file_menu.add(new Separator());
        file_menu.add(new ComputerNameAction());
        file_menu.add(new Separator());
        file_menu.add( new WeatherManagerAction(this.weatherManager));
        file_menu.add(new Separator());
        SaveSettingAction saveSettingAction = new SaveSettingAction(this);
        file_menu.add(saveSettingAction);
        file_menu.add(new Separator());
        ExitAction exitAction = new ExitAction(this);
        file_menu.add(exitAction);

        main_menu.add(file_menu);

        MenuManager sequence_menu = new MenuManager(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE));
        //sequence_menu.add( new SequenceManagerAction( this.sequenceManager ) );
        sequence_menu.add(new ProgramManagerAction(programManager));
        main_menu.add(sequence_menu);

        Player.ENABLE_NEWS = false;

        news_menu = new MenuManager("News");
        news_menu.add(new NewsManagerAction(newsManager));
        main_menu.add(news_menu);
        news_menu.setVisible(Player.ENABLE_NEWS);

		//		MenuManager setting_menu = new MenuManager("Settings");
        //		setting_menu.add( new SettingAction());
        //		
        //		setting_menu.add(  new Action("Italiano") {
        //			@Override
        //			public void run() {
        //				LocaleManager.changeLocale("it");
        //				getShell().layout(true, true);
        //				for( int i=0; i < tabFolder.getItemCount(); i++) {
        //					CTabItem ti = tabFolder.getItem(i);
        //					ScreenManagerComposite vmc = (ScreenManagerComposite) ti.getControl();
        //					vmc.layout(true, true);
        //					vmc.redraw();
        //					vmc.pack();
        //				}
        //				getShell().pack();
        //				
        //
        //				PreferenceManager preferenceManager = new PreferenceManager(); 
        //
        //				SMPlayerPrefPage preferencePage = new SMPlayerPrefPage();		
        //
        //				PreferenceNode preferenceNode = new PreferenceNode("prefs", preferencePage);
        //				preferenceManager.addToRoot(preferenceNode);	
        //
        //				PreferenceStore preferenceStore = new PreferenceStore(Player.PREFS);
        //				try {
        //					preferenceStore.load();	 
        //				} catch (IOException e) {
        //					      // Ignore
        //				    }
        //				PreferenceDialog dlg = new PreferenceDialog(null, preferenceManager);
        //				    dlg.setPreferenceStore(preferenceStore);
        //
        //				    // Open the dialog
        //				    dlg.open();
        //
        //				    try {
        //				      // Save the preferences
        //				    	preferenceStore.save();
        //				    	logger.error("preferences saved");
        //				    } catch (IOException e) {
        //				    	logger.error("error occurred ", e);
        //				      e.printStackTrace();
        //				    }
        //
        //			}
        //		});
        //		setting_menu.add(  new Action("English") {
        //			@Override
        //			public void run() {
        //				LocaleManager.changeLocale("en");
        //				getShell().layout(true, true);
        //				for( int i=0; i < tabFolder.getItemCount(); i++) {
        //					CTabItem ti = tabFolder.getItem(i);
        //					ScreenManagerComposite vmc = (ScreenManagerComposite) ti.getControl();
        //					vmc.layout(true, true);
        //					vmc.pack();
        //				}
        //				getShell().pack();
        //			}
        //		});
        //
        //		main_menu.add(setting_menu);
        MenuManager help_menu = new MenuManager(LocaleManager.getText(LocaleManager.APP_MENU_HELP));
        help_menu.add(new ManualAction(this));
        help_menu.add(new Separator());
        help_menu.add(new AboutAction());        
        help_menu.add(new Separator());
        help_menu.add(new SendTestEmailAction());        
        main_menu.add(help_menu);

        return main_menu;
    }

    protected StatusLineManager createStatusLineManager() {
        return slm;
    }

    public StatusLineManager getSlm() {
        return slm;
    }

    ;


	public void addSequence(Program program) {
        boolean insert = true;
        if (this.sequencesLoaded.containsKey(program.getName())) {
            insert = false;
        }
        if (!insert) {
            return;
        }
        this.sequencesLoaded.put(program.getName(), program);
        for (int i = 0; i < this.tabFolder.getItemCount(); i++) {
            CTabItem ti = this.tabFolder.getItem(i);
            ScreenManagerComposite vmc = (ScreenManagerComposite) ti.getControl();
            String name = program.getName();
            Combo c = vmc.getSequenceCombo();
            c.add(name);
        }
    }

    public Program getSequenceSMP(String name) {
        Program smp = (Program) this.sequencesLoaded.get(name);
        return smp;
    }

    public void setFocusOnTabItem(int idx) {
        this.tabFolder.setSelection(idx);
    }

    public static boolean hasVersionInfo() {
        if (Utils.isAnEmptyString(Player.VERSION)
                && Utils.isAnEmptyString(Player.VERSION_DATE)) {
            return false;
        }
        return true;

    }

    public int getNumberOfVideoWindows() {
        return numberOfVideoWindows;
    }

    public Point getLocation() {
        return location;
    }

	//	public static FTPClient getFtpClient() throws Exception {
    //		FTPClient ftpClient = null;
    //
    //		String ftp  = System.getenv("FTP_HOME");		
    //		String user = System.getenv("FTP_USER");		
    //		String pass = System.getenv("FTP_PASS");
    //
    //
    //		ftpClient = new FTPClient();
    //		ftpClient.connect(ftp);
    //		ftpClient.login(user, pass);
    //
    //		return ftpClient;
    //	}
    public static boolean isFTPEnabled() {
        String ftp = System.getenv("FTP_HOME");
        String user = System.getenv("FTP_USER");
        String pass = System.getenv("FTP_PASS");

        if (StringUtils.isEmpty(ftp) || StringUtils.isEmpty(user) || StringUtils.isEmpty(pass)) {
            logger.warn("FTP env vars NOT setted !");
            return false;
        }
        logger.info("FTP env vars setted");
        return true;
    }

    public static String getMediaLocalePath(String programName) {
        String path
                = Player.REMOTE_VIDEO_PATH.getAbsolutePath()
                + File.separator
                + programName;
        logger.debug("getLocalePath : " + path);
        return path;

    }

    public static String getProgramLocalPath() {
        return Player.REMOTE_PROGRAM_PATH.getAbsolutePath();
    }

    private static final String INFO_SEP = "|";

    public static void extractInfo() {
        String buildAndTime = Player.class.getPackage().getImplementationVersion();
        if (StringUtils.isNotEmpty(buildAndTime)) {
            StringTokenizer st = new StringTokenizer(buildAndTime, INFO_SEP);
            int id = 0;
            while (id < 2 && st.hasMoreTokens()) {
                if (id == 0) {
                    Player.VERSION = st.nextToken();
                }
                if (id == 1) {
                    Player.VERSION_DATE = st.nextToken();
                }
                id++;
            }
        }
    }
    
    
    public static void moveMouse(){
    	Display.getDefault().asyncExec( new Runnable() {					
			@Override
			public void run() {
				Point cl = Display.getCurrent().getCursorLocation();
				for ( ScreenManagerComposite smc : Player.listOfScreensManager )
				{
					if ( smc.isRunning() )
					{
						Point size = smc.getPlayerSetting().getSize();
						Point loca = smc.getPlayerSetting().getLocation();
						if ( ( loca.x < cl.x  &&  cl.x < ( loca.x + size.x) ) &&  /**/
								 ( loca.y < cl.y  &&  cl.y < ( loca.y + size.y) )
							){
                                Display.getDefault().setCursorLocation( loca.x + size.x, loca.y + size.y  );
                                logger.debug("*** CursorPosition ["+cl+"] *** shell *** Size ["+size+"] Location ["+loca+"] *** CURSOR MOVED *** " +Display.getCurrent().getCursorLocation());
							}
					}					
				}			
			
			}
		} );
    }
    

	public static void moveMouse(Control control){		
		logger.debug(" adding mouse listener *** ENTER ***");		
		moveMouse(control, SWT.MouseEnter );
		logger.debug(" adding mouse listener *** HOVER ***");
		moveMouse(control, SWT.MouseHover );
		logger.debug(" adding mouse listener *** UP ***");
		moveMouse(control, SWT.MouseUp );
	}
	
	private static void moveMouse(Control control, int event ){
		control.addListener(event, new Listener() {
			public void handleEvent(Event e) {
				moveMouse();
			}
		});
	}
	

	public String getWeatherLatlng() {
		return this.weatherLatlng;
	}

	public void setWeatherLatlng(String latlng) {
		this.weatherLatlng = latlng;
		logger.warn("Weather latlng : " + latlng);
		updateWeather();
		
	}
	
	private void updateWeather() {
		for (int i = 0; i < this.tabFolder.getItemCount(); i++) {
			ScreenManagerComposite sm = (ScreenManagerComposite) tabFolder.getItems()[i].getControl();
			sm.setWeatherLatlng(this.weatherLatlng);
		}		
	}
	
	public static File getLogFile() {
		File file = null;
//		Enumeration en = LogManager.getLogger(Player.class.getPackage().getName()).getAllAppenders();
//		while ( en.hasMoreElements() ){
//			Appender app = (Appender) en.nextElement();
//			if ( app instanceof FileAppender ){
//				FileAppender fileAppender = (FileAppender) app;
//				file = new File ( fileAppender.getFile() );			
//				System.out.println(file.getAbsolutePath());
//			}
//		}
		
		String sfile = Player.SETTINGS_PATH + "/logs/player.vlc.log";
		file = new File(sfile);
		
		if ( file != null && file.exists() )
			logger.info("logFile : " + file);
		return file ;

	}
	
	public static File getZipLogFile() {	
		byte[] buffer = new byte[1024];
		File zipLogFile = null;
		File logFile = Player.getLogFile();
		try {
			if ( logFile != null && logFile.exists() ) {
				zipLogFile = File.createTempFile(logFile.getName(), ".zip");
				
				FileOutputStream fos = new FileOutputStream(zipLogFile);
				ZipOutputStream zos = new ZipOutputStream(fos);
				ZipEntry zipEntry = new ZipEntry(logFile.getName());
				zos.putNextEntry(zipEntry);
				FileInputStream in = new FileInputStream(logFile.getAbsolutePath());
	    		int len;
	    		while ((len = in.read(buffer)) > 0) {
	    			zos.write(buffer, 0, len);
	    		}
	    		in.close();
	    		zos.closeEntry();
	    		zos.close();
	    		fos.close();
	    		logger.info("zip logFile created");
			} else {
				logger.error("can't create zip for logFile" );
			}
		} catch (IOException e) {
			logger.error("error creating zip logFile", e);
		}
		return zipLogFile;		
	}

}
