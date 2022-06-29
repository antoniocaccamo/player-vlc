package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.util.Utils;

import java.util.Iterator;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.CTabItem;

public class VideoDeleteAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(VideoDeleteAction.class);

	private ProgramManager programManager;

	public VideoDeleteAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DELETE ));
		this.programManager = programManager;
	}
    
	public void run() {
		String text = this.getText();
		int idx = text.indexOf("@");
		if ( idx!= -1) {
			text = text.substring(0, idx);
		}
		CTabItem item = programManager.getProgramTabFolder().getSelection();
		ProgramTab programTab = (ProgramTab) item.getControl();
		IStructuredSelection	selection = 
			(IStructuredSelection) programTab.getVideosOfSequencesTableViewer().getSelection();
		if ( selection != null ) {
			if ( ! MessageDialog.openQuestion(
					programManager.getShell(), 
					LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 	
					LocaleManager.getText(LocaleManager.WARNINGS_ARE_YOU_SURE)) ) {
				return ;
			}
			Utils.deleteMedia(selection, programTab);
		}
		else {
			MessageDialog.openError(programManager.getShell(), 
					LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 
					LocaleManager.getText(LocaleManager.ERRORS_VIDEO_NO_SELECTION)
			);
		}
	}
}




