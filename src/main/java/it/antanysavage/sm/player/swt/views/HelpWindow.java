package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.bundle.LocaleManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class HelpWindow extends org.eclipse.swt.widgets.Composite {
	
	private static Logger logger = LoggerFactory.getLogger(HelpWindow.class);
	
	private static final String INDEX = "help/index.html";
	
	private static final String INDEX_IT = "help/index_it.html";
	
	private Browser browser;

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
		HelpWindow inst = new HelpWindow(shell, SWT.NULL);
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

	public HelpWindow(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, SWT.NONE);
		initGUI();
	}

	private void initGUI() {
		
		byte[] buffer = new byte[2048];
		int bytes;
		setLayout(new GridLayout(1, false));

		browser = new Browser(this, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		FillLayout browserLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
		browser.setLayout(browserLayout);
//		InputStream is = getClass().getClassLoader().getResourceAsStream(INDEX);
		URL url = getClass().getClassLoader().getResource(INDEX);
		if ( "it".equals(LocaleManager.getLocale().getCountry()) ) {
			url = getClass().getClassLoader().getResource(INDEX_IT);
		}
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
//			while ( (bytes = is.read(buffer, 0, buffer.length)) > -1) {
//				baos.write(buffer, 0, bytes);
//			}
//			browser.setText(baos.toString());
			browser.setUrl(url.toString());
			pack();
		} catch (Exception e) {
			logger.error("error occurred ", e);
		}


	}

}
