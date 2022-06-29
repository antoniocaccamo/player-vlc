package it.antanysavage.sm.player.ftp;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
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

public class FTPDownloadProgramTimerTask extends TimerTask{

	private static final Logger logger = LoggerFactory.getLogger(FTPDownloadProgramTimerTask.class);

//	private Media media;

	private Program program;

	public FTPDownloadProgramTimerTask(Program program) {
		this.program = program;
	}

	@Override
	public void run() {
		FTPDownloader ftpDownloader = null;
		try {
			ftpDownloader = new FTPDownloader();
			ftpDownloader.connect();
			FTPFile ftpFile = ftpDownloader.checkFile( program.getRemotePath() );
			if ( ftpFile != null ) {
				File f = program.getFilepath();
				Calendar localTime = Calendar.getInstance(LocaleManager.getLocale());
				localTime.setTimeInMillis( f.lastModified() );
				Calendar remoteTime = Calendar.getInstance(LocaleManager.getLocale());
				remoteTime.setTimeInMillis( ftpFile.getTimestamp().getTimeInMillis());
//				remoteTime.setTimeInMillis(ftpFile.getModifiedDate().getTime() );				 
				if ( ! f.exists() || remoteTime.after(localTime) || f.length() != ftpFile.getSize()) {
					if ( f.exists() ) {
						logger.info(
								" == > local time [" + Utils.debugDate(localTime) +"] < == > remote time [" + Utils.debugDate( remoteTime ) +"] " +
										"of program : " + Utils.getProgramDescription(program) );
					} else {
						logger.info("first download of program : " + Utils.getProgramDescription(program) );
					}
					File outputFile = new File(program.getFilepath()+ ".ftpdwonload");
					FileOutputStream fos = new FileOutputStream(  outputFile );
					ftpDownloader.download(fos, program.getRemotePath());		
					logger.info("File downloaded as [" + outputFile.getAbsolutePath() +"]");
					FileLock lock = null;					
					while ( ( lock = program.lock()) == null ) {
						logger.info("can't get lock on program : " + program);
						Thread.sleep(500);
					}					
					fos.flush();
					fos.close();					
					copyFile( outputFile, f );
					outputFile.delete();
					
					program.loadVideo(SequenceFileManager.read(f));		
					program.unlock(lock);
				} else {
					logger.info("local time [" + Utils.debugDate(localTime) +"]" +
							  " remote time [" + Utils.debugDate(remoteTime ) +"]" +
							  " local size ["+ f.length()+ "] remote size [" + ftpFile.getSize() +"]" +
							  " update not necessary for program : " + Utils.getProgramDescription(program));
				}
			} else {
				logger.error("Can't find ftp program : " + Utils.getProgramDescription(program));
			} 
		} catch (Exception e) {
			logger.error("Error on refresh program : " + Utils.getProgramDescription(program), e);
		} finally {
			if ( ftpDownloader != null )
				try {
					ftpDownloader.disconnect();
				} catch (Exception e) {
					logger.error("Error on ftp disconnect for program : " + Utils.getProgramDescription(program), e);
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
