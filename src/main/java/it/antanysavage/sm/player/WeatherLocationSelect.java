package it.antanysavage.sm.player;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;

/**An example using Google Map JavaScript widget in SWT. It shows how to access Javascript object from Java.
 *
 */
public class WeatherLocationSelect {

	static List list;

	static Browser browser;

	static  DecimalFormat decimalFormat = new DecimalFormat ("#0.000" );


	public static void main(String [] args) throws IOException {
		decimalFormat.setDecimalFormatSymbols( new DecimalFormatSymbols(Locale.ENGLISH));
		File f = new File("C:\\development\\java\\workspaces\\arttech\\player\\src\\main\\resources\\html\\geo.html");
		if(!f.exists()){
			System.out.println("file not exist! " + f.getAbsolutePath()); 
			return;
		}
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("GMap Locator");
		shell.setSize(350, 450);
		shell.setLayout(new GridLayout(1, false));
		
		Group grpLatLong = new Group(shell, SWT.NONE);
		grpLatLong.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpLatLong.setText("Lat Long");
		grpLatLong.setLayout(new GridLayout(2, false));
		browser = new Browser(grpLatLong, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		browser.setSize(324, 279);
		browser.addControlListener(new ControlListener() {

			public void controlResized(ControlEvent e) {
				browser.execute("document.getElementById('map_canvas').style.width= "+ (browser.getSize().x )   +";");
				browser.execute("document.getElementById('map_canvas').style.height= "+ (browser.getSize().y )   +";");
				//browser.execute("document.getElementById('marquee').style.width= " + (browser.getSize().x - 20)  +";");
				//browser.execute("document.getElementById('marquee').style.height= "+ (browser.getSize().y - 20)  +";");
			}

			public void controlMoved(ControlEvent e) {
			}
		});
		
				browser.setUrl(f.toURI().toString());
				Button b = new Button(grpLatLong, SWT.PUSH);
				b.setSize(82, 25);
				b.setText("Where Am I ?");
				list = new List(grpLatLong, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
				list.setSize(340, 85);
				new Label(grpLatLong, SWT.NONE);
				new Label(grpLatLong, SWT.NONE);
				list.addMouseListener(
						new MouseListener() {
							
							public void mouseUp(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							public void mouseDown(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							public void mouseDoubleClick(MouseEvent arg0) {
								if ( list.getSelectionCount() != 0  ) {
									String[] selections = list.getSelection(); 
								}
								
							}
						}
				);
				
						b.addSelectionListener(new SelectionAdapter() {
				
							public void widgetSelected(SelectionEvent e) {
								browser.evaluate("codeLatLng()");
				//				System.out.println("WeatherLocationSelect.main(...).new SelectionAdapter() {...}.widgetSelected() | hasExcuted " + hasExcuted);
								double lat = ((Double) browser.evaluate("return map.getCenter().lat();")).doubleValue();
								double lng = ((Double) browser.evaluate("return map.getCenter().lng();")).doubleValue();		
								String address = (String) browser.evaluate("return address") ;
				//				browser.execute("return trovaindirizzo();");
								//browser.evaluate("return trovaindirizzo();");
								list.add( decimalFormat.format(lat) + " : " + decimalFormat.format(lng)  + " - " + address);
							}
						});

		try {
//			browser.getHorizontalBar().setVisible(false);
//			browser.getVerticalBar().setVisible(false);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}

