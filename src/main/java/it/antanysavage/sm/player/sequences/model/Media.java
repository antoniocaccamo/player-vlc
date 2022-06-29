package it.antanysavage.sm.player.sequences.model;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.ftp.FTPDownloadMediaTimerTask;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

//import quicktime.QTException;
//import quicktime.io.OpenMovieFile;
//import quicktime.io.QTFile;
//import quicktime.std.clocks.ExtremesCallBack;
//import quicktime.std.movies.Movie;

public class Media extends Video {	

	private static Logger logger = LoggerFactory.getLogger(Media.class);

	private boolean  dated = false;

	//	private ExtremesCallBack endCallBack = null;

	private Date endDate;

	private boolean error = false;

	//	private File  file ;

	private Date fromDate;

	//	private Image image;

	private Program myProgram;

	private Resource resource;

	//	private OpenMovieFile openMovieFile;
	//
	//	private Movie qtMovie = null;

	private Date startDate;

	private java.util.Timer fTPDownloadMediaTimer               = new java.util.Timer(); ;
	private FTPDownloadMediaTimerTask fTPDownloadMediaTimerTask = null;

	private boolean timed = false;

	private Date toDate;

	FileChannel fileChannel = null;

	private Date lastTimePlay;

	private int numOfPlayerTime = 0;
        
        private boolean errorEmailSent = false;

	public Media( ) {
		super();
		setDaysOfWeek("1111111");
	}

	public Media( Resource resource ) {
		super();
		setDaysOfWeek("1111111");
		this.resource = resource;
	}

	public Media(Program program, Video video) throws SequenceMoviePlayerException {
		super();
		this.myProgram = program;

		long startAt = 5000;


		try {
			Media.copy(this, video);
			if ( ! Utils.isAnEmptyString(video.getPath()) && getType() != AcceptedVideoTypes.BROWSER) {

				if (  getMyProgram().isRemote()  ) {																			
					
					File ff = new File (video.getPath() );
					if ( ! ff.exists() ) {						
						ff = new File (  Player.getMediaLocalePath( getMyProgram().getName() ), ff.getName() );
						logger.warn("### creating dirs : " + ff.getParentFile().getAbsolutePath());
						ff.getParentFile().mkdirs();
						logger.warn("### creating file : " + ff.getAbsolutePath());
						ff.createNewFile();	
//						logger.info("program remote : " + getMyProgram() + " :  new file added " + Utils.getMediaDescription(this));						
						setPath( ff.getAbsolutePath());
						setFile(ff);
						video.setPath( ff.getAbsolutePath());
						logger.warn("### program remote : " + getMyProgram() + " :  new file added " + Utils.getMediaDescription(this));
						startAt = 100;					
					}
				}

				if ( ! program.getManager().contains(video.getPath()) ) {

					int type = getResourceType() ;
					resource = new Resource(video.getPath(), type);
					program.getManager().add(resource);
					logger.info("NEW    resource : " + resource );
				} else {
					resource = program.getManager().get(video.getPath());
					logger.info("CACHED resource : " + resource );
				}

				error = ! resource.isAvailable();

				//			file = new File(video.getPath());
				//			if ( ! file.exists() ) {
				//				logger.error("program [" + program.getName() + "] media id [" + video.getId() +"] : file ["+file.getAbsolutePath()+"] doesn't exist");
				//				error = true;
				//			}


			}



			//FIXME patch ...
			if ( video.hasLimited() )
				setLimited(video.getLimited());
			//PATCH OLD VERSION
			if ( StringUtils.isEmpty( getDaysOfWeek() ) ) {
				setDaysOfWeek("1111111");
			}

			if ( ! Utils.isAnEmptyString(this.getFrom()) && ! Utils.isAnEmptyString( this.getTo()) ) {
				fromDate = Utils.getTimeMedium( this.getFrom() );
				toDate = Utils.getTimeMedium( this.getTo() );
				if ( fromDate != null && toDate != null ) {
					timed = true;
				}
			} 

			if ( ! Utils.isAnEmptyString(this.getStart()) && ! Utils.isAnEmptyString( this.getEnd()) ) {
				startDate = Utils.getDate(getStart());
				endDate   = Utils.getDate(getEnd());
				if ( startDate != null && endDate != null ) {
					dated  = true;
					Utils.setStartOfDay(startDate);
					Utils.setEndOfDay(endDate);
				}	

			}	


			//			if ( ! hasError() && AcceptedVideoTypes.VIDEO == video.getType()) {
			//				resource.checkDuration();
			//				setDuration( resource.getDuration() );
			//			}

			//			if ( ! hasError() && AcceptedVideoTypes.PHOTO == video.getType() ) {
			//				buildImage();
			//			}
			//			
			//			if ( AcceptedVideoTypes.FTPIMAGE == video.getType() && file.exists() ) {
			//				buildImage();
			//			}

			switch ( video.getType().getType()) {

			case AcceptedVideoTypes.FTPIMAGE_TYPE:
			case AcceptedVideoTypes.FTPVIDEO_TYPE:
				fTPDownloadMediaTimerTask = new FTPDownloadMediaTimerTask(this);

				fTPDownloadMediaTimer.schedule(fTPDownloadMediaTimerTask, startAt, Utils.hours2millis(Player.APP_FTP_REFRESH));



				//				File lock = new File(file.getAbsolutePath()+".lock");
				File lock = new File( resource.getPath().getAbsolutePath()+".lock");
				fileChannel = new RandomAccessFile( lock, "rw").getChannel();
				break;

			default:
				break;
			}


		} catch (Exception e) {
			error = true;
			logger.error("error occurred ", e);
			throw new SequenceMoviePlayerException(e.getMessage());			
		}
	}

