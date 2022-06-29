package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.swt.views.HelpWindow;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ManualAction extends Action {
	
	private Player smPlayer;

	public ManualAction(Player smPlayer) {
		super(LocaleManager.getText(LocaleManager.APP_MENU_HELP_MANUAL), AS_PUSH_BUTTON);
		this.smPlayer = smPlayer;
	}
	
	public void run() {
		Point p = smPlayer.getShell().getLocation();
		p.x += 10;
		p.y += 10;
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		HelpWindow helpComposite = new HelpWindow(shell, SWT.NONE | SWT.APPLICATION_MODAL);
		shell.setLayout(new FillLayout());
		shell.layout();		
		shell.setText( getText() );
		Point size = new Point(800, 300);
		Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
		shell.setSize(shellBounds.width, shellBounds.height);
		shell.setLocation(p);
		helpComposite.pack();
		shell.pack();
		helpComposite.forceFocus();
		shell.setImage(Player.LOGO_IMAGE);
		shell.open();
		this.setEnabled(false);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}				
		this.setEnabled(true);
	}

}
