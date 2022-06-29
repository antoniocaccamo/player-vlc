package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.email.EmailSender;
import it.antanysavage.sm.player.swt.views.IMediaComposite;
import it.antanysavage.sm.player.swt.views.MPlayerVideoComposite;
import it.antanysavage.sm.player.timertasks.MplayerAlphaTimerTask;
import it.antanysavage.sm.player.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Timer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;


/**
 * Jajuk player implementation based on Mplayer
 * 
 * @author Bertrand Florat
 * @created 16 sept. 2006
 */
public class MPlayerVideoPlayer  extends IPlayer{

	private static final Logger logger = LoggerFactory.getLogger(MPlayerVideoPlayer.class);
	
	Timer alphaTimer = new Timer(); 


	/** Time elapsed in ms */
	private float lTime = 0;

	/** Date of last elapsed time update */
	private long lDateLastUpdate = System.currentTimeMillis();

	/** Length to be played in secs */
	private long length;

	/** Starting position */
	private float fPosition;

	/** Stored Volume */
	private float fVolume;

	/** Current track estimated duration in ms */
	private float lDuration;

	/** Cross fade duration in ms */
	int iFadeDuration = 0;

	/** Fading state */
	private volatile boolean bFading = false;

	/** Progress step in ms */
	private static final int PROGRESS_STEP = 300;// need a fast refresh,

	// especially for fading

	/** current file */
	private File fCurrent;

	/** Mplayer process */
	private volatile Process proc;

	/** pause flag * */
	private volatile boolean bPaused = false;

	/** End of file flag * */
	private volatile boolean bEOF = false;

	/** File is opened flag * */
	private volatile boolean bOpening = false;

	/** Current position thread */
	private volatile PositionThread position;

	/** Current reader thread */
	//	private volatile ReaderThread reader;

	private MPlayerVideoComposite mPlayerVideoComposite;


	public MPlayerVideoPlayer( IMaster playerMaster, Composite mPlayerVideoComposite) {
		super();
		this.playerMaster          = playerMaster;
		this.mPlayerVideoComposite = (MPlayerVideoComposite) mPlayerVideoComposite;
		this.composite             = mPlayerVideoComposite;
	}

	/**
	 * Position and elapsed time getter
	 */
	private class PositionThread extends Thread {
		/** Stop flag */
		volatile boolean bStop = false;

		public void run() {
			while (!bStop) { // stop this thread when exiting
				try {
					Thread.sleep(PROGRESS_STEP);
					if (!bPaused && !bStop) { // a get_percent_pos resumes
						// (mplayer issue)
						sendCommand("get_time_pos"); //$NON-NLS-1$
					}
				} catch (Exception e) {
					logger.error("error playing media "+ Utils.getMediaDescription(current), e);
					
					next();
				}
			}
		}

		public void stopThread() {
			this.bStop = true;
		}
	}

