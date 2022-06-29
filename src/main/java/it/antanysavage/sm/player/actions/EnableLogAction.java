package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.action.Action;

public class EnableLogAction extends Action {
	
	private Player player;

	public EnableLogAction( Player player) {
		super( "Log" , AS_CHECK_BOX);
		this.player = player;
		setChecked( player.isEnabledLog());
	}
	
	@Override
	public void run() {
		//System.out.println("EnableLogAction.run() : " + isChecked() );
		player.enableLog( isChecked() );
	}

}
