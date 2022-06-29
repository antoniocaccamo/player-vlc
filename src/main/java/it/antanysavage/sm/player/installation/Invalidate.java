package it.antanysavage.sm.player.installation;

import it.antanysavage.sm.player.Player;

import java.util.prefs.Preferences;

public class Invalidate {
	
	public static void main(String[] args) {
		invalidate();
	}

	public static void invalidate() {
			Preferences prefs = Preferences.systemRoot();		
			prefs.remove(Player.REG_KEY);
		}
}

