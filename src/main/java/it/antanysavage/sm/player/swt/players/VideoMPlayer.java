package it.antanysavage.sm.player.swt.players;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.email.EmailSender;
import it.antanysavage.sm.player.swt.views.IMediaComposite;
import it.antanysavage.sm.player.swt.views.MPlayerVideoComposite;
import it.antanysavage.sm.player.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Timer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;


/**
 * Jajuk player implementation based on Mplayer
 * 
 * @author Bertrand Florat
 * @created 16 sept. 2006
 */
public class VideoMPlayer  extends IPlayer {

	private static final Logger logger = LoggerFactory.getLogger(VideoMPlayer.class);

	Timer alphaTimer = new Timer(); 

	/** Time elapsed in ms */
	private float lTime = 0;

	/** Current track estimated duration in ms */
	private float lDuration;

	/** Cross fade duration in ms */
	int iFadeDuration = 0;



	/** Progress step in ms */
	private static final int PROGRESS_STEP = 150;// need a fast refresh,


	/** pause flag * */
	private volatile boolean bPaused = false;


	/** File is opened flag * */
	private volatile boolean bOpening = false;

	
	private MPlayerVideoComposite mPlayerVideoComposite;


	///
	private static final String STARTING_PLAYBACK = "Starting playback...";
	private static final String PAUSE             = "pause";
	private static final String STOP              = "stop";

	private Process mplayerProcess;
	private BufferedReader mplayerOutErr;
	private PrintStream mplayerIn;

	public VideoMPlayer( IMaster playerMaster, Composite mPlayerVideoComposite) {
		super();
		this.playerMaster          = playerMaster;
		this.mPlayerVideoComposite = (MPlayerVideoComposite) mPlayerVideoComposite;
		this.composite             = mPlayerVideoComposite;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if ( mplayerProcess != null ){
			mplayerProcess.destroy();
			logger.info("mplayerProcess.destroy()");
		}
		super.finalize();
	}

	@Override
	public void play() {
		try {
			if ( current.getFile().exists() && current.getFile().canRead() ) {
				getPlayerMaster().getScreenManager().resetVideoProgressBarMaximum();
				play( current.getFile() );						
			} else {
				logger.error(Utils.getMediaDescription(current) + " doesn't exist or can't be read .. ");
				next();
			}
		} catch (Exception e) {
			logger.error("Error playing media " + Utils.getMediaDescription(current), e);
			EmailSender.getInstance().sendMediaError(current);
			next();
		}
	}

	private void play(File file) throws Exception {
		String path = file.getAbsolutePath().replace('\\', '/');
		bOpening = true;
		bPaused  = false;
		if (mplayerProcess == null) {
			// start MPlayer as an external process
			Point size = getPlayerMaster().getScreenManager().getPlayerSetting().getSize();

			String scale = "scale=" + size.x + ":" +size.y;
			StringBuffer sb = new StringBuffer();
			sb.append( Player.MPLAYER_PATH );
			sb.append( " -slave -idle" );
			sb.append( " -wid " + String.valueOf( mPlayerVideoComposite.getVideoHandle() ) );
			sb.append( " -nokeepaspect" );
			sb.append( " -quiet" );
//			sb.append( " -vo directx:noaccel" );
			sb.append( " -vf " + scale );    		
			sb.append( " -colorkey "+ mPlayerVideoComposite.getColorKey() );

			if ( ! Utils.isAnEmptyString(Player.MPLAYER_OPTIONS) ) {
				sb.append(" " + Player.MPLAYER_OPTIONS);
			}
			//    		sb.append("-vop bmovl:opaque");
			sb.append(" \"" + path +"\"");
			String command = sb.toString();
			logger.info("Starting MPlayer process: " + command);
			mplayerProcess = Runtime.getRuntime().exec(command);

			// create the piped streams where to redirect the standard output and error of MPlayer
			// specify a bigger pipesize
			PipedInputStream  readFrom = new PipedInputStream(1024*1024);
			PipedOutputStream writeTo = new PipedOutputStream(readFrom);
			mplayerOutErr = new BufferedReader(new InputStreamReader(readFrom));

			// create the threads to redirect the standard output and error of MPlayer
			new LineRedirecter(this, mplayerProcess.getInputStream(), writeTo, "MPlayer STDOUT : " , Redirect.STDOUT).start();
			new LineRedirecter(this, mplayerProcess.getErrorStream(), writeTo, "MPlayer STDERR : ",  Redirect.STDERR).start();

			// the standard input of MPlayer
			mplayerIn = new PrintStream(mplayerProcess.getOutputStream());			
			
		} else {
			execute("loadfile \"" + path + "\" 0");
		}

		execute("get_time_length");
		
		new Runnable() {
			
			public void run() {
				try {
					// wait to start playing
					logger.debug( String.format("before Thread.sleep(%d) | video bOpening %s", (Player.APP_MPLAYER_WAIT_VIDEO_START * 1000), bOpening));		
					Thread.sleep( Player.APP_MPLAYER_WAIT_VIDEO_START * 1000);
					logger.info( String.format(" after Thread.sleep(%d) | video bOpening %s", (Player.APP_MPLAYER_WAIT_VIDEO_START * 1000), bOpening));		
					
					if ( bOpening == true ) {
						logger.error("Error playing " + Utils.getMediaDescription(current));
						stop();
						next();
					}
				} catch (InterruptedException e) {
					logger.error("Error playing " + Utils.getMediaDescription(current));
					EmailSender.getInstance().sendMediaError(current);
					stop();
					next();
				}
			}
		};		

	}

