package it.antanysavage.sm.player.actions;

import java.util.Iterator;

import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class DeleteMediaAction extends Action {

	private SequenceManager sequenceManager;

	public DeleteMediaAction(SequenceManager sequenceManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DELETE));
		this.sequenceManager = sequenceManager;
	}

	public void run() {
		String text = this.getText();
		int idx = text.indexOf("@");
		if ( idx!= -1) {
			text = text.substring(0, idx);
		}




		//		IStructuredSelection selection = 
		//			(IStructuredSelection) this.sequenceManager.getSequecensListViewer().getSelection();
		//		if ( selection != null) {			
		//			Program smp = (Program) selection.getFirstElement();
		//			if ( smp != null ) {
		//				if ( smp.isPlayed() ) {
		//					MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
		//					mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text);
		//					mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_IN_USE));
		//					mb.open();
		//					return ;
		//				}
		IStructuredSelection	selection = 
			(IStructuredSelection) this.sequenceManager.getVideosOfSequencesTableViewer().getSelection();
		if ( selection != null ) {
			if ( ! MessageDialog.openQuestion(
					sequenceManager.getShell(), 
					LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 	
					LocaleManager.getText(LocaleManager.WARNINGS_ARE_YOU_SURE)) ) {
				return ;
			}
			Iterator it = selection.iterator();					
			while ( it.hasNext() ) {						
				Media vv = (Media) it.next();
				if ( vv != null ) {				

					Program smp = vv.getMyProgram();

					if ( smp.removeVideo(vv) ) {
						sequenceManager.getVideosOfSequencesTableViewer().remove(vv);
						sequenceManager.getVideosOfSequencesTableViewer().refresh();
					} else {
						//								MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
						//								mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text);
						//								mb.setMessage("The movie with id ["+vv.getId()+"] is not in the sequence ["+smp.getName()+"]");
						//								mb.open();
						MessageDialog.openError(sequenceManager.getShell(), 
								LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 
								"The movie with id ["+vv.getId()+"] is not in the sequence ["+smp.getName()+"]"
						);
					}
				}
			}
		}

		else {
			//						MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
			//						mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text);
			//						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_VIDEO_NO_SELECTION));
			//						mb.open();
			MessageDialog.openError(sequenceManager.getShell(), 
					LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 
					LocaleManager.getText(LocaleManager.ERRORS_VIDEO_NO_SELECTION)
			);
		}
	}

	//			} else {
	//				MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
	//				mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text);
	//				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION));
	//				mb.open();
	//				MessageDialog.openError(sequenceManager.getShell(), 
	//						LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 
	//						LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION)
	//				);
	//			}
	//		}
 
}


