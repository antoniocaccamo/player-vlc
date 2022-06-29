package it.antanysavage.sm.player.timertasks;

import it.antanysavage.sm.player.IMaster;

import java.util.TimerTask;

public class DemoTimerTask extends TimerTask {
	
	private IMaster master;

	public DemoTimerTask(IMaster master) {
		this.master = master;
	}

	@Override
	public void run() {
		master.demoSwitcher();

	}

}
