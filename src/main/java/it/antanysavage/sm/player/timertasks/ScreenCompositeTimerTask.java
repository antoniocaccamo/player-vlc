package it.antanysavage.sm.player.timertasks;


import it.antanysavage.sm.player.ScreenManagerComposite;
import it.antanysavage.sm.player.Status;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.util.Utils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.Display;

public class ScreenCompositeTimerTask extends TimerTask {

	private static Logger logger = LoggerFactory.getLogger(ScreenCompositeTimerTask.class);

	private ScreenManagerComposite screenManager;

	public ScreenCompositeTimerTask(
			ScreenManagerComposite screenManagerComposite) {
		this.screenManager = screenManagerComposite;
	}

	public void run() {
		//		/*
		//		 * set next weak up
		//		 */
		//		Calendar now = Calendar.getInstance();
		//		long delay = 0;
		//		if ( screenManagerComposite.isRunnig() ) {
		//			logger.info("it's time to stopping playlist ..");
		//			this.screenManagerComposite.getPlayerMaster().stop();
		//			this.screenManagerComposite.getPlayerMaster().deActive();
		//			this.screenManagerComposite.setRunnig(false);
		//			this.screenManagerComposite.getStart().add(Calendar.DAY_OF_MONTH, 1);
		//			this.screenManagerComposite.getEnd().add(Calendar.DAY_OF_MONTH, 1);
		//			delay = this.screenManagerComposite.getStart().getTimeInMillis() - now.getTimeInMillis();
		//			screenManagerComposite.resetVideoProgressBarMaximum();
		//
		//		} 
		//		/*
		//		 * set next end
		//		 */else {
		//			 logger.info("it's time to start playlist ..");
		//			 Display.getDefault().syncExec(new Runnable() {
		//
		//				 public void run() {
		//					 screenManagerComposite.setRunnig(true);				 
		//					 String seq = screenManagerComposite.getSequenceCombo().getText();
		//					 Program smp = screenManagerComposite.getSmPlayer().getSequenceSMP(seq);
		//					 screenManagerComposite.getPlayerMaster().setSequence(smp);
		//					 screenManagerComposite.getPlayerMaster().play();
		//
		//				 }
		//			 });
		//
		//			 delay = this.screenManagerComposite.getEnd().getTimeInMillis() -  now.getTimeInMillis() ;
		//		 }
		//		logger.info("next delay [ ms ] : " + delay );
		//		this.screenManagerComposite.getTimer().schedule( new ScreenCompositeTimerTask(screenManagerComposite), delay);
		//		if ( logger.isDebugEnabled() ) {
		//			logger.error("start date : " + Utils.debugDate( this.screenManagerComposite.getStart().getTime() ) );
		//			logger.error(" stop date : " + Utils.debugDate( this.screenManagerComposite.getEnd().getTime() ) );
		//		}

		//		logger.info("timertask.............");


		logger.debug("running : " +  screenManager.isRunning() + " - status  : " +  screenManager.getStatus() );


		if ( screenManager.getPlayerSetting().isTimed() ) {

			Calendar now   = Calendar.getInstance(LocaleManager.getLocale());
			
//			Calendar start = (Calendar) now.clone();
//			start.set(Calendar.HOUR_OF_DAY, screenManager.getStart().get(Calendar.HOUR_OF_DAY));
//			start.set(Calendar.MINUTE, screenManager.getStart().get(Calendar.MINUTE));
//			start.set(Calendar.SECOND, 0);
//			
//			Calendar   end = (Calendar) now.clone();
//			end.set(Calendar.HOUR_OF_DAY, screenManager.getEnd().get(Calendar.HOUR_OF_DAY));
//			end.set(Calendar.MINUTE, screenManager.getEnd().get(Calendar.MINUTE));
//			end.set(Calendar.SECOND, 0);
			
			Calendar start = screenManager.getStart();
			Calendar end   = screenManager.getEnd();
			
			
			logger.debug("*** now [" + Utils.debugDate(now) + "]  start [" + Utils.debugDate(start ) + "] end [" + Utils.debugDate( end )+ "]");
			

			if (  screenManager.isRunning() ) {

				switch ( screenManager.getStatus() ) {

				case Status.PLAYNG :
					
					if ( (now.before( start ) || now.after( end ))  ) {

						logger.info("it's time to stopping playlist .. now [" + Utils.debugDate(now) + "]" +
								" start " + Utils.debugDate(start) + "]" +
								" end [" + Utils.debugDate(end) +"]");
						
						screenManager.getStart().add(Calendar.DATE, 1);
						screenManager.getEnd().add(Calendar.DATE, 1);
						
						logger.info("next playlist range is" +
								" start " + Utils.debugDate(start) + "]" +
								" end [" + Utils.debugDate(end) +"]");

						screenManager.pause();					
					}

					break;

				case Status.PAUSED :
					
					if ( start.before(now) && end.after(now)  ) {
						logger.info("it's time to start playlist ..  now " + Utils.debugDate(now) 
								+ " start " + Utils.debugDate(start) 
								+ " end   " + Utils.debugDate(end) );

						screenManager.play();
					}

					break;

				default:
					logger.debug("running : " +  screenManager.isRunning() + " - status  : " +  screenManager.getStatus() );
					break;
				}

			}
		}

	

	}

}
