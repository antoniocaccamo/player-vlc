package it.antanysavage.sm.player.swt.players;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.swt.views.PhotoComposite;
import it.antanysavage.sm.player.swt.views.WebUrlComposite;
import it.antanysavage.sm.player.util.Utils;

public class WebUrlPlayer extends IPlayer {


	public  WebUrlPlayer(IMaster playerMaster, Composite webUrlComposite) {
		super();
		this.playerMaster = playerMaster;
		this.composite      =  webUrlComposite;
	}

	@Override
	public void setMedia(Media media) {

		super.setMedia(media);
		if ( StringUtils.isNotEmpty(media.getPath()))
			((WebUrlComposite) this.composite).navigateTo(media.getPath());

	}

}