	/**
	 * Reader : read information from mplayer like position
	 */
	//	private class ReaderThread extends Thread {
	//		private IPlayer iPlayer = null;
	//		
	//		public ReaderThread(IPlayer iPlayer) {
	//			this.iPlayer = iPlayer;
	//		}
	//		
	//		public void run() {
	//			try {
	//				BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
	//				String line = null;
	//				for (; (line = in.readLine()) != null;) {
	//					logger.debug(line);
	//					if (line.matches(".*ANS_TIME_POSITION.*")) { //$NON-NLS-1$
	//						StringTokenizer st = new StringTokenizer(line, "="); //$NON-NLS-1$
	//						st.nextToken();
	//						lTime = (int) (Float.parseFloat(st.nextToken()) * 1000);
	//						//Store current position for use at next startup
	//					
	//						// Cross-Fade test
	//						if (!bFading && iFadeDuration > 0 && lDuration > 0 
	//								// can be null before getting length
	//								&& lTime > (lDuration - iFadeDuration)) {
	//							bFading = true;
	//							// force a finished (that doesn't stop but only
	//							// make a FIFO request to switch track)
	//						
	//						}
	//						// test end of length for intro mode
	////						if (length != TO_THE_END && lDuration > 0 
	////								// can be null before getting  length
	////								&& (lTime - (fPosition * lDuration)) > length) {
	////							// length=-1 means there is no max length
	////							FIFO.getInstance().finished();
	////						}
	//					} else if (line.matches("ANS_LENGTH.*")) { //$NON-NLS-1$
	//						StringTokenizer st = new StringTokenizer(line, "="); //$NON-NLS-1$
	//						st.nextToken();
	//						lDuration = (long) (Float.parseFloat(st.nextToken())* 1000) ;
	//						logger.debug("lDuration : " + lDuration);
	////						task = new IPlayerTask(iPlayer, lDuration);
	////						timer.schedule( task, 0, 100 );
	//					}
	//					// EOF
	//					else if (line.matches("Exiting.*End.*")) { //$NON-NLS-1$
	//						bEOF = true;
	//						bOpening = false;
	////						// Launch next track
	//						try {
	////							// End of file: inc rate by 1 if file is fully played
	////							fCurrent.getTrack().setRate(
	////									fCurrent.getTrack().getRate() + 1);
	////							// alert bestof playlist something changed
	////							FileManager.getInstance().setRateHasChanged(true); 
	////							// if using crossfade, ignore end of file
	////							if (!bFading) { 
	////								// Benefit from end of file to perform a full gc
	////								System.gc();
	////								if (lDuration > 0){
	////									//if corrumpted file, length=0 and we have not not call finished
	////									//as it is managed my Player
	////									FIFO.getInstance().finished();
	////								}
	////							} else {
	////								bFading = false;
	////							}
	//							logger.info("finishing playback video " + Utils.getSequenceMediaDescription(current));
	//							if ( proc != null)
	//								proc.destroy();
	//							next();
	//						} catch (Exception e) {
	//							System.err.println(e);
	//						}
	//						break;
	//					}
	//					// Opening ?
	//					else if (line.matches(".*Starting playback.*")) { //$NON-NLS-1$
	//						bOpening = false;
	//						logger.info("starting playback video " + Utils.getSequenceMediaDescription(current));
	//					}
	//				}
	//				// can reach this point at the end of file
	//				in.close();
	//				return;
	//			} catch (Exception e) {
	//				// A stop causes a steam close exception, so ignore it
	//				if (!e.getMessage().matches(".*Stream closed")) { //$NON-NLS-1$
	//					System.err.println(e);
	//					logger.error("error playing media "+ Utils.getSequenceMediaDescription(current), e);
	//					next();
	//				}
	//			}
	//		}
	//	}

