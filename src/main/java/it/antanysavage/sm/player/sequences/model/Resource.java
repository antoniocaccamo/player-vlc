package it.antanysavage.sm.player.sequences.model;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.util.SWTUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class Resource {

	private static final Logger logger = LoggerFactory.getLogger(Resource.class);

	public static final int EMPTY  = 0;
	public static final int WATCH  = 1;
	public static final int PHOTO  = 2;
	public static final int VIDEO  = 3;
	public static final int URL    = 4;
	public static final int SOC    = 5;


	protected File path;

	protected int type ;

	// for video

	private float duration = -1F;

	// for images
	private Image image;
	
	private static String getTypeDesc(int type){
		String s = null;
		switch (type) {
		case PHOTO:
			s = "PHOTO";
			break;
			
		case VIDEO:
			s = "VIDEO";
			break;
			
		case URL:
			s = "URL";
			break;
			
		case SOC:
			s = "SOC";
			break;

		}
		return s;
	}

	public Resource ( Image image ) {
		this.image = image ;
		this.type = Resource.PHOTO;
	}

	public Resource( String path, int type) throws Exception {
		this(new File(path), type);
	}

	public Resource( File file, int type) throws Exception {
		this.path = file;
		this.type = type;

		if ( type != URL ||  type != SOC) {

			if ( path.exists() ) {
				if ( type == Resource.PHOTO ) {
					//				buildImage();
				} else {
					//				checkDuration();
				} 

			} else {
				if ( type != URL )
				logger.error("the file ["+file.getAbsolutePath()+"] doesn't exist");
			}
		}
		logger.debug("::::: resource : " + this);
	}


	public Resource() {
		this.type = EMPTY;
	}

	public boolean isVideo() {
		return type == Resource.VIDEO;
	}

	public boolean isPhoto() {
		return type == Resource.PHOTO;
	}

	public File getPath() {
		return path;
	}

	public Image getImage() {
		if ( image == null)
			buildImage();
		return image;
	};

	public void resizeTo(Point size) {

		if ( image == null )
			buildImage(); 

		Point mysize = new Point( image.getBounds().width,  image.getBounds().height);

		if ( ! mysize.equals( size) ) {
			logger.info("resizing image  to " + size );
			this.image = SWTUtils.resizeImage(image, size.x, size.y);
		}				

	}

	public boolean isAvailable() {
		boolean b = false;
		switch (type) {
		case EMPTY :			
		case URL:
		case SOC:
			b = true;
			break;

		default:
			b = path.exists() && path.canRead() && ( path.length() > 0);
			break;
		}

		return b ;
	}
	
	public int getType() {
		return type;
	}

	public void buildImage() {
		if ( path.exists() ) {
			if (image != null ) 
				image.dispose();
			image = SWTUtils.getImage(path.getAbsolutePath());
		}
	}


	protected void checkDuration() throws Exception {
		logger.info("check duration for " + path.getAbsolutePath() );

		if ( path.exists() && duration == -1F ) {

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
			/* 
			 * how to get video length with mplayer
			 * 
			 * mplayer -vo null -ao null -frames 0 -identify <video file>
			 */
			try {
				String[] cmd = { 
						Player.MPLAYER_PATH,  
						"-vo",  "null" ,
						"-ao",  "null" ,
						"-frames",  "0" ,
						//					"-identify", file.getAbsolutePath() 
						"-identify", path.getAbsolutePath()
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
				logger.error("error occurred ", e);
				throw e;
			}

		}
	}


	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public class MPlayerMediaLentghReader implements Runnable {

		private BufferedReader br;
		private Resource media;
		private Process process;

		public MPlayerMediaLentghReader(Resource media, BufferedReader br, Process p) {
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

	@Override
	public String toString() {
		return "Type [" + getTypeDesc(this.type) + "] Resource [" + path.getAbsolutePath() +"]";

	}
	
	

}
