package it.antanysavage.sm.player.installation;

import it.antanysavage.sm.player.Player;

import java.util.prefs.Preferences;

public class Check {
	
	public static boolean checkValidation() {
		Preferences prefs = Preferences.systemRoot();	
		String value = prefs.get( Player.REG_KEY , "not present");
		if ( Player.REG_KEY_VALUE.equals(value.trim())) {
			return true;
		}
		return false;
	}

}
