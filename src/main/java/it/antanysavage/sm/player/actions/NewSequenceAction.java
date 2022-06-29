package it.antanysavage.sm.player.actions;

import java.io.File;

import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class NewSequenceAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(NewSequenceAction.class);

	private SequenceManager sequenceManager;

	public NewSequenceAction(SequenceManager sequenceManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_NEW));
		this.sequenceManager = sequenceManager;
	}

	public void run() {		

		FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.SAVE );
		fileDialog.setFilterExtensions(new String[] {"*.xseq"});
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
					Sequence sequence = new Sequence();
					sequence.setName(name);
					sequence.setVideos( new Videos() );

					Program smp = new Program(sequence, f.getPath());
					this.sequenceManager.getStatusLineManager().setMessage(smp.toString());
					this.sequenceManager.getSmPlayer().addSequence(smp);
					this.sequenceManager.updateSequences();
					
					SequenceFileManager.write(smp);
				}
			} catch (Exception e) {
				logger.error("" ,e);
			}
		}

	}


}
