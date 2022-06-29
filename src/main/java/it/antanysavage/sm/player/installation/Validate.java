package it.antanysavage.sm.player.installation;

import it.antanysavage.sm.player.Player;

import java.util.prefs.Preferences;

public class Validate {
	
	public static void validate() {
		Preferences prefs = Preferences.systemRoot();		
		prefs.put(Player.REG_KEY, Player.REG_KEY_VALUE);
	}
	
	public static void main(String[] args) {
		validate();
	}

}
