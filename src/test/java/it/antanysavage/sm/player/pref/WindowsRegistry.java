package it.antanysavage.sm.player.pref;

import it.antanysavage.sm.player.Player;

import java.util.prefs.Preferences;

public class WindowsRegistry {
	
	private static final String REG_KEY = "---AT ADV Player--";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Preferences prefs = Preferences.systemRoot();		
		prefs.put(REG_KEY, "aaaaaaaaaaaa");
		System.out.println(REG_KEY + " : " + prefs.get( REG_KEY , "not present"));
	}

}
