package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.PlayerMaster;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.swt.views.IMediaComposite;
import it.antanysavage.sm.player.swt.views.MPlayerVideoComposite;
import it.antanysavage.sm.player.swt.views.PhotoComposite;
import it.antanysavage.sm.player.timertasks.IPlayerAlphaTimerTask;
import it.antanysavage.sm.player.timertasks.IPlayerTask;

import java.util.Calendar;
import java.util.Timer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.Composite;

public abstract class IPlayer  {

	protected static Logger logger = LoggerFactory.getLogger(IPlayer.class);

	protected IPlayerTask durationTask;
	
	protected IPlayerAlphaTimerTask alphaTask;

	protected long startInMillis;

	protected long pausedAt;

	protected Timer durationTimer = new Timer();
	protected Timer alphaTimer    = new Timer();

	protected Media current;

	protected IMaster playerMaster;

	protected boolean alphaEnabled;	
	
	protected Composite composite = null;

	public void play()  {		
		if ( ! current.isAvailable() ) {
			next();
		}
		
		IMediaComposite iMediaComposite = getIMediaComposite();
		if ( iMediaComposite != null ) {
			logger.debug("showing...");
			iMediaComposite.show();
		}
		
		logger.debug("playyyyyyyy");
		this.startInMillis = Calendar.getInstance().getTimeInMillis();
		durationTask = new IPlayerTask(this, Float.valueOf( current.getDuration() ).intValue() * 1000 );
		durationTimer.schedule( durationTask, 0, 100 );		
		if ( playerMaster.getScreenManager().getPlayerSetting().isFade()  ) {
			logger.debug("init alpha timer task");
			alphaTask = new IPlayerAlphaTimerTask(this);		
			alphaTimer.schedule( alphaTask , 0 , 30);
			alphaEnabled = true;
		}
	}

	public void next() {
		durationTimer.purge();
		if ( alphaEnabled  ) {
			alphaTimer.purge();
		}		
		
		IMediaComposite iMediaComposite = getIMediaComposite();
		if ( iMediaComposite != null )
			iMediaComposite.hide();
		
		playerMaster.next();
	}

	public void stop() {
		if ( durationTask != null )
			this.durationTask.cancel();
		durationTimer.purge();
		if ( alphaEnabled ) {
			if (alphaTask != null ) {
				alphaTask.cancel();
			}
			alphaTimer.purge();
		}
		
		IMediaComposite iMediaComposite = getIMediaComposite();
		if ( iMediaComposite != null )
			iMediaComposite.hide();

	}

	public void setMedia(Media movie) {
		this.current = movie ;
	}

	public void pause() {
		this.pausedAt = durationTask.getActual();		
		durationTask.cancel();
		durationTimer.purge();	
	}

	public void resume() {
		long duration = 1000 * Float.valueOf( current.getDuration() ).intValue();
		durationTask = new IPlayerTask(this, this.pausedAt, duration);
		durationTimer.schedule(durationTask, 0, 100);
	}

	public IMaster getPlayerMaster() {
		return playerMaster;
	}
	
	public Composite getComposite() {
		return composite;
	}
	
	protected IMediaComposite getIMediaComposite() {
		IMediaComposite mediaComposite = null;
		
		
		Composite composite = getComposite();
		
		if ( composite != null ) {
			if ( getComposite() instanceof IMediaComposite)
				mediaComposite = (IMediaComposite) composite;
			
//			if ( getComposite() instanceof MPlayerVideoComposite)
//				mediaComposite = (MPlayerVideoComposite) composite;
		}
		
		
		return mediaComposite;
	}

    public Media getCurrent() {
        return current;
    }
        
        
        
}
