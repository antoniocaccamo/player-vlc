package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.timertasks.IPlayerTask;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;

public class HiddenWindowPlayer extends IPlayer{
	 

	public HiddenWindowPlayer(IMaster playerManager) {
		super();
		this.playerMaster = playerManager;
	}

	public void play()  {		
		this.playerMaster.hide(true);
		super.play();
	}
	
	public void next() {
		this.playerMaster.hide(false);
		super.next();
		
	}
	
	public void stop() {
		super.stop();
		this.playerMaster.hide(false);
	}
	

		
}
