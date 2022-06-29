package it.antanysavage.sm.player.timertasks;

import it.antanysavage.sm.player.swt.players.IPlayer;
import it.antanysavage.sm.player.swt.players.MPlayerVideoPlayer;

import java.util.TimerTask;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class MplayerAlphaTimerTask extends TimerTask{
	
	private static Logger logger = LoggerFactory.getLogger( MplayerAlphaTimerTask.class);
	
	private IPlayer player;
	private int alpha;
	
	public MplayerAlphaTimerTask(final IPlayer  player) {
		this.player = player;
		this.alpha  = 0;
		Display.getDefault().syncExec(
				new Runnable() {						
					public void run() {									
						player.getComposite().getShell().setAlpha(0);
					    logger.debug("setted alpha : " + player.getComposite().getShell().getAlpha() );
					}
				}
		);
	}

	@Override
	public void run() {
		if ( alpha <= 255 ) {
			StringBuffer sb = new StringBuffer();
			Display.getDefault().syncExec(
					new Runnable() {						
						public void run() {							
							player.getComposite().getShell().setAlpha(alpha);
							logger.debug("setted alpha : " + player.getComposite().getShell().getAlpha());
						}
					}
			); 			
			alpha += 2; 			
		} else {
			cancel();
		}
	}

}