	@Override
	public void pause(){		
		bPaused = true;
		execute(PAUSE); 
		logger.info("getElapsedTime() @ " + getElapsedTime() );

	}

	public Composite getComposite() {
		return mPlayerVideoComposite;
	}

	public void resume()  {
		//		super.resume();
		bPaused = false;
		execute(PAUSE);    //$NON-NLS-1$
	}

	@Override
	public void stop() {		
		execute(STOP);
		if ( mplayerProcess != null ) {
			mplayerProcess.destroy();
			mplayerProcess = null;
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


	public long getTimePosition() {
		return getPropertyAsLong("time_pos");
	}

	public void setTimePosition(long seconds) {
		setProperty("time_pos", seconds);
	}

	public long getTotalTime() {
		return getPropertyAsLong("length");
	}

	public float getVolume() {
		return getPropertyAsFloat("volume");
	}

	public void setVolume(float volume) {
		setProperty("volume", volume);
	}

	protected String getProperty(String name) {
		if (name == null || mplayerProcess == null) {
			return null;
		}
		String s = "ANS_" + name + "=";
		String x = execute("get_property " + name, s);
		if (x == null)
			return null;
		if (!x.startsWith(s))
			return null;
		return x.substring(s.length());
	}

	protected long getPropertyAsLong(String name) {
		try {
			return Long.parseLong(getProperty(name));
		}
		catch (NumberFormatException exc) {}
		catch (NullPointerException exc) {}
		return 0;
	}

	protected float getPropertyAsFloat(String name) {
		try {
			return Float.parseFloat(getProperty(name));
		}
		catch (NumberFormatException exc) {}
		catch (NullPointerException exc) {}
		return 0f;
	}

	protected void setProperty(String name, String value) {
		execute("set_property " + name + " " + value);
	}

	protected void setProperty(String name, long value) {
		execute("set_property " + name + " " + value);
	}

	protected void setProperty(String name, float value) {
		execute("set_property " + name + " " + value);
	}

	/** Sends a command to MPlayer..
	 * @param command the command to be sent
	 */
	private void execute(String command) {
		execute(command, null);
	}

	/** Sends a command to MPlayer and waits for an answer.
	 * @param command the command to be sent
	 * @param expected the string with which has to start the line; if null don't wait for an answer
	 * @return the MPlayer answer
	 */
	private String execute(String command, String expected) {
		if (mplayerProcess != null) {
			logger.debug("Send to MPlayer the command \"" + command + "\" and expecting "
					+ (expected != null ? "\"" + expected + "\"" : "no answer"));
			mplayerIn.print(command);
			mplayerIn.print("\n");
			mplayerIn.flush();
			logger.debug("Command sent");
			if (expected != null) {
				String response = waitForAnswer(expected);
				logger.info("MPlayer command response: " + response);
				return response;
			}
		}
		return null;
	}

	/** Read from the MPlayer standard output and error a line that starts with the given parameter and return it.
	 * @param expected the expected starting string for the line
	 * @return the entire line from the standard output or error of MPlayer
	 */
	private String waitForAnswer(String expected) {
		// todo add the possibility to specify more options to be specified
		// todo use regexp matching instead of the beginning of a string
		String line = null;
		if (expected != null) {
			try {
				long start = System.currentTimeMillis();
				while ((line = mplayerOutErr.readLine()) != null) {
					logger.info("Reading line: " + line);
					if (line.startsWith(expected)) {
						return line;
					}
				}
			}
			catch (IOException e) {
			}
		}
		return line;
	}

	private float getElapsedTime() {
		return lTime;
	}



	/** A thread that reads from an input stream and outputs to another line by line. */
	private class LineRedirecter extends Thread {
		/** reference to player */
		private IPlayer iPlayer= null;
		/** The input stream to read from. */
		private InputStream in;
		/** The output stream to write to. */
		private OutputStream out;
		/** The prefix used to prefix the lines when outputting to the logger. */
		private String prefix;
		/** kind of redirecting */
		private Redirect redirect;

		/**
		 * @param in the input stream to read from.
		 * @param out the output stream to write to.
		 * @param prefix the prefix used to prefix the lines when outputting to the logger.
		 */
		LineRedirecter(IPlayer iPlayer, InputStream in, OutputStream out, String prefix , Redirect redirect) {
			this.iPlayer   = iPlayer;
			this.in       = in;
			this.out      = out;
			this.prefix   = prefix;
			this.redirect = redirect;
		}

		public void run()
		{
			try {
				// creates the decorating reader and writer
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				PrintStream printStream = new PrintStream(out);
				String line;

				// read line by line
				while ( (line = reader.readLine()) != null) {
					// MPlayer STDERR
					if ( this.redirect == VideoMPlayer.Redirect.STDERR ) {
						logger.error( (prefix != null ? prefix : "") + line);
						// blocking error
						if ( line.toLowerCase().matches(".*error.*") ||  
							 line.toLowerCase().matches(".*fatal.*") 
						){
							logger.error( (prefix != null ? prefix : "") + 
									": a severe MPlayer error occurred. Stopping MPlayer process and going to next media !!! ");
//                          current.setError(true);
							iPlayer.stop();
							iPlayer.next();
							EmailSender.getInstance().sendMediaError(current);
						}			
					}
					// MPlayer STDOUT					
					if ( this.redirect == VideoMPlayer.Redirect.STDOUT ) {
						//logger.debug( (prefix != null ? prefix : "") + line);
						// starting playback 
						if ( ! bOpening && StringUtils.isEmpty(line) ) {
							bOpening = true;
							logger.info("finishing playback video : " + current.getFile().getAbsolutePath());
							next();
						}
						if (line.matches(".*Starting playback.*")) {
							bOpening = false;							
							new VideoMPlayer.PositionThread().start(); 
							logger.info("starting playback video : "+ current.getFile().getAbsolutePath());
						}
						// time position
						else if (line.matches(".*ANS_TIME_POSITION.*")) {
							StringTokenizer st = new StringTokenizer(line, "="); 
							st.nextToken();
							String time = st.nextToken();
							lTime = Float.parseFloat( time ) ;
							float i = lTime * 100   / current.getDuration();
							iPlayer.getPlayerMaster().getScreenManager().updateVideoProgressBarSelection( lTime, iPlayer.current.getDuration() /*(int)i*/);
						}
						// length
						else if (line.matches("ANS_LENGTH.*")) {
							StringTokenizer st = new StringTokenizer(line, "=");
							st.nextToken();
							String time = st.nextToken();
							lDuration =  Float.parseFloat(time) ;						
							iPlayer.current.setDuration( lDuration );
							//logger.debug(Utils.getMediaDescription(current) + " | time : " + time + " lDuration : " + Utils.getDurationString(lDuration) );
							this.iPlayer.getPlayerMaster().getScreenManager().updateMediaLabels(current);

						}
						// EOF
						else if (line.matches("Exiting.*End.*")) {					
							bOpening = false;
							logger.info("finishing playback video : " + current.getFile().getAbsolutePath());
							next();
						}
					}
				}
			} catch (IOException exc) {
				logger.warn("An error has occured while grabbing lines", exc);
			}
		}

	}

	/**
	 * Position and elapsed time getter
	 */
	private class PositionThread extends Thread {

		public void run() {
			while (! bOpening ) { // stop this thread when exiting
				try {					
					if ( ! bPaused && ! bOpening) { 					
						execute("get_time_pos");  
					}
					Thread.sleep(PROGRESS_STEP);
				} catch (Exception e) {
					
				}
			}
		}
	}

	private enum Redirect {
		STDOUT,
		STDERR
	}
	
	
	public static void main(String[] args) {
		final String line = "Error opening/initializing the selected video_out (-vo) device";
		System.out.println("VideoMPlayer.main() : " +  (line.toLowerCase().matches(".*error.*") ||  line.toLowerCase().matches(".*fatal.*") ) );
	}

}
