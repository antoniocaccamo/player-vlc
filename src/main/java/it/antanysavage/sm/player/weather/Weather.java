package it.antanysavage.sm.player.weather;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Weather {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		

		URL url = new URL("http://www.google.com/ig/api?weather=Avigliana&hl=it");
		InputStream in = url.openStream();

		byte[] buf = new byte[4 * 1024]; // 4K buffer
		int bytesRead;
		while ((bytesRead = in.read(buf)) != -1) {
			baos.write(buf, 0, bytesRead);
		}
		in.close();
		baos.flush();
		baos.close();
		
		String xml = null;//new String(baos.toByteArray(), Charset.forName("UTF-8"));

		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser sp = spf.newSAXParser();

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();

		/*
		 * Create a new ContentHandler and apply it to the
		 * XML-Reader
		 */
		GoogleWeatherHandler gwh = new GoogleWeatherHandler();
		xr.setContentHandler(gwh);

		/* Parse the xml-data our URL-call returned. */
		InputSource is = new InputSource( new StringReader(xml));
//		is.setEncoding("UTF-8");
//		is.setByteStream( new FileInputStream(weather ));
		xr.parse(is);

		/* Our Handler now provides the parsed weather-data to us. */
		WeatherSet ws = gwh.getWeatherSet();

		System.out.println(
				"Current date time : " + ws.getCurrentDateTime() + "\n" +
				"Forecast date     : " + ws.getForecastDate()    + "\n" +
				"current condition : " + ws.getWeatherCurrentCondition().getCondition() + "\n" +
				ws.getWeatherCurrentCondition().getTempCelcius()
				);

	}

}
