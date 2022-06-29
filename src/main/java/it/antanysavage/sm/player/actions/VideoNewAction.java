package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.util.Utils;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabItem;

public class VideoNewAction extends Action {

	private ProgramManager programManager;

	public VideoNewAction(ProgramManager programManager) {
		super(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_NEW));
		this.programManager = programManager;
	}

	public void run() {
		String text = getText();
		int idx = text.indexOf("@");
		if ( idx!= -1) {
			text = text.substring(0, idx);
		}
		CTabItem item = programManager.getProgramTabFolder().getSelection();
		ProgramTab programTab = (ProgramTab) item.getControl();
		
		Utils.insertMedia(programManager.getShell(), programTab, text);
//		programTab.getVideosOfSequencesTableViewer().setInput( programTab.getProgram().getVideos() );
//		programTab.getVideosOfSequencesTableViewer().refresh();
		
		/*
		boolean ok = false;
		IStructuredSelection selection = 
			(IStructuredSelection) programManager.getSequecensListViewer().getSelection();
		if ( selection != null){			
			Program program = (Program) selection.getFirstElement();
			if ( program != null) {
				
//				if ( smp.isPlayed() ) {
//					MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
//					mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + this.getText());
//					mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_IN_USE));
//					mb.open();
//					return ;
//				}
				
				sequenceManager.getVideosOfSequencesTableViewer().setInput(program.getVideos());
				sequenceManager.getVideosOfSequencesTableViewer().refresh();
//				VideoWindow  vc = new VideoWindow(sequenceManager);
				Point p  = programManager.getShell().getLocation();
				p.x += 10;
				p.y += 10;
//				vc.create();
//				vc.getShell().setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + this.getText() );
//				vc.getShell().setLocation(p);
//				vc.getShell().getSize().x = 400;
//
//				vc.setSequenceSMP(smp);
//				vc.getShell().setVisible(true);
//				vc.init();
//				vc.getShell().forceFocus();
//				ok = true;
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
				NewMediaMultiComposite inst = new NewMediaMultiComposite(shell, SWT.NONE , program);
				shell.setLayout(new FillLayout());
				shell.layout();
				
				shell.setText( text );
				Point size = inst.getSize();
				Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
				shell.setSize(shellBounds.width, shellBounds.height);
				shell.setLocation(p);
				inst.pack();
				shell.pack();
				inst.forceFocus();
				shell.setImage(Player.LOGO_IMAGE);
				shell.open();
				this.setEnabled(false);
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}				
				ok = true;
				this.setEnabled(true);
				sequenceManager.getStatusLineManager().setMessage( Utils.getSequenceDescription(program));				
			}
		} 
		if ( ! ok ) {
//			MessageBox mb = new MessageBox(sequenceManager.getShell(), SWT.OK | SWT.ICON_WARNING);
//			mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + this.getText());
//			mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION));
//			mb.open();
			MessageDialog.openError(sequenceManager.getShell(), 
					LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | " + text, 
					LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION)
			);
		} 
		*/

	}
}
