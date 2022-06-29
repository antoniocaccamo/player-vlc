package it.antanysavage.sm.player.actions;

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

public class ProgramOpenFTPAction extends Action {

	private static Logger logger = LoggerFactory.getLogger(ProgramOpenFTPAction.class);
	private ProgramManager programManager;

	public ProgramOpenFTPAction(ProgramManager programManager ) {
		super("Remote Open");
		this.programManager = programManager;
	}

	public void run() {
		String text = getText();
		int idx = text.lastIndexOf("@");
		if ( idx!= -1) {
			text = text.substring(0, idx);
		}
		Utils.programFTP(programManager, text );

	}

}
