package it.antanysavage.sm.player.actions;

import java.util.HashMap;
import java.util.Iterator;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.ScreenManagerComposite;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabItem;
import org.slf4j.LoggerFactory;

public class ExitAction extends Action {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExitAction.class);

	private Player player;

	public ExitAction( Player player) {
		super( LocaleManager.getText(LocaleManager.APP_MENU_FILE_EXIT) , AS_PUSH_BUTTON);
		this.player = player;
	}
	
	public void run() {
		logger.info("stop playing all playerMaster ...");
		for( int i=0; i < this.player.tabFolder.getItemCount(); i++) {
			CTabItem ti = this.player.tabFolder.getItem(i);
			ScreenManagerComposite vmc = (ScreenManagerComposite) ti.getControl();
			vmc.getPlayerMaster().stop();
		}
		logger.info("... done");
		
		HashMap<String, Program> sequencesLoaded = player.getSequencesLoaded();
		logger.info("saving sequences " + sequencesLoaded.keySet());
		Iterator<String> it = sequencesLoaded.keySet().iterator();
		while ( it.hasNext() ){
			Program program = sequencesLoaded.get(it.next() );
			logger.info("\t* " + program);	
			SequenceFileManager.write(program);
		}
		logger.info("... done");
		
		logger.info(LocaleManager.getText(LocaleManager.APP_TITLE) + " closed");	
		System.exit(0);
	}
}
