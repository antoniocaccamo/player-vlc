package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class OpenSequenceAction extends Action {
	
	private static Logger logger = LoggerFactory.getLogger(OpenSequenceAction.class);
	

	private SequenceManager sequenceVideoManager;
	
	public OpenSequenceAction(SequenceManager sequenceVideoManager ) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_OPEN));
		this.sequenceVideoManager = sequenceVideoManager;
	}
	
	public void run() {		
		
		FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.OPEN );
		fileDialog.setFilterPath(System.getProperty("user.dir"));
		fileDialog.setFilterExtensions(new String[] {"*.xseq"});
		String sequenceFile = fileDialog.open();
		if ( sequenceFile != null && ! "".equals(sequenceFile) ) {
		
					
				try {					
					Sequence sequence = SequenceFileManager.read(sequenceFile);
					Program smp = new Program(sequence, sequenceFile);										
					this.sequenceVideoManager.getStatusLineManager().setMessage(smp.toString());
					this.sequenceVideoManager.getSmPlayer().addSequence(smp);
					this.sequenceVideoManager.updateSequences();	
					this.sequenceVideoManager.getVideosOfSequencesTableViewer().setInput(smp.getVideos());
					this.sequenceVideoManager.getVideosOfSequencesTableViewer().refresh();
				}catch (Exception e) {
					this.sequenceVideoManager.getStatusLineManager().setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + " | File ["+sequenceFile +"]");
					logger.error("error occurred ", e);
				}
			
		}
	}

}
