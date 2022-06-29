package it.antanysavage.sm.player.ftp;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.ScreenManagerComposite;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
//import it.antanysavage.sm.player.sequences.model.Palimpsest;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class FTPDownloadScreenProgramTimerTask extends TimerTask{



	private static final Logger logger = LoggerFactory.getLogger(FTPDownloadScreenProgramTimerTask.class);

	//	private Media media;

	//	private Palimpsest palimpsest;
	private FTPDownloader ftpDownloader;

	private ScreenManagerComposite screenManager;


	public FTPDownloadScreenProgramTimerTask( ScreenManagerComposite screenManager) {
		this.screenManager = screenManager;
		ftpDownloader = new FTPDownloader();
	}

	@Override
	public void run() {
//		Palimpsest palimpsest = screenManager.getPalimpsest();
//		if ( palimpsest == null )
//			return;
//
//		ScreenManagerComposite screenManager = palimpsest.getScreenManager();
//		Program program = palimpsest.getScreenProgram();
//
//
//		try {
//			if ( ! ftpDownloader.isConnected() ) 
//				ftpDownloader.connect();
//			StringBuffer sb = new StringBuffer();
//			Player.createScreenPath(screenManager.getScreenSetting().getScreen());
//			String remotePath =sb.append("/Schermi/").append(screenManager.getScreenSetting().getScreen()).append("/Sequenza/sequenza.xseq").toString();  
//			//Player.SCREEN_SEQ_FTP_PATH_MF.format(screenManager.getScreenSetting().getScreen()) 
//			;
//			FTPFile ftpFile = ftpDownloader.checkFile( remotePath);
//			if ( ftpFile != null ) {
//				File f = new File( Player.createScreenPath(screenManager.getScreenSetting().getScreen()),"sequenza.xseq");
//				//				if ( program == null) {
//				//					program = new Program(remotePath, f);
//				//				}
//				//				######################
//					
//				Calendar localTime = Calendar.getInstance(LocaleManager.getLocale());
//				localTime.setTimeInMillis( f.lastModified() );
//				Calendar remoteTime = Calendar.getInstance(LocaleManager.getLocale());
//				remoteTime.setTimeInMillis( ftpFile.getTimestamp().getTimeInMillis());
//				//				remoteTime.setTimeInMillis(ftpFile.getModifiedDate().getTime() );		
//
//				if ( ! f.exists() /*|| remoteTime.after(localTime)*/ || f.length() != ftpFile.getSize()) {
//					if ( f.exists() ) {
//						logger.info(
//								" == > local time [" + Utils.debugDate(localTime) +"] < == > remote time [" + Utils.debugDate( remoteTime ) +"] " +
//										"of program : " + Utils.getProgramDescription(program) );
//					} else {
//						logger.info("first download of SCREEN program : " + f.getAbsolutePath() );
//					}
//					File outputFile = new File(f.getAbsolutePath() + ".ftpdwonload");
//					FileOutputStream fos = new FileOutputStream(  outputFile );
//					ftpDownloader.download(fos, remotePath );		
//					logger.info("File downloaded as [" + outputFile.getAbsolutePath() +"]");
//					FileLock lock = null;
//					if ( program != null ) {
//						while ( ( lock = program.lock()) == null ) {
//							logger.info("can't get lock on SCREEN program : " + program);
//							Thread.sleep(500);
//
//						}				
//					}	
//					fos.flush();
//					fos.close();
//
//					Sequence sequence = SequenceFileManager.read(outputFile);
//					sequence.setRemotePath( remotePath );
//					SequenceFileManager.write(sequence, outputFile);
//
//					copyFile( outputFile, f );
//					outputFile.delete();
//
//					logger.error("load media for SCREEN program : " + remotePath );
//					if ( program == null) {
//						program = new Program(sequence, f.getAbsolutePath(), remotePath);
//					} else{
//						program.loadVideo(sequence);
//					}
//					palimpsest.updateScreenProgram(program);
//					program.unlock(lock);
//				} else {
//					logger.info("local time [" + Utils.debugDate(localTime) +"]" +
//							" remote time [" + Utils.debugDate(remoteTime ) +"]" +
//							" local size ["+ f.length()+ "] remote size [" + ftpFile.getSize() +"]" +
//							" update not necessary for SCREEN program : " + Utils.getProgramDescription(program));
//				}
//			} else {
//				logger.error("Can't find ftp SCREEN program : " + remotePath );
//				palimpsest.removeScreenVideo();
//			} 
//		} catch (Exception e) {
//			logger.error("Error on refresh program : " + Utils.getProgramDescription(program), e);
//		} finally {
//			if ( ftpDownloader != null )
//				try {
//					ftpDownloader.disconnect();
//				} catch (Exception e) {
//					logger.error("Error on ftp disconnect for program : " + Utils.getProgramDescription(program), e);
//				}
//		}
//	}
//
//	private void copyFile(File src, File dest) throws Exception {
//		InputStream in = new FileInputStream(src);
//		OutputStream out = new FileOutputStream(dest);
//
//		byte[] buf = new byte[2048];
//		int len;
//		while ((len = in.read(buf)) > 0){
//			out.write(buf, 0, len);
//		}
//		in.close();
//		out.close();
//		logger.info("File " + src.getAbsolutePath() + " copied to " + dest.getAbsolutePath());
//	}
	}
}
