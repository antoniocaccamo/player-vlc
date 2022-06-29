package it.antanysavage.sm.player;

import it.antanysavage.sm.player.ReferWindow.ReferWindowControlAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ReferComposite extends Composite {

	private ControlAdapter referWindowControlAdapter = new ReferWindowControlAdapter();

	private ScreenManagerComposite managerComposite;

	
	
	private static Color black  = new Color(Display.getCurrent(), 0, 0, 0);

	public ReferComposite(ScreenManagerComposite managerComposite) {
		super( new Shell(managerComposite.getDisplay(), SWT.SHADOW_ETCHED_IN | SWT.ON_TOP), SWT.NULL);
		this.managerComposite = managerComposite;
		getShell().setBackground(black);
//		setBackground(black);
		setVisible(false);
	}

	public void close() {
		getShell().dispose();
	}

	
	
	public static void main (String [] args) {
		
		ReferComposite rc = new ReferComposite(null);
		
		
	}
	
	public void addAdpter() {
		addControlListener(referWindowControlAdapter);
	}
	
	public void removeAdpter() {
		removeControlListener(referWindowControlAdapter);
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


	public void create() {
		;
		
	}

//	public Control getShell() {
//		
//		return shell;
//	}

	public void show(final Point size, final Point location) {
		
		
				setSize(size);
				setLocation(location);
				setVisible(true);
				addAdpter();
		
	}
	
	public void notShow() {
		
				setVisible(false);
				removeAdpter();
			
	
	}

	
	
}
