package it.antanysavage.sm.player.swt;

import java.util.ArrayList;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.NewsPlayerMaster;
import it.antanysavage.sm.player.ScreenManagerComposite;
import it.antanysavage.sm.player.sequences.model.MediaPlayer;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.swt.players.BlackWindowPlayer;
import it.antanysavage.sm.player.swt.players.HiddenWindowPlayer;
import it.antanysavage.sm.player.swt.players.MPlayerVideoPlayer;
import it.antanysavage.sm.player.swt.players.PhotoPlayer;
import it.antanysavage.sm.player.swt.players.WatchPlayer;
import it.antanysavage.sm.player.swt.views.BlackComposite;
import it.antanysavage.sm.player.swt.views.MPlayerVideoComposite;
import it.antanysavage.sm.player.swt.views.PhotoComposite;
import it.antanysavage.sm.player.swt.views.WatchComposite;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class PlayerScreenComposite extends Composite {
	
	private ArrayList<MediaPlayer> mediaPlayersList = new ArrayList<MediaPlayer>();
	
	private ScreenManagerComposite screenManagerComposite;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}

	private StackLayout playerScreenCompositeLayout;

	private IMaster playerMaster;
	
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
		PlayerScreenComposite inst = new PlayerScreenComposite(null, shell);
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

	public PlayerScreenComposite(IMaster playerMaster , Composite parent) {		
		super(parent, SWT.NONE);
		this.playerMaster = playerMaster;
		initGUI();
	}

	private void initGUI() {
		try {
			playerScreenCompositeLayout = new StackLayout();
			setLayout(playerScreenCompositeLayout);
			
			/*
			 * movie
			 */

			MediaPlayer mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.VIDEO);
//			logger.info("MPLAYER_MODE : " + Player.MPLAYER_MODE);
//			if ( Player.MPLAYER_MODE ) {
				mp.setComposite(new MPlayerVideoComposite(this));
				mp.setPlayer( new MPlayerVideoPlayer(playerMaster,  mp.getComposite()));	
//			} else {
//				mp.setComposite(new QTMovieComposite(programComposite));
//				mp.setPlayer(new QTMoviePlayer(this, mp.getComposite()));
//			}				

			mediaPlayersList.add(mp);

			/*
			 * hidden window
			 */

			BlackComposite blackComposite = new BlackComposite(this);	
			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.HIDDENWINDOW);
			mp.setComposite(blackComposite);
			mp.setPlayer( new HiddenWindowPlayer(playerMaster));
			mediaPlayersList.add(mp);

			/*
			 * black
			 */

			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.BLACKWINDOW);
			mp.setComposite(blackComposite);
			mp.setPlayer( new BlackWindowPlayer(playerMaster, blackComposite));
			mediaPlayersList.add(mp);


			/*
			 * watch 
			 */	

			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.WATCH);		
			WatchComposite watchComposite = new WatchComposite( screenManagerComposite, this);
			Thread thread = new Thread(watchComposite);
			thread.start();
			mp.setComposite(watchComposite);
			mp.setPlayer( new WatchPlayer(playerMaster, watchComposite));
			//		AnalogClockComposite clockComposite = new AnalogClockComposite(programComposite);
			//		mp.setComposite(clockComposite);
			//		mp.setPlayer( new AnalogClockPlayer(this, clockComposite) );
			mediaPlayersList.add(mp);



			/*
			 * photo
			 */
			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.PHOTO);	
			PhotoComposite photoComposite = new PhotoComposite(this);	
			mp.setComposite(photoComposite);
			mp.setPlayer( new PhotoPlayer(playerMaster, photoComposite));
			mediaPlayersList.add(mp);

			/*
			 * web spot
			 */

			//		mp = new MediaPlayer();
			//		mp.setType(AcceptedVideoTypes.WEBSPOT);
			//		WebSpotComposite webSpotComposite = new WebSpotComposite(programComposite);
			//		mp.setComposite(webSpotComposite);
			//		mp.setPlayer( new WebSpotPlayer(this, webSpotComposite) );
			//		mediaPlayersList.add(mp);


			/*
			 * ftp movie
			 */

			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.FTPVIDEO);
//			if ( Player.MPLAYER_MODE ) {
				mp.setComposite(new MPlayerVideoComposite(this));
				mp.setPlayer( new MPlayerVideoPlayer(playerMaster,  mp.getComposite()));	
//			} else {
//				mp.setComposite(new QTMovieComposite(programComposite));
//				mp.setPlayer(new QTMoviePlayer(this, mp.getComposite()));
//			}	
			mediaPlayersList.add(mp);

			/*
			 * ftp photo
			 */
			mp = new MediaPlayer();
			mp.setType(AcceptedVideoTypes.FTPIMAGE);	
			mp.setComposite( photoComposite );
			mp.setPlayer( new PhotoPlayer(playerMaster, photoComposite));
			mediaPlayersList.add(mp);

			/*
			 * set on top black window
			 */
			playerScreenCompositeLayout.topControl = blackComposite;
			layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
