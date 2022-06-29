package it.antanysavage.sm.player.swt.players;

import org.eclipse.swt.widgets.Composite;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.swt.views.PhotoComposite;

public class PhotoPlayer extends IPlayer {

	private PhotoComposite photoComposite;

	public PhotoPlayer(IMaster playerMaster, PhotoComposite photoComposite) {
		super();
		this.playerMaster = playerMaster;
		this.photoComposite = photoComposite;	
		this.composite      = photoComposite;
	}


	public void setMedia(Media media) {
		this.current =  media;		
		if ( ! current.isAvailable() ) {
			media.setError(true);
			this.next();
		} else {
//			this.photoComposite.showPhoto(current);
			this.photoComposite.set(current);
		}
	}
	

}
