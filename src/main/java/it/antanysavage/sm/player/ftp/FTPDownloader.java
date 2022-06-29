package it.antanysavage.sm.player.ftp;



import java.io.OutputStream;

//import org.apache.commons.cli.CommandLine;
//import org.apache.commons.cli.CommandLineParser;
//import org.apache.commons.cli.Option;
//import org.apache.commons.cli.Options;
//import org.apache.commons.cli.PosixParser;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPDownloader {

	private static final Logger logger = LoggerFactory.getLogger(FTPDownloader.class);

	private FTPClient client;

	public FTPDownloader() {

	}

	public void download( OutputStream os , String file) throws Exception {
		
		client.retrieveFile( file, os);
//		client.download(file, os, 0, null);
	}


	

	public FTPFile checkFile(String ftpFilePath) throws Exception {
		logger.info("checking file path [" + ftpFilePath +"] ..");
		FTPFile[] file = client.listFiles(ftpFilePath);
//		FTPFile[] file = client.list(ftpFilePath);
		if ( file == null || file.length == 0 )
			return null;
		if ( file.length > 1 )
			return null;		
		return file[0];

	}
	
	public String printWorkingDirectory() throws Exception {
		return client.printWorkingDirectory();
//		return client.currentDirectory();
	}
	
	public void connect () throws Exception {
		String ftp  = System.getenv("FTP_HOME");		
		String user = System.getenv("FTP_USER");		
		String pass = System.getenv("FTP_PASS");
		
//		String ftp  = "93.93.203.204 " ;		
//		String user = "ftp1_support" ;		
//		String pass = "support";
		
		connect(ftp, user, pass);
	}

	public void disconnect() throws Exception {
		if ( client.isConnected())
			client.disconnect();	
//			client.disconnect(true);
		logger.info("ftp client disconnected");
	}
	
	
//	public static void main(String[] args) throws Exception {		
//		try {
//			InputStream is = FTPDownloader.class.getClassLoader().getResourceAsStream(Player.LOG4J);
//			Properties p = new Properties();
//			p.load(is);
//			PropertyConfigurator.configure(p);
//			
//			
////			String ftp  = System.getenv("FTP_HOME");		
////			String user = System.getenv("FTP_USER");		
////			String pass = System.getenv("FTP_PASS");
////			
////			if (  Utils.isAnEmptyString(ftp) || Utils.isAnEmptyString(user) || Utils.isAnEmptyString(pass) ) {
////				throw new Exception("Environment variables not correctly setted !!!");
////				
////			}
//			
//			Options options = new Options();
//			
//			Option anOption = new Option("h", true, "ftp file path") ;
//			anOption.setRequired(true);
//			options.addOption(anOption);
//			
//			anOption = new Option("u", true, "ftp file path") ;
//			anOption.setRequired(true);
//			options.addOption(anOption);
//			
//			anOption = new Option("p", true, "ftp file path") ;
//			anOption.setRequired(true);
//			options.addOption(anOption);
//			
//			
//			anOption = new Option("o", true, "output directory") ;
//			anOption.setRequired(true);
//			options.addOption(anOption);
//
//			anOption = new Option("f", true, "ftp file path") ;
//			anOption.setRequired(true);
//			options.addOption(anOption);
//			
//			
//			CommandLineParser parser = new PosixParser();
//			CommandLine cmd = parser.parse( options, args);
//			
//			String outputDirectory = cmd.getOptionValue("o");
//			String ftpFilePath     = cmd.getOptionValue("f");
//			
//			String ftp  = cmd.getOptionValue("h");
//			String user = cmd.getOptionValue("u");
//			String pass = cmd.getOptionValue("p");
//			
//			FTPDownloader ftpDownloader = new FTPDownloader();
//			ftpDownloader.connect(ftp, user, pass);
//			FTPFile ftpFile = ftpDownloader.checkFile(ftpFilePath);
//			if ( ftpFile != null ) {
//				File outputFile = new File(outputDirectory + File.separator + ftpFile.getName());
//				FileOutputStream fos = new FileOutputStream(  outputFile );
//				ftpDownloader.download(fos, ftpFilePath);
//				logger.info("File downloaded as [" + outputFile.getAbsolutePath() +"]");
//			} else {
//				logger.info( "[" +ftpFilePath + "] was not found in ftp host [" + ftp +"]");
//			}
//			ftpDownloader.disconnect();
//			
//		} catch (Exception e) {
//			if ( logger != null ) {
//				logger.error("error occurred ", e);
//			}
//			System.exit(1);
//		}
//	}

	public void connect(String ftp, String user, String pass) throws Exception {
		client = new FTPClient();
		client.connect(ftp);
		logger.info("ftp client connected to [" + ftp +"]");
		client.login( user, pass ); 
		logger.info("ftp client logged to [" + ftp +"]");		
		
		if ( ! client.setFileType(FTP.BINARY_FILE_TYPE) ) {
			logger.error("can't set FTP bynary transfer");
			throw new Exception("Failed to set ftpClient object to BINARY_FILE_TYPE"); 
		}
		client.enterLocalPassiveMode();
		
	}
	
	public FTPFile[] listFiles() throws Exception {
		FTPFile[] files = client.listFiles();
		logger.info( "# FTP files " + files.length );
		return files;
	}
	
	public boolean changeWorkingDirectory(String dir) throws Exception {
		return client.changeWorkingDirectory(dir);
	}

	public boolean isConnected() {
		if ( client == null )
			return false;
		return client.isConnected();
	}

}
