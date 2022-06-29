package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class ProgramSaveAsAction extends Action {
	

	private static Logger logger = LoggerFactory.getLogger(ProgramSaveAsAction.class);
	private ProgramManager programManager;

	public ProgramSaveAsAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_SAVE_AS));
		this.programManager = programManager;
	}

	public void run() {
		FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.SAVE );
		
		fileDialog.setFilterExtensions(new String[] {"*.xseq"});
		fileDialog.setFilterPath(System.getProperty("user.dir"));
		String  s = fileDialog.open();
		if ( ! Utils.isAnEmptyString(s) ) {
			int idx = s.lastIndexOf(".xseq");
			if ( idx == -1 ) {
				s += ".xseq";
			} 				
			File f = new File(s);
			try {
				if ( ! f.exists() ) {
					f.createNewFile();
				}
				if ( f.canWrite() ) {
					idx = f.getName().lastIndexOf(".xseq");
					String name = f.getName().substring(0,idx);
					CTabItem item = programManager.getProgramTabFolder().getSelection();
					ProgramTab programTab = (ProgramTab) item.getControl();
					Program program = programTab.getProgram();							
					
					Program dest  = Program.clone(program, name , f);
									
					SequenceFileManager.write(dest);
					programManager.getTheplayer().addSequence(dest);
					programManager.getStatusLineManager().setMessage("Sequence saved " + dest.toString());
					programManager.createInitialTabs(programManager.getTheplayer().getSequencesLoaded());
				}
			} catch ( Exception e) {
				MessageDialog.openError(
						Display.getDefault().getActiveShell(), 
						LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_OPEN), 								
						"Error : " + e.getLocalizedMessage()
				);
				programManager.getStatusLineManager().setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + " | File ["+s +"]");
				logger.error("error occurred ", e);
			}
		}
	}
}