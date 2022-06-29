package it.antanysavage.sm.player.wwo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class WwoClient {
	
	public static final Logger logger = LoggerFactory.getLogger(WwoClient.class);
//	public static final boolean LOGD = true;
	
	
	LocalWeather.Data weather;
	LocationSearch.Data loc;
	String ip = "45.08,7.40"; // Utils.getIP();
	
	JLabel fancyLabel;
	
	public void createGUI() {
		JFrame f = new JFrame();
		f.setSize(500,500);
        f.setUndecorated(true);
        f.setLocationRelativeTo(null);
        f.setBackground(new Color(0f, 0f, 0f, 0.1f));
        //f.setOpacity(0.55f); //this only works for Java 7 and above
		
		String labelText =
			      "<html>" + "<b>" + "Region" + ", " + "Country" + "</b>" +
			      "<P>" +
			      "<FONT COLOR=BLUE>" + "WeatherDesc" + "</FONT>" +
			      "<p>" +
			      "<FONT COLOR=BLUE>" + "temp_C" + "\u2103" + "</FONT>" +
			      "</html>";
			
		try {
			fancyLabel =
				      new JLabel(labelText,
				    		  	 Utils.createTransparentIcon(100,200),
				                 JLabel.CENTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        f.add(fancyLabel);
        f.pack();
        f.setVisible(true);

        //create Timer
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateGUI();
            }
        };
        
        Timer timer = new Timer(1000, taskPerformer);
        timer.setInitialDelay(0);
        timer.setDelay(6*3600*1000);
        timer.start();
	}
	
	public void updateGUI() {
		if(ip != null) {
			//get weather
			LocalWeather lw = new LocalWeather(true);
			String query = (lw.new Params(lw.key)).setQ(ip).getQueryString(LocalWeather.Params.class);
			System.out.println("WwoClient.updateGUI() qury : " + query);
			weather = lw.callAPI(query);
			
			//get location
			LocationSearch ls = new LocationSearch(true);
			query = (ls.new Params(ls.key)).setQuery(ip).getQueryString(LocationSearch.Params.class);
			loc = ls.callAPI(query);
			
			//updateWidget
			String labelText =
				      "<html>" + "<b>" + loc.result.region + ", " + loc.result.country + "</b>" +
				      "<P>" +
				      "<FONT COLOR=BLUE>" + weather.current_condition.weatherDesc + "</FONT>" +
				      "<p>" +
				      "<FONT COLOR=BLUE>" + weather.current_condition.temp_C + "\u2103" + "</FONT>" +
				      "</html>";
			
			fancyLabel.setText(labelText);
			try {
				fancyLabel.setIcon(new ImageIcon(new URL(weather.current_condition.weatherIconUrl)));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		WwoClient client = new WwoClient();

		client.update("45.08,7.40");
	}
	
	public WWO update(String weatherLatLng) {
		WWO wwo = new WWO();
		
		LocalWeather lw = new LocalWeather(true);
		String query = (lw.new Params(lw.key)).setQ(weatherLatLng).getQueryString(LocalWeather.Params.class);
		logger.info(query);
		wwo.setWeather( lw.callAPI(query) );
		
		LocationSearch ls = new LocationSearch(true);
		query = (ls.new Params(ls.key)).setQuery(weatherLatLng).getQueryString(LocationSearch.Params.class);
		//System.out.println("WwoClient.update() " + query);
		wwo.setLocation( ls.callAPI(query) );
		
		return wwo;
	}
}
