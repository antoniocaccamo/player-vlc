package it.antanysavage.sm.player;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewsComposite extends Composite {

	
	
	private Timer timer;

	private StringBuffer sb = new StringBuffer();
	
	private static final String MSG = "text scrolling demo .. do you like it ?";
	
	private boolean first = true;

	private Browser browser;
	
	private static final Logger logger = LoggerFactory.getLogger( NewsComposite.class);

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		NewsComposite inst = new NewsComposite(shell);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public NewsComposite(Composite parent) {
		super(parent, SWT.NONE);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(287, 223);
			browser = new Browser(this, SWT.NONE);
			
			GridData browserLData = new GridData();
			browserLData.grabExcessVerticalSpace = true;
			browserLData.grabExcessHorizontalSpace = true;
			browserLData.horizontalAlignment = GridData.FILL;
			browserLData.verticalAlignment = GridData.FILL;
			browser.setLayoutData(browserLData);
//			browser.setUrl("C:/development/java/workspaces/arttech/player/src/test/resources/html/weather.html");
//			String url = "C:\\development\\java\\workspaces\\arttech\\player\\src\\test\\resources\\html\\m.html";
//			browser.setUrl(url);
//			browser.getHorizontalBar().setVisible(false);
//			browser.getVerticalBar().setVisible(false);
			
//				newsCanvas.setBackground(SWTResourceManager.getColor(128, 255, 128));
//				timer = new Timer();
//				timer.schedule( new ScrollingCompositeTask(), 500, 100);
			
			browser.addControlListener(new ControlListener() {

				public void controlResized(ControlEvent e) {
					logger.error("controlResized to " + browser.getSize() );
					browser.execute("document.getElementById('body').style.width= "+ (browser.getSize().x )   +";");
					browser.execute("document.getElementById('body').style.height= "+ (browser.getSize().y )   +";");

				}

				public void controlMoved(ControlEvent e) {
				}

				
			});
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private class ScrollingCompositeTask extends TimerTask {
//		@Override
//		public void run() {
//			Display.getDefault().asyncExec(
//					new Runnable() {						
//						public void run() {
//							newsCanvas.redraw();
//						}
//					}
//				);
//		}
//	}
//	
//	private class ScrollingListener implements Listener {
//		public void handleEvent(Event evt) {
//			if ( first ){				
//				int averageCharWidth = evt.gc.getFontMetrics().getAverageCharWidth();
//				int spaces = newsCanvas.getSize().x / averageCharWidth;
////				System.err.println("averageCharWidth " + averageCharWidth +
////						", bottomNewsCanvas.getSize().x "+ newsCanvas.getSize().x  +
////						", spaces : " + spaces 
////					);
//				for( int i = 0; i < spaces; i++ ){
//					sb.append("  ");
//				}
//				sb.append(MSG);
////				System.err.println("sb [" + sb.toString() + "]");
//			} 
//			else {
//				String ff = sb.substring(0, 1);
//				sb = sb.deleteCharAt(0).append(ff);
//			} 
//			first = false;			
//			Point textSize = evt.gc.textExtent(sb.toString());
//			evt.gc.drawText(
//					sb.toString()                                      , 
//					0 , 
//					(newsCanvas.getSize().y - textSize.y) / 2 ,
//					true
//			);
//		}
//	}
	
	public void show () {
			URL url = NewsComposite.class.getClassLoader().getResource("news/index.html");
			browser.setUrl(url.toString());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
		
	}

}
