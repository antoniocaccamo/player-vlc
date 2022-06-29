package it.antanysavage.sm.player;

import org.eclipse.swt.widgets.Control;

import it.antanysavage.sm.player.sequences.model.Program;

/**
 * Interface to implements for Master Screen
 */
public interface IMaster {

	/**
	 * Switcher for demo version
	 */
	public abstract void demoSwitcher();
	
	/**
	 * The parent screen manager </br> 
	 * return {@link ScreenManagerComposite}
	 */
	public abstract ScreenManagerComposite getScreenManager();
	/**
	 * Sets the screen manager </br> 
	 * {@link ScreenManagerComposite}
	 */
	public abstract void setScreenManager(ScreenManagerComposite screenManager);
	
	/**
	 * Get the playing program</br> 
	 * return {@link Program}
	 */
	public abstract Program getProgram();
	/**
	 * Sets the program that will be played</br>
	 * {@link Program}
	 */
	public abstract void setProgram(Program program);
	
	/**
	 * 
	 */
	public abstract int getStatus();
	/**
	 * 
	 */
	public abstract void play();
	/**
	 * 
	 */
	public abstract void stop();
	/**
	 * 
	 */
	public abstract void deActive();
	/**
	 * 
	 */
	public abstract void next();
	/**
	 * 
	 */
	public abstract void pause();
	/**
	 * 
	 */
	public abstract void resume();

	
	/**
	 * 
	 */
	public abstract void block(boolean block);
	/**
	 * 
	 */
	public abstract void create();
	/**
	 * 
	 */
	public abstract Control getShell();
	/**
	 * 
	 */
	public abstract void hide(final boolean hide);
	/**
	 * 
	 */
	public abstract void setVisibility(final boolean visibility);

	public abstract void setAlpha(int selection);

	public abstract void setWeatherLatlng(String weatherLatlng);


	

}