package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabItem;

public class ProgramSaveAction extends Action {

	private ProgramManager programManager;

	public ProgramSaveAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_SAVE));
		this.programManager = programManager;
	}
	
	public void run() {
			CTabItem item = programManager.getProgramTabFolder().getSelection();
			ProgramTab programTab = (ProgramTab) item.getControl();
			Program smp = programTab.getProgram();
			
			SequenceFileManager.write(smp);
			programManager.getStatusLineManager().setMessage("Sequence saved " + smp.toString());		
	}

}
