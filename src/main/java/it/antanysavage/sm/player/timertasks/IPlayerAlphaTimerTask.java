package it.antanysavage.sm.player.timertasks;

import it.antanysavage.sm.player.swt.players.IPlayer;

import java.util.TimerTask;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.Display;

public class IPlayerAlphaTimerTask extends TimerTask{

	private static Logger logger = LoggerFactory.getLogger( IPlayerAlphaTimerTask.class);

	private IPlayer player;
	private int alpha;

	public IPlayerAlphaTimerTask(final IPlayer player) {
		this.player = player;
		this.alpha  = 20;
		if ( player.getComposite() != null ) {			
			Display.getDefault().syncExec(
					new Runnable() {						
						public void run() {									
							player.getComposite().getShell().setAlpha(0);
							logger.debug("setted alpha : " + player.getComposite().getShell().getAlpha() );
						}
					}
			); 				
		}
	}

	@Override
	public void run() {

		if ( player == null )
			return;

		if (player.getComposite() == null ) {			
			cancel();
		}

		if ( alpha <= 255 ) {
			StringBuffer sb = new StringBuffer();
			Display.getDefault().syncExec(
					new Runnable() {						
						public void run() {				
							if ( player != null && player.getComposite()!= null && player.getComposite().getShell() != null  ) {
								player.getComposite().getShell().setAlpha(alpha);
								logger.debug("setted alpha : " + player.getComposite().getShell().getAlpha() );
							}
						}
					}
			); 			
			alpha += 5; 			
		} 
	}

}
