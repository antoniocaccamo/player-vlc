package it.antanysavage.sm.player.model;

import java.io.File;
import java.util.Date;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;

public class ScreenSetting {
	
	private static final boolean      ALL_DAY_DEFAULT = false;

	private static final Point           SIZE_DEFAULT = new Point(200, 180);
	
	private static final Point       LOCATION_DEFAULT = new Point(200, 180);
	
	private static final boolean         LOCK_DEFAULT = false;
	
	private static final boolean      AUTOPLAY_DEFAULT = false;
	
	private static final boolean   VIEW_SCREEN_DEFAULT = true; 
	
	private static final FontData TIME_LABEL_FONT_DATA = new FontData("1|Tahoma|15.75|0|WINDOWS|1|-21|0|0|0|400|0|0|0|0|3|2|1|34|Tahoma");
	
	private static final FontData DATE_LABEL_FONT_DATA = new FontData("1|Tahoma|15.75|0|WINDOWS|1|-21|0|0|0|400|0|0|0|0|3|2|1|34|Tahoma");
	
	private static final RGB               DEFAULT_RGB = new RGB(255, 255, 0);

	private static final int DEFAULT_ALPHA             = 255;
	
	public static final String DEFAULT_WEATHER_LATLNG = "45.08,7.40";
	
	public static final int DEFAULT_NUMBER_LOOP       = 0;
	
	private File                        watchImageFile = null;

	private Point                                 size = SIZE_DEFAULT ;
	
	private Point                             location = LOCATION_DEFAULT;
	
	private File                           programFile ;
	
	private boolean                               lock = LOCK_DEFAULT;	
	
	private boolean                         viewScreen = VIEW_SCREEN_DEFAULT;
	
	private boolean                             allDay = true;
	
	private boolean                              timed = false;

	private Date                                  from ;
	
	private Date                                    to ;
	
	private boolean                 whenNotActiveWatch ;
	
	private boolean                 whenNotActiveBlack ;
	
	private FontData                 timeLabelFontData = TIME_LABEL_FONT_DATA;
	
	private FontData                 dateLabelFontData = DATE_LABEL_FONT_DATA;

	private RGB                     timeLabelFontColor = DEFAULT_RGB;

	private RGB                     dateLabelFontColor = DEFAULT_RGB;
	
	private boolean                               fade = false;

	private int                                  alpha = DEFAULT_ALPHA;
	
	private String                       weatherLatLng = DEFAULT_WEATHER_LATLNG;

	private boolean whenNotActiveImage;               
	
	private int loopNumber                             = DEFAULT_NUMBER_LOOP;

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public File getProgramFile() {
		return programFile;
	}

	public void setProgramFile(File sequenceFile) {
		this.programFile = sequenceFile;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isViewScreen() {
		return viewScreen;
	}

	public void setViewScreen(boolean viewScreen) {
		this.viewScreen = viewScreen;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public boolean isTimed() {
		return timed;
	}

	public void setTimed(boolean timed) {
		this.timed = timed;
	}

	public boolean isWhenNotActiveWatch() {
		return whenNotActiveWatch;
	}

	public void setWhenNotActiveWatch(boolean whenNotActiveWatch) {
		this.whenNotActiveWatch = whenNotActiveWatch;
	}

	public boolean isWhenNotActiveBlack() {
		return whenNotActiveBlack;
	}

	public void setWhenNotActiveBlack(boolean whenNotActiveBlack) {
		this.whenNotActiveBlack = whenNotActiveBlack;
	}

	public FontData getTimeLabelFontData() {
		return timeLabelFontData;
	}

	public void setTimeLabelFontData(FontData timeLabelFontData) {
		this.timeLabelFontData = timeLabelFontData;
	}

	public FontData getDateLabelFontData() {
		return dateLabelFontData;
	}

	public void setDateLabelFontData(FontData dateLabelFontData) {
		this.dateLabelFontData = dateLabelFontData;
	}	
	
	public File getWatchImageFile() {
		return watchImageFile;
	}
	
	public void setWatchImageFile(File watchImageFile) {
		this.watchImageFile = watchImageFile;
	}

	public void setTimeLabelFontColor(RGB rgb) {
		this.timeLabelFontColor = rgb;
		
	}

	public void setDateLabelFontColor(RGB rgb) {
		this.dateLabelFontColor = rgb;
		
	}
	
	public RGB getTimeLabelFontColor() {
		return timeLabelFontColor;
	}
	
	public RGB getDateLabelFontColor() {
		return dateLabelFontColor;
	}

	public boolean isFade() {
		return fade;
	}

	public void setFade(boolean fade) {
		this.fade = fade;
	}
	
	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {		
		this.alpha   = alpha ;
	}
	
	public String getWeatherLatLng() {
		return weatherLatLng;
	}
	
	public void setWeatherLatLng(String weatherLatLng) {
		this.weatherLatLng = weatherLatLng;
	}
	
	public boolean isWhenNotActiveImage() {
		return whenNotActiveImage;
	}

	public void setWhenNotActiveImage(boolean img) {
		this.whenNotActiveImage = img;
		
	}
	
	public int getLoopNumber() {
		return loopNumber;
	}
	
	public void setLoopNumber(int loopNumber) {
		this.loopNumber = loopNumber;
	}
	
}
