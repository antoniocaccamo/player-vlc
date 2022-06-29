package it.antanysavage.sm.player.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.WeatherManager;

public class WeatherManagerAction extends Action {

	private WeatherManager weatherManager;

	public WeatherManagerAction(WeatherManager weatherManager) {
		super( LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_6), AS_PUSH_BUTTON);
		this.weatherManager = weatherManager;
	}
	
	public void run() {
		
		Point p  = weatherManager.getPlayer().getShell().getLocation();
		p.x += 10;
		p.y += 10;
		weatherManager.getShell().setLocation(p);
		weatherManager.getShell().setVisible(true);
		weatherManager.setBlockOnOpen(true);		
		weatherManager.getShell().forceFocus();
	}
}
 