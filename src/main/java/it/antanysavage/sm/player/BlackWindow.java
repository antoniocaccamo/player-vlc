package it.antanysavage.sm.player;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class BlackWindow  {
	
	private static Color black  = new Color(Display.getCurrent(), 0, 0, 0);

	
	public static void main(String[] args) {
	    Window window = new ApplicationWindow(null);
	    window.setBlockOnOpen(true);
	   
	    window.open();
	    window.getShell().setBackground(black);
	   
	  }


}
