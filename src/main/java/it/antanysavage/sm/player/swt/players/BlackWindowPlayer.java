package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;

import org.eclipse.swt.widgets.Composite;

public class BlackWindowPlayer extends IPlayer{
	

	public BlackWindowPlayer(IMaster playerManager, Composite composite) {
		super();
		this.playerMaster  = playerManager;
		this.composite     = composite; 
	}	

}
