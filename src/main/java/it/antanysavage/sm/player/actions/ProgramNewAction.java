package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class ProgramNewAction extends Action {

	private static final Logger logger = LoggerFactory.getLogger(ProgramNewAction.class);
	private ProgramManager programManager;


	public ProgramNewAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_NEW));
		this.programManager = programManager;
	}

	@Override
	public void run() {
		FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.SAVE );
		fileDialog.setFilterExtensions(new String[] {Player.SEQUENCE_FILE_EXT_FILTER});
		fileDialog.setFilterPath(System.getProperty("user.dir"));
		String  s = fileDialog.open();
		if ( ! Utils.isAnEmptyString(s) ) {
			int idx = s.lastIndexOf( Player.SEQUENCE_FILE_EXT);
			if ( idx == -1 ) {
				s += Player.SEQUENCE_FILE_EXT;
			} 				
			File f = new File(s);
			try {
				if ( ! f.exists() ) {
					f.createNewFile();
				}
				if ( f.canWrite() ) {
					idx = f.getName().lastIndexOf(Player.SEQUENCE_FILE_EXT);
					String name = f.getName().substring(0,idx);
					Sequence sequence = new Sequence();
					sequence.setName(name);
					sequence.setVideos( new Videos() );

					Program program = new Program(sequence, f.getPath());

					SequenceFileManager.write(program);								

					CTabItem tabItem = new CTabItem( programManager.getProgramTabFolder(), SWT.NONE);
					tabItem.setText(name);
					tabItem.setControl( new ProgramTab(programManager.getProgramTabFolder(), programManager, program) );

					programManager.getProgramTabFolder().setSelection(tabItem);

					programManager.getStatusLineManager().setMessage(program.toString());
					programManager.getTheplayer().addSequence(program);	
				}
			} catch (Exception e) {
				MessageDialog.openError(
						Display.getDefault().getActiveShell(), 
						LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_NEW), 								
						"Error : " + e.getLocalizedMessage()
				);
				logger.error("error occurred ", e);
			}
		}
	}
}
