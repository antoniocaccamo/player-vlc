package it.antanysavage.sm.player.swt.views;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;

public class MPlayerVideoComposite extends IMediaComposite{
	// You'll have to edit these paths to something useful.
	private static Logger logger = LoggerFactory.getLogger(MPlayerVideoComposite.class);
	public Canvas wVideo;
	
	public int alpha = 0 ;
	
	public MPlayerVideoComposite(Composite composite) {
		super(composite);
		
//		composite1 = new Composite(composite12, SWT.NONE);
		GridLayout thisLayout = new GridLayout(1, false);
//		composite1Layout.makeColumnsEqualWidth = true;
		setLayout(thisLayout);
		GridData gridData = new GridData();
		gridData.horizontalSpan = GridData.FILL;
		gridData.verticalSpan = GridData.FILL;		
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;

		wVideo = new Canvas(this, SWT.INHERIT_DEFAULT);
		GridData wVideoLData = new GridData();
		wVideoLData.grabExcessVerticalSpace = true;
		wVideoLData.grabExcessHorizontalSpace = true;
		wVideoLData.verticalAlignment = GridData.FILL;
		wVideoLData.horizontalAlignment = GridData.FILL;
		wVideo.setLayoutData(wVideoLData);
		
		wVideo.setBackground(new Color(Display.getCurrent(), new RGB(0x10, 0x10, 0x10)));
//		wVideo.getShell().setAlpha(0);
		
		Player.moveMouse(wVideo);
		Player.moveMouse(wVideo.getShell());
	}
	
	@Override
	public void init() {
	

				
		
	}


	public String getColorKey() {
		return "0x010101";
	}
	
	public String getVideoHandle() throws Exception {		
		return String.valueOf(wVideo.getClass().getField("handle").getLong(this));
	}
	
	public void hide() {
		Display.getDefault().syncExec(
				new Runnable() {					
					public void run() {
					MPlayerVideoComposite.this.setVisible(false);
					logger.debug("mplayer hide");
					}
				}
		);
		
	}
	
	public void show() {
		Display.getDefault().syncExec(
				new Runnable() {					
					public void run() {
						MPlayerVideoComposite.this.setVisible(true);
						logger.debug("mplayer show");
					}
				}
		);
	}
}
