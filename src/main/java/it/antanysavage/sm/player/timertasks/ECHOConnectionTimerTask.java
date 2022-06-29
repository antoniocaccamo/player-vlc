package it.antanysavage.sm.player.timertasks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.commons.net.echo.EchoTCPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;



public class ECHOConnectionTimerTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(ECHOConnectionTimerTask.class);

	@Override
	public void run() {
		logger.warn("testing if is online ..");
		EchoTCPClient client = null;
		String line;
		try {
//			client = new EchoTCPClient();
//			client.setDefaultTimeout(5000);
//			client.connect("173.194.113.88");	
//			
//			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//			PrintWriter echoOutput =
//	            new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
//			BufferedReader echoInput =
//	            new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//	        while ((line = input.readLine()) != null)
//	        {
//	            echoOutput.println(line);
//	            System.out.println(echoInput.readLine());
//	        }
//		
//			Player.setOnline(true);
//			InetAddress.getByName("google.com").isReachable(5000);
//			
//			logger.warn("Player is online !!!");
		} catch (Exception e) {
//			logger.error("Player is NOT online : error : " + e );
//			Player.setOnline(false);
		} 
//		finally {
//			if ( client != null )
//				try {
//					client.disconnect();
//				} catch (IOException e) {
//					logger.error("Player is NOT online : disconnetting error : " + e.getMessage() );
//					Player.setOnline(false);
//				}
//		}
	}
	
//	public static void main(String[] args) {
//
//		try {
//			InputStream is = Player.class.getClassLoader().getResourceAsStream(Player.LOG4J);
//			Properties p = new Properties();
//			p.load(is);
//			PropertyConfigurator.configure(p);
//			(new ECHOConnectionTimerTask() ).run();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