	@Override
	public void play() {
		try {
			if ( current.getFile().exists() && current.getFile().canRead() ) {
				getPlayerMaster().getScreenManager().resetVideoProgressBarMaximum();
				play( current.getFile() , 0, 0, 0);		
			} else {
				logger.error(Utils.getMediaDescription(current) + " doesn't exist or can't be read .. ");
				next();
			}
		} catch (Exception e) {
			logger.error("Error playing media " + Utils.getMediaDescription(current), e);
			next();
		}
	}
	;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.players.IPlayerImpl#play(org.jajuk.base.File, float, long,
	 *      float)
	 */
	private void play(File file, float fPosition, long length,	float fVolume) throws Exception {
		this.lTime = 0;
		this.fVolume = fVolume;
		this.length = length;
		this.fPosition = fPosition;
		this.bFading = false;
		this.fCurrent = file;
		this.bOpening = true;
		this.bEOF = false;

		Point size = getPlayerMaster().getScreenManager().getPlayerSetting().getSize();

		String scale = "scale=" + size.x + ":" +size.y;
		StringBuffer sb = new StringBuffer();
		sb.append( Player.MPLAYER_PATH );
		sb.append( " -slave" );
		sb.append( " -wid " + String.valueOf( mPlayerVideoComposite.getVideoHandle() ) );
		sb.append( " -nokeepaspect" );
		sb.append( " -quiet" );
//		sb.append( " -vo directx:noaccel" );
		sb.append( " -vf " + scale );
		sb.append( " -colorkey "+ mPlayerVideoComposite.getColorKey() );
		
		if ( ! Utils.isAnEmptyString(Player.MPLAYER_OPTIONS) ) {
			sb.append(" " + Player.MPLAYER_OPTIONS);
		}
//		sb.append("-vop bmovl:opaque");
		sb.append(" \"" + file.getAbsolutePath() +"\"");
		

//		String[] cmd = { 
//				Player.MPLAYER_PATH, 
//				"-wid", String.valueOf( mPlayerVideoComposite.getVideoHandle() ),  
//				"-nokeepaspect",
//				//				"-identify",
//				"-quiet" ,
//				"-vo", "directx:noaccel",
//				"-vf" , scale ,
//				"-slave", 
//				"-colorkey", mPlayerVideoComposite.getColorKey(), 
//				file.getAbsolutePath() 
//		}; //$NON-NLS-1$ //$NON-NLS-2$
//		proc = Runtime.getRuntime().exec(cmd);
		logger.info("launching mplayer process " + sb.toString() );
		proc = Runtime.getRuntime().exec(sb.toString());
		
		final InputStream errorStream = proc.getErrorStream();
		final InputStream inputStream = proc.getInputStream();

		//		final Runnable lPrinter1 = new Runnable() {
		//			public void run() {
		//				try {
		//					final BufferedReader errorStreamReader = new
		//					BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
		//					for (String l = errorStreamReader.readLine(); l != null; l = errorStreamReader.readLine()) {
		//						logger.error("errorStreamReader : " + l);
		//					}
		//				} catch (Throwable t) {
		//					t.printStackTrace();
		//				}
		//			}
		//		};
		//
		final Runnable lPrinter1 = new MPlayerVideoPlayerRunnable(this, errorStream);
		final Runnable lPrinter2 = new MPlayerVideoPlayerRunnable(this, inputStream);



		new Thread(lPrinter1).start();
		new Thread(lPrinter2).start();

		if (position == null) {
			position = new PositionThread();
			position.start();
		}

		sendCommand("get_time_length"); //$NON-NLS-1$			
		
		if ( getPlayerMaster().getScreenManager().getPlayerSetting().isFade() )  {
			
			alphaTimer.schedule( new MplayerAlphaTimerTask(this) , 100, 20);
		}
		
		( (IMediaComposite) composite).show();
	
//		logger.info("before Thread.sleep(2000) | video bOpening : " + bOpening);		
//		Thread.sleep(2000);
//		logger.info("after  Thread.sleep(2000) | video bOpening : " + bOpening);
		
		logger.debug( String.format("before Thread.sleep(%d) | video bOpening : %s", (Player.APP_MPLAYER_WAIT_VIDEO_START * 1000), bOpening));		
		Thread.sleep( Player.APP_MPLAYER_WAIT_VIDEO_START * 1000);
		logger.info(  String.format("after  Thread.sleep(%d) | video bOpening : %s ", (Player.APP_MPLAYER_WAIT_VIDEO_START * 1000), bOpening));		
		
		
		if ( bOpening == true ) {
			logger.error("Error playing " + Utils.getMediaDescription(current));
			stop();
			((MPlayerVideoPlayerRunnable) lPrinter1).stopThread();
			((MPlayerVideoPlayerRunnable) lPrinter2).stopThread();
			EmailSender.getInstance().sendMediaError(current);
			next();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.base.IPlayerImpl#stop()
	 */
	@Override
        public void stop() {
		// Kill abrutely the mplayer process (this way, killing is synchronous,
		// and easier than sending a quit command)
		logger.debug("Stop"); //$NON-NLS-1$
		if (proc != null) {
			proc.destroy();
			position.stopThread();
			logger.info("mplayer process destroyed");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.base.IPlayerImpl#setVolume(float)
	 */
	private void setVolume(float fVolume) {
		this.fVolume = fVolume;
		System.err.println("Volume=" + (int) (100 * fVolume)); //$NON-NLS-1$
		sendCommand("volume " + (int) (100 * fVolume) + " 2"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Send a command to mplayer slave
	 * 
	 * @param command
	 */
	private void sendCommand(String command) {
		
		if (proc != null) {
			logger.debug("sendig command " + command);
			PrintStream out = new PrintStream(proc.getOutputStream());
			// Do not use println() : it doesn't work under windows
			out.print(command + '\n'); 
			out.flush();
		}
	}

	/**
	 * @return current position as a float ex: 0.2f
	 */
	private float getCurrentPosition() {
		if (lDuration == 0) {
			return 0;
		}
		return ((float) lTime) / lDuration;
	}

	/**
	 * @return current volume as a float ex: 0.2f
	 */
	private float getCurrentVolume() {
		return fVolume;
	}

	/**
	 * @return Returns the lTime in ms
	 */
	private float getElapsedTime() {
		return lTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.players.IPlayerImpl#pause()
	 */
        @Override
	public void pause(){
		//		super.pause();
		bPaused = true;
		sendCommand("pause"); //$NON-NLS-1$
		logger.info("getElapsedTime() @ " + getElapsedTime() );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.players.IPlayerImpl#resume()
	 */
        @Override
	public void resume()  {
		//		super.resume();
		bPaused = false;
		sendCommand("pause"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.players.IPlayerImpl#seek(float) Ogg vorbis seek not yet
	 *      supported
	 */
	private void seek(float posValue) {
		// if fading, ignore
		if (bFading) {
			return;
		}
		// save current position
		String command = "seek " + (int) (100 * posValue) + " 1"; //$NON-NLS-1$ //$NON-NLS-2$
		sendCommand(command);
		setVolume(fVolume); // need this because a seek reset volume
	}

	/**
	 * @return player state, -1 if player is null.
	 */
	private int getState() {
		if (bFading) {
			return 0;
		} else {
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.jajuk.players.IPlayerImpl#getCurrentLength()
	 */
	private float getCurrentLength() {
		return lDuration;
	}

	private class MPlayerVideoPlayerRunnable implements Runnable {

		private IPlayer iPlayer= null;
		private InputStream inputStream; 
		
		private boolean bStop = false;

		MPlayerVideoPlayerRunnable(IPlayer iPlayer, InputStream is) {
			this.inputStream = is;
			this.iPlayer = iPlayer;
		}

		public void run() {
			try {
				final BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				for (String line = inputStreamReader.readLine(); ! bStop && line != null; line = inputStreamReader.readLine()) {

					logger.debug(line);

					if (line.matches(".*ANS_TIME_POSITION.*")) { //$NON-NLS-1$
						StringTokenizer st = new StringTokenizer(line, "="); //$NON-NLS-1$
						st.nextToken();
						String time = st.nextToken();
						lTime = Float.parseFloat( time ) ;
//						if ( lDuration == 0.0F )
//							lDuration = current.getDuration();
						float i = lTime * 100   / current.getDuration();
//						logger.info("time : " +time +" lTime : " + lTime + " - lDuration : " + lDuration + " - % " + i  + 
//								    " - current.duration : " + iPlayer.current.getDuration() );						
						logger.debug("updating video progress bar : [" + i + "]");						
						logger.debug("time : " +time +" lTime : " + lTime + " - current.duration : " + iPlayer.current.getDuration() );
						// System.err.println("time : " +time +" lTime : " + lTime + " - current.duration : " + iPlayer.current.getDuration() );
						iPlayer.getPlayerMaster().getScreenManager().updateVideoProgressBarSelection( lTime, iPlayer.current.getDuration() /*(int)i*/);											
						// Cross-Fade test
						if (!bFading && iFadeDuration > 0 && lDuration > 0 
								// can be null before getting length
								&& lTime > (lDuration - iFadeDuration)) {
							bFading = true;
							// force a finished (that doesn't stop but only
							// make a FIFO request to switch track)

						}
						// test end of length for intro mode
						//						if (length != TO_THE_END && lDuration > 0 
						//								// can be null before getting  length
						//								&& (lTime - (fPosition * lDuration)) > length) {
						//							// length=-1 means there is no max length
						//							FIFO.getInstance().finished();
						//						}
					} else if (line.matches("ANS_LENGTH.*")) { //$NON-NLS-1$
						StringTokenizer st = new StringTokenizer(line, "="); //$NON-NLS-1$
						st.nextToken();
						String time = st.nextToken();
						lDuration =  Float.parseFloat(time) ;						
						iPlayer.current.setDuration( lDuration );
						logger.info(Utils.getMediaDescription(current) + " | time : " + time + " lDuration : " + Utils.getDurationString(lDuration) );
						this.iPlayer.getPlayerMaster().getScreenManager().updateMediaLabels(current);

					}
					// error
					else if (line.toLowerCase().matches(".*error.*") ||  line.toLowerCase().matches(".*fatal.*")) {
						logger.error("error occurred : " + line);
						EmailSender.getInstance().sendMediaError(current);
						
					}
					// EOF
					else if (line.matches("Exiting.*End.*")) { //$NON-NLS-1$
						bEOF = true;
						bOpening = false;
						logger.info("finishing playback video " + Utils.getMediaDescription(current));
						//						// Launch next track
						//						try {
						//							// End of file: inc rate by 1 if file is fully played
						//							fCurrent.getTrack().setRate(
						//									fCurrent.getTrack().getRate() + 1);
						//							// alert bestof playlist something changed
						//							FileManager.getInstance().setRateHasChanged(true); 
						//							// if using crossfade, ignore end of file
						//							if (!bFading) { 
						//								// Benefit from end of file to perform a full gc
						//								System.gc();
						//								if (lDuration > 0){
						//									//if corrumpted file, length=0 and we have not not call finished
						//									//as it is managed my Player
						//									FIFO.getInstance().finished();
						//								}
						//							} else {
						//								bFading = false;
						//							}
						next();
						//						} catch (Exception e) {
						//							logger.error("error occurred ", e);
						//						}
						//						break;
					}
					// Opening ?
					else if (line.matches(".*Starting playback.*")) { //$NON-NLS-1$
						bOpening = false;
						logger.info("starting playback video " + Utils.getMediaDescription(current));
					}

				}
			} catch (Throwable t) {
				logger.error("error playing media "+ Utils.getMediaDescription(current), t);
				next();
			}
		}
		
		public void stopThread() {
			this.bStop = true;
		}

		public void setiPlayer(IPlayer iPlayer) {
			this.iPlayer = iPlayer;
		}

	}
	
	public Composite getComposite() {
		return mPlayerVideoComposite;
	}
}
