package it.antanysavage.sm.player.timertasks;

import it.antanysavage.sm.player.swt.players.IPlayer;

import java.util.TimerTask;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.Display;

public class IPlayerTask extends TimerTask {
	
	private static Logger logger = LoggerFactory.getLogger(IPlayerTask.class);
	
	private long start;		
	
	private long paused;
	
	private long duration;
	
	private long actual = 0;

	private IPlayer player;
	
	
	public IPlayerTask(  IPlayer player, long duration ) {
		this(player, 0, duration);
	}
	
	public IPlayerTask( IPlayer player, long paused, long duration) {
		this.start    = System.currentTimeMillis();
		this.duration = duration;
		this.paused   = paused;
		this.player   = player;
		//player.getPlayerMaster().getScreenManager().resetVideoProgressBarMaximum();
	}

	@Override
	public void run() {
		actual = System.currentTimeMillis() - start + paused;		
		if ( actual < duration ) {
			double aa = Double.valueOf(actual)    / 1000;
			double dd = Double.valueOf(duration)  / 1000; 
		    logger.debug("updating video progress bar : [" + aa  + " / " + dd + "] ");
			player.getPlayerMaster().getScreenManager().updateVideoProgressBarSelection( aa, dd /*(int) x*/ );
		} else {			
			cancel();
			player.next();
		}
	}
	
//	public void setDuration( ) {
//		this.duration = duration;
//	}
	
	public long getActual() {
		return actual;
	}
	
}
