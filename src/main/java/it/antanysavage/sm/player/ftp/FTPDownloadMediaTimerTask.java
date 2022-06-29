package it.antanysavage.sm.player.ftp;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class FTPDownloadMediaTimerTask extends TimerTask{

	private static final Logger logger = LoggerFactory.getLogger(FTPDownloadMediaTimerTask.class);

	private Media media;

	public FTPDownloadMediaTimerTask(Media aMedia) {
		media = aMedia;
	}

	@Override
	public void run() {
		FTPDownloader ftpDownloader = null;
		try {
			ftpDownloader = new FTPDownloader();
			ftpDownloader.connect();
			FTPFile ftpFile = ftpDownloader.checkFile( media.getRemotePath() );
			if ( ftpFile != null ) {
				File f = media.getFile();
				Calendar localTime = Calendar.getInstance(LocaleManager.getLocale());
				localTime.setTimeInMillis( f.lastModified() );
				Calendar remoteTime = Calendar.getInstance(LocaleManager.getLocale());
				remoteTime.setTimeInMillis( ftpFile.getTimestamp().getTimeInMillis());
//				remoteTime.setTimeInMillis(ftpFile.getModifiedDate().getTime() );	
				
				
				if ( ! f.exists() || remoteTime.after(localTime) || f.length() != ftpFile.getSize()) {
					if ( f.exists() ) {
						logger.info(
								" == > local time [" + Utils.debugDate(localTime) +"] < == > remote time [" + Utils.debugDate( remoteTime ) +"] " +
										"of media : " + Utils.getMediaDescription(media) );
					} else {
						logger.info("first download of media : " + Utils.getMediaDescription(media));
					}
					File outputFile = new File(media.getFile().getAbsoluteFile()+ ".ftpdwonload");
					FileOutputStream fos = new FileOutputStream(  outputFile );
					ftpDownloader.download(fos, media.getRemotePath());		
					logger.info("File downloaded as [" + outputFile.getAbsolutePath() +"]");
					FileLock lock = null;					
					while ( ( lock = media.lock()) == null ) {
						Thread.sleep(5000);
					}					
					fos.flush();
					fos.close();					
					copyFile( outputFile, f );
					outputFile.delete();													
					media.remoteCheckErrors();
					media.unlock(lock);
				} else {
					logger.info("local time [" + Utils.debugDate(localTime) +"]" +
							  " remote time [" + Utils.debugDate(remoteTime ) +"]" +
							  " local size ["+ f.length()+ "] remote size [" + ftpFile.getSize() +"]" +
							  " update not necessary for media : " + Utils.getMediaDescription(media));
				}
			} else {
				logger.error("Can't find ftp media : " + Utils.getMediaDescription(media));
			} 
		} catch (Exception e) {
			logger.error("Error on refresh media : " + Utils.getMediaDescription(media), e);
		} finally {
			if ( ftpDownloader != null )
				try {
					ftpDownloader.disconnect();
				} catch (Exception e) {
					logger.error("Error on ftp disconnect for media : " + Utils.getMediaDescription(media), e);
				}
		}
	}

	private void copyFile(File src, File dest) throws Exception {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);

		byte[] buf = new byte[2048];
		int len;
		while ((len = in.read(buf)) > 0){
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		logger.info("File " + src.getAbsolutePath() + " copied to " + dest.getAbsolutePath());
	}
}
