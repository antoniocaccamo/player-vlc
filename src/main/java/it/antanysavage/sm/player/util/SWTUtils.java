package it.antanysavage.sm.player.util;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class SWTUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SWTUtils.class);
	
	public static Color getColor(RGB rgb) {				
		return new Color( Display.getCurrent(), rgb) ;
	}


	public static Image resizeImage(Image image, int width, int height) {

		logger.debug("init resize");

		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
				image.getBounds().width, image.getBounds().height, 
				0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		logger.debug("end  resize");
		return scaled;
	}


	public static Image getImage(String photoAbsolutePath) {
		return new Image(Display.getDefault(), photoAbsolutePath );
	}


	public static Color getColor(int i, int j, int k) {		
		return getColor( new RGB(i, j, k));
	}

}
