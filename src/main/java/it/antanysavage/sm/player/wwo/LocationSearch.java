package it.antanysavage.sm.player.wwo;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class LocationSearch extends WwoApi {
	
	public static final Logger logger = LoggerFactory.getLogger(LocationSearch.class);
	
	public static final String FREE_API_ENDPOINT = "http://api.worldweatheronline.com/free/v1/search.ashx";
	public static final String PREMIUM_API_ENDPOINT = "http://api.worldweatheronline.com/free/v1/search.ashx";
	
	LocationSearch(boolean freeAPI) {
		super(freeAPI);
		
		if(freeAPI) {
			apiEndPoint = FREE_API_ENDPOINT;
		} else {
			apiEndPoint = PREMIUM_API_ENDPOINT;
		}
	}

	Data callAPI(String query) {
		return getLocationSearchData(getInputStream(apiEndPoint + query));
	}
	
	Data getLocationSearchData(InputStream is) {
		Data location = null;
		
		try {
			   // create JAXB context and initializing Marshaller
			   JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
			
			   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			   // this will create Java object - Data from the XML response
			   location = (Data) jaxbUnmarshaller.unmarshal(is);
		
			   logger.info("location : " + location);
			   
		  } catch (JAXBException e) {
			   // some exception occured
			   e.printStackTrace();
		  }
	
		return location;
	}
	
	class Params extends RootParams {
		String query;					//required
		String num_of_results="1";
		String timezone;
		String popular;
		String format;				//default "xml"
		String key;					//required
		
		Params(String key) {
			num_of_results = "1";
			this.key = key;
		}
		
		Params setQuery(String query) {
			this.query = query;
			return this;
		}
		
		Params setNumOfResults(String num_of_results) {
			this.num_of_results = num_of_results;
			return this;
		}
		
		Params setTimezone(String timezone) {
			this.timezone = timezone;
			return this;
		}
		
		Params setPopular(String popular) {
			this.popular = popular;
			return this;
		}
		
		Params setFormat(String format) {
			this.format = format;
			return this;
		}
		
		Params setKey(String key) {
			this.key = key;
			return this;
		}
	}
	
	@XmlRootElement( name="search_api" )
	@XmlAccessorType( XmlAccessType.FIELD )
	static class Data {
		Result result;
		
		Data() {};
	}
	
	@XmlRootElement( namespace = "it.antanysavage.sm.player.wwo.LocalWeather.Data" )
	@XmlAccessorType( XmlAccessType.FIELD )
	static class Result {
		String areaName;
		String country;
		String region;
		String latitude;
		String longitude;
		String population;
		String weatherUrl;
		TimeZone timezone;
		
		Result() {};
	}
	
	@XmlRootElement( namespace = "it.antanysavage.sm.player.wwo.LocalWeather.Data" )
	@XmlAccessorType( XmlAccessType.FIELD )
	static class TimeZone {
		String offset;
		
		TimeZone() {};
	}
}

