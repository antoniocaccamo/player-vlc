package it.antanysavage.sm.player;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ReferWindow extends ApplicationWindow {
	
	private ScreenManagerComposite managerComposite;
	
	private ControlAdapter referWindowControlAdapter = new ReferWindowControlAdapter();
	
	private static Color black  = new Color(Display.getCurrent(), 0, 0, 0);

	public ReferWindow( ScreenManagerComposite managerComposite) {
		super(null);
		setShellStyle(SWT.ON_TOP | SWT.SHADOW_ETCHED_IN);
		this.managerComposite = managerComposite;
	}
	
	@Override
	protected Control createContents(Composite parent) {
//		Composite composite = new Composite(parent, SWT.NONE);
		
//		parent.setLayout( new FillLayout());

		parent.setBackground( black );
		
		getShell().setVisible(false);
		
		return parent;
	}
	
	public void addAdpter() {
		getShell().addControlListener(referWindowControlAdapter);
	}
	
	public void removeAdpter() {
		getShell().removeControlListener(referWindowControlAdapter);
	}
	
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = super.createMenuManager();
		menuManager.setVisible(false);
		
		
		return menuManager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReferWindow wwin = new ReferWindow(null);
		wwin.setBlockOnOpen(true);
		wwin.open();
		wwin.getContents().forceFocus();
		Display.getCurrent().dispose();

	}
	
	public class ReferWindowControlAdapter extends ControlAdapter {
		@Override
		public void controlResized(ControlEvent arg0) {
			if ( managerComposite == null)
				return;
			Point size = getShell().getSize();
			Point location = getShell().getLocation();
			if ( managerComposite.hasPlayerSetting())
				managerComposite.changeSpinners(size, location);
		}
	}

}
