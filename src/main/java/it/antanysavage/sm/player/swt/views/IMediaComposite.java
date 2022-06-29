package it.antanysavage.sm.player.swt.views;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public abstract class IMediaComposite extends Composite {
	
	protected static Color black  = new Color(Display.getCurrent(), 0, 0, 0);
	
	private static final Logger logger = LoggerFactory.getLogger(IMediaComposite.class);
	
	public IMediaComposite(Composite parent) {
		super(parent, SWT.NONE);
		setBackground( black );
//		getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
//		hide();
	}
	
	public abstract void init();
	
	public void hide() {
//		Display.getDefault().syncExec(
//				new Runnable() {					
//					public void run() {
//						IMediaComposite.this.setVisible(false);
//						logger.info("hide");
//					}
//				}
//		);

	}

	public void show() {
//		Display.getDefault().syncExec(
//				new Runnable() {					
//					public void run() {
//						IMediaComposite.this.setVisible(true);
//						logger.info("show");
//					}
//				}
//		);
	}

}