	public int getResourceType() {
		int type = -1;

		switch ( getType().getType() ) {
		
		case AcceptedVideoTypes.PHOTO_TYPE:
		case AcceptedVideoTypes.FTPIMAGE_TYPE:
			type = Resource.PHOTO;
			break;		
		
		case AcceptedVideoTypes.VIDEO_TYPE:
		case AcceptedVideoTypes.FTPVIDEO_TYPE:
			type = Resource.VIDEO;
			break;	
			
		case AcceptedVideoTypes.BROWSER_TYPE:
		case AcceptedVideoTypes.WATCH_TYPE:	
			type = Resource.URL;
			
		default:
			type = Resource.WATCH;
			break;

		}

		return type;
	}

	public Date getEndDate() {
		return endDate;
	}

	public boolean hasError() {
		return error;
	}

	public File getFile() {
		//		return file;
		if ( resource != null)
			return resource.getPath();
		return null;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Image getImage() {
		//		return image ;
		return resource.getImage(); 
	}

	//	public void setImage(Image image) {
	//		this.image = image;
	//	}

	public Program getMyProgram() {
		return myProgram;
	}


	//	public OpenMovieFile getOpenMediaFile() {
	//		return openMovieFile;
	//	}
	//
	//	public OpenMovieFile getOpenMovieFile() {
	//		return openMovieFile;
	//	}
	//
	//	public Movie getQtMovie() {
	//		return qtMovie;
	//	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public boolean isBeetwen(final Date d) {
		boolean beetwen = true;
		Date now = null;
		if ( dated ) {
			//			now = Utils.getDate( Utils.getDateAsString( d ) );
			//			if ( now.after(startDate ) && now.before(endDate) ) {
			//				beetwen = true;
			//			} else {
			//				beetwen = false;
			//			}
			if ( startDate.getTime() <= d.getTime() && d.getTime() <= endDate.getTime() ) {
				beetwen = true;
			} else {
				beetwen = false;
			}
			logger.debug(
					"beetwen dated : " + beetwen                            + " - " + 
							"getStartDate "    + Utils.debugDate( getStartDate() )  + " - " +
							"now "             + Utils.debugDate( d )               + " - " +
							"getEndDate "      + Utils.debugDate(getEndDate() ) 
					);
		}
		if ( beetwen && timed ) {
			now = Utils.getTimeMedium( Utils.getTimeAsString( d ) );
			//			if ( now.after(fromDate ) && now.before(toDate) ) {
			//				beetwen = true;
			//			} else {
			//				beetwen = false;
			//			}
			if ( fromDate.getTime() <= now.getTime() && now.getTime() <= toDate.getTime() ) {
				beetwen = true;
			} else {
				beetwen = false;
			}
			logger.debug(
					"beetwen timed : " + beetwen                           + " - " + 
							"getFromDate "     + Utils.debugDate( getFromDate() )  + " - " +
							"now "             + Utils.debugDate( now )            + " - " +
							"getToDate "       + Utils.debugDate(getToDate() ) 
					);
		}
		return beetwen;
	}

	public boolean isDated() {
		return dated;
	}

	public boolean isDateOrTimed() {
		return isTimed() || isDated() ;
	}

	public boolean isTimed() {
		return timed;

	}


	public void setDated(boolean dated) {
		this.dated = dated;
		if ( ! this.dated ) {			
			setStart(null);
			setEnd(null);
		}
	}

