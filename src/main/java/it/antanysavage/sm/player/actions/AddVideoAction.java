package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.action.Action;

public class AddVideoAction extends Action  {

	public  AddVideoAction() {
		super( LocaleManager.getText(LocaleManager.APP_MENU_HELP_ABOUT) , AS_PUSH_BUTTON);
	}

}
