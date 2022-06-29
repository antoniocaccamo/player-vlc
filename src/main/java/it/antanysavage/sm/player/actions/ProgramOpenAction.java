package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class ProgramOpenAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(ProgramOpenAction.class);
	private ProgramManager programManager;

	public ProgramOpenAction(ProgramManager programManager ) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_OPEN));
		this.programManager = programManager;
	}

	public void run() {		

		FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.OPEN );
		fileDialog.setFilterPath(System.getProperty("user.dir"));
		fileDialog.setFilterExtensions(new String[] {"*.xseq"});
		
		String sequenceFile = fileDialog.open();
		if ( ! Utils.isAnEmptyString(sequenceFile) ) {
				
			try {					
				Sequence sequence = SequenceFileManager.read(sequenceFile);	
				Program program = Utils.getProgram(sequence, sequenceFile);	
				
				if ( Player.FTP_MODE && ! program.isRemote() ) {
					String text = getText();
					int idx = text.lastIndexOf("@");
					if ( idx!= -1) {
						text = text.substring(0, idx);
					}
					MessageDialog.openError(
							Display.getDefault().getActiveShell(), 
							text, 								
							"Error : Only Remote Sequence are allowed" 
					);
					return;
				}

				CTabItem tabItem = new CTabItem( programManager.getProgramTabFolder(), SWT.NONE);
				tabItem.setText(program.getName());
				tabItem.setControl( new ProgramTab(programManager.getProgramTabFolder(), programManager, program) );

				programManager.getProgramTabFolder().setSelection(tabItem);

				programManager.getTheplayer().addSequence(program);
				programManager.getStatusLineManager().setMessage(program.toString());
			}catch (Exception e) {
				MessageDialog.openError(
						Display.getDefault().getActiveShell(), 
						LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_OPEN), 								
						"Error : " + e.getLocalizedMessage()
				);
				programManager.getStatusLineManager().setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + " | File ["+sequenceFile +"]");
				logger.error("error occurred ", e);
			}			
		}
	}

}
