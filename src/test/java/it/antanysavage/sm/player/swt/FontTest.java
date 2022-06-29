package it.antanysavage.sm.player.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FontTest {

	/**
	 * @param args
	 */
	public static void test(String[] args) {
		//FontData fontData = new FontData("Arial", 12, SWT.NORMAL);
		FontData fontData = new FontData("1|Arial|12.0|0|WINDOWS|1|0|0|0|0|0|0|0|0|1|0|0|0|0|Arial");
		System.out.println( fontData.toString());

	}
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setBounds(10, 10, 200, 200);
		Text text = new Text(shell, SWT.MULTI);
		text.setBounds(10, 10, 150, 150);
		FontDialog fontDialog = new FontDialog(shell);
		FontData fontData = fontDialog.open();
		System.out.println( "fontdata : " + fontData);
		System.out.println( "fontdata : " + fontData.getName() + ", " + fontData.getStyle() + ", " + fontData.getHeight());		
		Font newFont = new Font(display, fontData);
		text.setFont(newFont);
		GC gc = new GC(text);
	    FontMetrics fm = gc.getFontMetrics();	    
		System.out.println( "fm.getLeading() : " + fm.getLeading() );
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		newFont.dispose();
		display.dispose();
	}


}
