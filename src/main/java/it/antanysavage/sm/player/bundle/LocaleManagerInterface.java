package it.antanysavage.sm.player.bundle;

import java.util.MissingResourceException;

public interface LocaleManagerInterface {
	
	
	public String getText(String key) throws MissingResourceException ;

	public static final String   TITLE = "app.title"; 
}
