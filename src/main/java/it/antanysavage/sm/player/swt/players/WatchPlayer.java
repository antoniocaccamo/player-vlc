package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.swt.views.WatchComposite;
import it.antanysavage.sm.player.timertasks.IPlayerTask;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;

public class WatchPlayer extends IPlayer{

	private WatchComposite watchComposite;

	public WatchPlayer(IMaster playerMaster, Composite watchComposite) {
		super();
		this.playerMaster = playerMaster;
		this.watchComposite = (WatchComposite) watchComposite;
	}

	public void play()  {
		Calendar c = Calendar.getInstance();
		this.startInMillis = c.getTimeInMillis();
//		this.watchComposite.start();
		durationTask = new IPlayerTask(this, Float.valueOf( current.getDuration() ).intValue() * 1000);
		durationTimer.schedule( durationTask, 0, 100 );
	}
	
	public void next() {	
//		this.watchComposite.stop();
		durationTimer.purge();
		this.playerMaster.next();
		
	}
			

}
