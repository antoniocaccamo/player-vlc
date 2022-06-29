package it.antanysavage.sm.player.sequences.model;

import org.eclipse.swt.widgets.Composite;

import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.swt.players.IPlayer;

public class MediaPlayer {
	
	private AcceptedVideoTypes       type;
	
	private IPlayer      player;
	
	private Composite composite;

	public AcceptedVideoTypes getType() {
		return type;
	}

	public void setType(AcceptedVideoTypes type) {
		this.type = type;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	public Composite getComposite() {
		return composite;
	}

	public void setComposite(Composite composite) {
		this.composite = composite;
	}
	
		

}