	//	public void setEndCallBack(QTMoviePlayer player) throws QTException {
	//		if ( this.endCallBack == null ) {
	//			this.endCallBack = new EndCallBack(player, this);			
	//		}
	//
	//	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setFile(File file) {
		/*		
		this.file = file;
		setPath( file.getAbsolutePath());
//		if ( AcceptedVideoTypes.PHOTO == getType() ) {
//			buildImage();
//		}

		 */		
		ResourceManager rm = myProgram.getManager(); 
		 if ( rm.contains(file) ) {
			 resource = rm.get(file.getAbsolutePath()); 
		 } else {

			 int type = getResourceType() ;
			 try {
				 resource = new Resource(file, type);
			 } catch (Exception e) {
				 error = true;
				 logger.error("error occurred ", e);
			 }
			 myProgram.getManager().add(resource);
			 setError( ! resource.isAvailable());
			 setPath( resource.getPath().getAbsolutePath());
			 logger.info("NEW    resource : " + resource );
		 }

	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	//	public void setImage(Image image) {
	//		this.image = image;
	//	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public void setTimed(boolean timed) {
		this.timed = timed;
		if ( ! this.timed ) {			
			setFrom(null);
			setTo(null);
		}
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}





	public FileLock lock() throws Exception {
		return fileChannel.tryLock();
	}
	public void unlock(FileLock lock) throws Exception {
		lock.release();
	}

	@Override
	protected void finalize() throws Throwable {
		if ( fileChannel != null )
			fileChannel.close();
	}

	public void remoteCheckErrors() throws Exception {
		//		if ( hasError() ) {
		//			if ( file.exists() )
		if ( resource.isAvailable() )
			error = false;			
		if ( getType() == AcceptedVideoTypes.FTPVIDEO ) {
			try {
				resource.checkDuration();
				setDuration( resource.getDuration() );
			} catch (Exception e) {
				logger.error("error occurred ", e);
				error = true;
			}				
		}
		if ( getType() == AcceptedVideoTypes.FTPIMAGE ) {				
			resource.buildImage();
			logger.debug("building new image result : " + error);
		}
		//		}		
	}

	/*	

	private void buildImage() throws Exception {
		logger.debug("building image for file : " + getFile().getAbsolutePath());
//		this.image = SWTResourceManager.getImage(getFile().getAbsolutePath());
		Image img = new Image(Display.getDefault(), getFile().getAbsolutePath());
		if ( this.image != null ){
			this.image.dispose();
		}
		this.image = img;

	}
	 */

	public boolean isAvailable() {
		boolean available = true;

		switch ( getType().getType() ) {
		case AcceptedVideoTypes.FTPIMAGE_TYPE :
		case AcceptedVideoTypes.FTPVIDEO_TYPE :			
		case AcceptedVideoTypes.VIDEO_TYPE    :	
		case AcceptedVideoTypes.PHOTO_TYPE    :
			//			available = file.exists();		
			available = resource.isAvailable();
			break;

		default:
			break;
		}

		return available && ! hasError() ;
	}

	public void setError( boolean error) {
		this.error = error;
	}

	/*

	private void checkDuration() throws Exception {


		//		if ( ! Player.MPLAYER_MODE ) {
		//			QTFile qtFile = new QTFile(file);
		//
		//			OpenMovieFile omFile = OpenMovieFile.asRead (qtFile);
		//			//				this.openMovieFile = omFile;
		//
		//			Movie aMovie = Movie.fromFile (omFile);
		//			int ss = aMovie.getDuration() / aMovie.getTimeScale();
		//			this.qtMovie = aMovie;
		//			//				aMovie.disposeQTObject();
		//			//				omFile.disposeQTObject();
		//			this.setDuration(ss);
		//
		//
		//		} else {

//		 * how to get video length with mplayer
//		 * 
//		 * mplayer -vo null -ao null -frames 0 -identify <video file>

		try {
			String[] cmd = { 
					Player.MPLAYER_PATH,  
					"-vo",  "null" ,
					"-ao",  "null" ,
					"-frames",  "0" ,
					//					"-identify", file.getAbsolutePath() 
					"-identify", resource.getPath().getAbsolutePath()
			}; //$NON-NLS-1$ //$NON-NLS-2$
			Process proc = Runtime.getRuntime().exec(cmd);

			//					final BufferedReader errorStream = new BufferedReader( new InputStreamReader( proc.getErrorStream()));
			final BufferedReader inputStream =  new BufferedReader( new InputStreamReader(proc.getInputStream()));

			//					MPlayerMediaLentghReader errorReader = new MPlayerMediaLentghReader(this, errorStream, proc);
			MPlayerMediaLentghReader inputReader = new MPlayerMediaLentghReader(this, inputStream, proc);

			//					new Thread(errorReader).start();
			Thread t = new Thread(inputReader);
			t.start();					
			t.join();

			proc.destroy();

			//					logger.info(Utils.getSequenceMediaDescription(this));
		} catch (Exception e) {
			error = true;
			logger.error("error occurred ", e);
			//				throw new SequenceMoviePlayerException(e.getMessage());
		}
	}


	public class MPlayerMediaLentghReader implements Runnable {

		private BufferedReader br;
		private Media media;
		private Process process;

		public MPlayerMediaLentghReader(Media media, BufferedReader br, Process p) {
			this.media = media;
			this.br = br;
			this.process = p;
		}

		//		@Override
		public void run() {
			String l = null;
			try {
				while ( (l = br.readLine() ) != null ) {
					if ( l.indexOf("ID_LENGTH=") != -1) {						
						String d = l.substring("ID_LENGTH=".length());
						media.setDuration( Float.parseFloat(d) );

						process.destroy();
					}					
				}
			} catch (IOException e) {

			}

		}

	}

	 */

	//	}

	public boolean isLimited(){
		if ( hasLimited() && getLimited() >  0 )
			return true;
		return false;
	}

	//	public void setLastTimePlay(Date lastTimePlay) {
	//		this.lastTimePlay = lastTimePlay;
	//	}
	//	
	//	public Date getLastTimePlay() {
	//		return lastTimePlay;
	//	}

	public void resetNumOfPlayedTime(){
		this.numOfPlayerTime = 0;
	}		

	public boolean isPlayable(final Calendar now) {
		logger.debug("now is : " + Utils.debugDate(now) );
		boolean canBePlayed = true;

		if ( hasError() ){
			return false;
		}
		// limiti di date e/o ora		
		if ( isDateOrTimed() && ! isBeetwen(now.getTime()) ) {		    
			canBePlayed = false;
		}

		switch ( now.get(Calendar.DAY_OF_WEEK) ) {

		case Calendar.MONDAY:
			if ( '1' != getDaysOfWeek().charAt(0) )
				canBePlayed = false;
			break;

		case Calendar.TUESDAY:
			if ( '1' != getDaysOfWeek().charAt(1) )
				canBePlayed = false;
			break;

		case Calendar.WEDNESDAY:
			if ( '1' != getDaysOfWeek().charAt(2) )
				canBePlayed = false;
			break;

		case Calendar.THURSDAY:
			if ( '1' != getDaysOfWeek().charAt(3) )
				canBePlayed = false;
			break;

		case Calendar.FRIDAY:
			if ( '1' != getDaysOfWeek().charAt(4) )
				canBePlayed = false;
			break;

		case Calendar.SATURDAY:
			if ( '1' != getDaysOfWeek().charAt(5) )
				canBePlayed = false;
			break;

		case Calendar.SUNDAY:
			if ( '1' != getDaysOfWeek().charAt(6) )
				canBePlayed = false;
			break;
		default:
			canBePlayed = false;
			break;
		}

		// limiti di passaggi
		if ( canBePlayed && isLimited() ) {			
			if ( this.numOfPlayerTime < getLimited() ) {
				numOfPlayerTime++;
				lastTimePlay = new Date() ;
			} else{				
				// e' passato un giorno ??
				if ( Utils.isLimitationElapsed(lastTimePlay, now.getTime()) )	{
					resetNumOfPlayedTime();
					numOfPlayerTime++;
					lastTimePlay = new Date();
				} else {
					canBePlayed = false;
				}
			} 
		}		 
		return canBePlayed;
	}

	public void resizeTo(Point size) {
		resource.resizeTo(size);

	}

	@Override
	public float getDuration() {
		if ( resource != null && resource.isAvailable() && resource.type == Resource.VIDEO ) 
			return resource.getDuration();
		return super.getDuration();
	}

	public void setDuration(float duration) {
		logger.info("setting duration : "  + duration );
		super.setDuration(duration);
		if ( resource != null ) 
			resource.setDuration(duration);
	}
	
	public static void copy(Video dest, Video src ){
		dest.setDaysOfWeek( src.getDaysOfWeek() );
		dest.setDuration(src.getDuration());
		dest.setEnd( src.getEnd() );
		dest.setFrom( src.getFrom() );		
		dest.setId( src.getId() );
		dest.setLimited( src.getLimited() );
		dest.setPath(src.getPath() );
		dest.setRemotePath( src.getRemotePath() );		
		dest.setStart(src.getStart());
		dest.setTo( src.getTo() );
		dest.setType( src.getType() );				
	}

        public boolean isErrorEmailSent() {
            return errorEmailSent;
        }

        public void setErrorEmailSent(boolean errorEmailSent) {
            this.errorEmailSent = errorEmailSent;
        }
        
        @Override
        public String toString(){
            StringBuffer sb = new StringBuffer();
            sb.append("id [").append( this.getId()).append("] ");
            sb.append("type [").append(getType()).append("] ");
            if ( StringUtils.isNotEmpty(getPath()))
            	sb.append("path [").append(this.getPath()).append("]");
            return sb.toString();
        }

}
