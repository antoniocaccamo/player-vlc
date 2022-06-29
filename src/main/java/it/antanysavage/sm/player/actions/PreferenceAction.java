package it.antanysavage.sm.player.actions;

import java.io.IOException;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.prefs.PlayerPrefPage;
import it.antanysavage.sm.player.prefs.ScreenManagerPrefPage;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PreferenceAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(PreferenceAction.class);

	private Player player;

	public PreferenceAction(Player player) {
		super( LocaleManager.getText(LocaleManager.APP_MENU_FILE_PREFS) , AS_PUSH_BUTTON);
		this.player = player;
	}

	public void run() {
		PreferenceManager preferenceManager = new PreferenceManager(); 

		PlayerPrefPage playerPrefPage = new PlayerPrefPage(player);		

		PreferenceNode preferenceNode = new PreferenceNode("player", playerPrefPage);
		preferenceManager.addToRoot(preferenceNode);	
		for( int i = 1; i <= player.getNumberOfVideoWindows() ; i++){
			PreferenceNode screenManagerPreferenceNode = new PreferenceNode("screenManager #" + i, new ScreenManagerPrefPage(player, i));
			preferenceManager.addTo( preferenceNode.getId(), screenManagerPreferenceNode);
		}

		PreferenceStore preferenceStore = new PreferenceStore(Player.PREFS);
		try {
			preferenceStore.load();	 
		} catch (IOException e) {
			// Ignore
		}
		Shell shell = new Shell(Display.getCurrent());
		shell.setLocation( player.getLocation());
		PreferenceDialog dlg = new PreferenceDialog( shell, preferenceManager);		
		dlg.setPreferenceStore(preferenceStore);

		// Open the dialog
		dlg.open();

		try {
			// Save the preferences
			preferenceStore.save();
			logger.info("preferences saved");
		} catch (IOException e) {
			logger.error("error occurred ", e);
			e.printStackTrace();
		}
	}


}
