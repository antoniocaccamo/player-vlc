package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.sequences.model.Media;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class PhotoComposite extends Composite{	

	private static Logger logger = LoggerFactory.getLogger(PhotoComposite.class);

	

	private Image image;

	public PhotoComposite(Composite parent) {
		super(parent, SWT.NONE);
		getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
	

		GridLayout thisLayout = new GridLayout(1,false);
		
		//		this.addControlListener(new ControlAdapter() {
		//			public void controlResized(ControlEvent evt) {
		//				if ( image != null ){
		//					resize();
		//				}
		//			}
		//		});
		GridData gridData = new GridData();
		gridData.horizontalSpan = GridData.FILL;
		gridData.verticalSpan = GridData.FILL;		
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;

		



		//		addControlListener( new ControlAdapter() {
		//			@Override
		//			public void controlResized(ControlEvent arg0) {
		//				if ( image != null) {
		//					resize();
		//				}
		//			}
		//		});

	}

	public void set(final Media media ) {
		Display.getDefault().syncExec(
				new Runnable() {					
					public void run() {
						Point clientArea = new Point (getParent().getClientArea().width,getParent().getClientArea().height);
						media.resizeTo(clientArea);
						PhotoComposite.this.setBackgroundImage( media.getImage() );					
					}
				}
		);
	}

	

}
