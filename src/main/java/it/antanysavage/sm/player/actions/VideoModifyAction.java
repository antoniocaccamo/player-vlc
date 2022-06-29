package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.CTabItem;

public class VideoModifyAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(VideoModifyAction.class);
	
	private ProgramManager programManager;

	public VideoModifyAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_MODIFY));
		this.programManager = programManager;
	}

	public void run() {
		Media media = null;
		
		CTabItem item = programManager.getProgramTabFolder().getSelection();
		ProgramTab programTab = (ProgramTab) item.getControl();
		IStructuredSelection	selection = 
			(IStructuredSelection) programTab.getVideosOfSequencesTableViewer().getSelection();
		if ( selection != null && selection.size() == 1) {
			media = (Media) selection.getFirstElement();
			if ( media != null ) {
				logger.info("Editing video with id : " + media.getId());
				Utils.modifyMedia( programManager.getShell(), programTab, media );
//				programTab.getVideosOfSequencesTableViewer().setInput(programTab.getProgram().getVideos());
//				programTab.getVideosOfSequencesTableViewer().refresh();
			}
		}
	}
 
}


