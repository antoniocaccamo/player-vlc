package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;

import java.util.HashMap;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;

public class ProgramManagerAction extends Action {

	private ProgramManager programManager;

	public ProgramManagerAction(ProgramManager programManager) {
		super( LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) , AS_PUSH_BUTTON);
		this.programManager = programManager;
	}
	
	public void run() {
		
		Point p  = programManager.getTheplayer().getShell().getLocation();
		p.x += 10;
		p.y += 10;
		programManager.getShell().setLocation(p);
		programManager.createInitialTabs(programManager.getTheplayer().getSequencesLoaded());	
		programManager.getShell().setVisible(true);
		programManager.setBlockOnOpen(true);		
		programManager.getShell().forceFocus();
	}
}
 