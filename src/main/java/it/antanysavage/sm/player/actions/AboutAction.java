package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.dialogs.AboutDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class AboutAction extends Action {

	public AboutAction() {
		super( LocaleManager.getText(LocaleManager.APP_MENU_HELP_ABOUT) , AS_PUSH_BUTTON);
	}
	
	public void run() {
		AboutDialog dialog = new AboutDialog();
		dialog.open();
	}
}
