package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

public class SaveSequenceAction extends Action {

	private SequenceManager sequenceManager;

	public SaveSequenceAction(SequenceManager sequenceManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_SAVE));
		this.sequenceManager = sequenceManager;
	}
	
	public void run() {
		IStructuredSelection selection = 
			(IStructuredSelection) this.sequenceManager.getSequecensListViewer().getSelection();
		if ( selection != null){
			Program smp = (Program) selection.getFirstElement();			
			SequenceFileManager.write(smp);
			this.sequenceManager.getStatusLineManager().setMessage("Sequence saved " + smp.toString());
		}
	}

}
