package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.NewsManager;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;

public class NewsManagerAction extends Action {

	private NewsManager newsManager;

	public NewsManagerAction(NewsManager newsManager) {
		super( "** News Manager");
		this.newsManager = newsManager;
	}
	
	public void run() {
		
		Point p  = newsManager.getSmPlayer().getShell().getLocation();
		p.x += 10;
		p.y += 10;
		newsManager.getShell().setLocation(p);
//		HashMap sh = NewsManager.getSmPlayer().getSequencesLoaded();
//		newsManager.getSequecensListViewer().setInput(sh);	
		newsManager.getShell().setVisible(true);
		newsManager.setBlockOnOpen(true);		
		newsManager.getShell().forceFocus();
	}
}
 