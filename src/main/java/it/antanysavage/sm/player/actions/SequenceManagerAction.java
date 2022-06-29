package it.antanysavage.sm.player.actions;

import java.util.HashMap;

import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;

public class SequenceManagerAction extends Action {

	private SequenceManager sequenceManager;

	public SequenceManagerAction(SequenceManager sequenceVideoManager) {
		super( LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) , AS_PUSH_BUTTON);
		this.sequenceManager = sequenceVideoManager;
	}
	
	public void run() {
		
		Point p  = sequenceManager.getSmPlayer().getShell().getLocation();
		p.x += 10;
		p.y += 10;
		sequenceManager.getShell().setLocation(p);
		HashMap sh = sequenceManager.getSmPlayer().getSequencesLoaded();
		sequenceManager.getSequecensListViewer().setInput(sh);	
		sequenceManager.getShell().setVisible(true);
		sequenceManager.setBlockOnOpen(true);		
		sequenceManager.getShell().forceFocus();
	}
}
 