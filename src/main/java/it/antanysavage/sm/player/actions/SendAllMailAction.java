package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.action.Action;

public class SendAllMailAction extends Action {
	
	private Player player;

	public SendAllMailAction( Player player) {
		super( "Send All Mail" , AS_CHECK_BOX);
		this.player = player;
		setChecked( Player.APP_SEND_ALL_MAIL);
	}
	
	@Override
	public void run() {
		//System.out.println("EnableLogAction.run() : " + isChecked() );
		Player.APP_SEND_ALL_MAIL = isChecked() ;
	}

}
