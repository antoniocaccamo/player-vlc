package it.antanysavage.sm.player.swt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public class Player2
{
	//You'll have to edit these paths to something useful.
	private static final String MPLAYER=" /usr/bin/mplayer";
	private static final String VIDEO_FILE =" /usr/A.rmvb";
	
//	public static Process setFile (final File pFile, final int hWnd) throws IOException	{
//		//call the Mplayer command line
//String[] String[]  = {	MPLAYER, //mplayer path
//		" -vo", " x11", //linux can only use X11 and XV there is a God code, windows using directX
//		" -identify", // output, details
//		" -slave", //slave mode to play
//		" -wid", String.valueOf (hWnd), // video window handle
//		" -colorkey", " 0x010101", // video window background color
//		" -osdlevel", String.valueOf (1), //osd style
//		VIDEO_FILE// playback file path
//		};
//		final lProcess = Runtime.getRuntime (Process).Exec (CMD);
//		final InputStream stderr = lProcess.getErrorStream ();
//		final InputStream stdin = lProcess.getInputStream ();
//		new Thread (New Runnable () {
//			public void run () {
//				try {
//					Final BufferedReader lReader = new BufferedReader (New InputStreamReader (stderr, " UTF-8"));
//					For (String L = lReader.readLine (); l! = null; L = lReader.readLine ()) {
//						System.out.println (L);
//					}
//				} catch (Throwable T) {
//					(t.printStackTrace);
//				}
//			}
//		}.Start ());
//		New Thread (New Runnable () {
//			Public void run () {
//				Try {
//					Final BufferedReader lReader = new BufferedReader (New InputStreamReader (stdin, " UTF-8"));
//					For (String L = lReader.readLine (); l! = null; L = lReader.readLine ()) {
//						System.out.println (L);
//					}
//				} catch (Throwable T) {
//					(t.printStackTrace);
//				}
//			}
//		}.Start ());
//		Return lProcess;
//	}
//	Public static void main (String[] pArgs)
//	Throws Exception
//	{
//		Final Display wDisplay = new Display ();
//		Final Shell wShell = new Shell (wDisplay, SWT.SHELL_TRIM);
//		/ / in Mplayer you set the background color to many, here you should set the playback window background color for many.
//		Final Color bkColor = new Color (null, 0x01,0x01,0x01);
//				Final FillLayout wLayout = new FillLayout ();
//				Composite videoComposite;
//				VideoComposite = new Composite (wShell, SWT.EMBEDDED);
//				VideoComposite.setLayout (wLayout);
//				VideoComposite.setBackground (bkColor);
//				VideoComposite.setBounds (New Rectangle (0, 51, 720, 480));
//				WShell.setLayout (wLayout);
//				WShell.setSize (800, 600);
//				(wShell.layout);
//				WShell.setVisible (true);
//				Int han=0;
//				//handle, windows use.Handle to get the window handle.But the linux using embeddedHandle to obtain handle, otherwise there would be
//				/ * *
//				* X11 error: BadDrawable (invalid Pixmap or Window parameter)
//				* X11 error: BadWindow (invalid Window parameter)
//				* this mistake
//				* /
//				Han=videoComposite.embeddedHandle;
//				System.out.println (Han);
//				Final Process lProcess = setFile (New File (VIDEO_FILE),
//						Han);
//				While () {wShell.isDisposed (!)
//					If () {wDisplay.readAndDispatch (!)
//					(wDisplay.sleep);
//				}
//				}
//				(lProcess.destroy);
//	}
}
