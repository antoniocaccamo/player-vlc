package it.antanysavage.sm.player.weather;

 


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
/**
 * SAXHandler capable of extracting information out of the xml-data returned by
 * the Google Weather API.
 */
public class GoogleWeatherHandler extends DefaultHandler {
 
        // ===========================================================
        // Fields
        // ===========================================================
 
        private WeatherSet myWeatherSet = null;
        private boolean in_forecast_information = false;
        private boolean in_current_conditions = false;
        private boolean in_forecast_conditions = false;
 
        private boolean usingSITemperature = false; // false means Fahrenheit
 
        // ===========================================================
        // Constructors
        // ===========================================================
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public WeatherSet getWeatherSet() {
                return this.myWeatherSet;
        }
 
        // ===========================================================
        // Methods
        // ===========================================================
        @Override
        public void startDocument() throws SAXException {
                this.myWeatherSet = new WeatherSet();
        }
 
        @Override
        public void endDocument() throws SAXException {
                // Nothing
        }
 
        public void startElement(String namespaceURI, String localeName,
    			String qName, Attributes atts) throws SAXException {
    		// 'Outer' Tags
    		if (qName.equals("forecast_information")) {
    			this.in_forecast_information = true;
    		} else if (qName.equals("current_conditions")) {
    			this.myWeatherSet.setWeatherCurrentCondition(new WeatherCurrentCondition());
    			this.in_current_conditions = true;
    		} else if (qName.equals("forecast_conditions")) {
    			this.myWeatherSet.getWeatherForecastConditions().add(new WeatherForecastCondition());
    			this.in_forecast_conditions = true;
    		} else {
    			String dataAttribute = atts.getValue("data");
    			// 'Inner' Tags of "<forecast_information>"
    			if (qName.equals("city")) {
    			} else if (qName.equals("postal_code")) {
    			} else if (qName.equals("latitude_e6")) {
    				/* One could use this to convert city-name to Lat/Long. */
    			} else if (qName.equals("longitude_e6")) {
    				/* One could use this to convert city-name to Lat/Long. */
    			} else if (qName.equals("forecast_date")) {
    				myWeatherSet.setForecastDate(dataAttribute);
    			} else if (qName.equals("current_date_time")) {
    				myWeatherSet.setCurrentDateTime(dataAttribute);
    			} else if (qName.equals("unit_system")) {
    				if (dataAttribute.equals("SI"))
    					this.usingSITemperature = true;
    			}
    			// SHARED(!) 'Inner' Tags within "<current_conditions>" AND
    			// "<forecast_conditions>"
    			else if (qName.equals("day_of_week")) {
    				if (this.in_current_conditions) {
    					this.myWeatherSet.getWeatherCurrentCondition()
    							.setDayofWeek(dataAttribute);
    				} else if (this.in_forecast_conditions) {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setDayofWeek(dataAttribute);
    				}
    			} else if (qName.equals("icon")) {
    				if (this.in_current_conditions) {
    					this.myWeatherSet.getWeatherCurrentCondition().setIconURL(
    							dataAttribute);
    				} else if (this.in_forecast_conditions) {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setIconURL(dataAttribute);
    				}
    			} else if (qName.equals("condition")) {
    				if (this.in_current_conditions) {
    					this.myWeatherSet.getWeatherCurrentCondition()
    							.setCondition(dataAttribute);
    				} else if (this.in_forecast_conditions) {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setCondition(dataAttribute);
    				}
    			}
    			// 'Inner' Tags within "<current_conditions>"
    			else if (qName.equals("temp_f")) {
    				this.myWeatherSet.getWeatherCurrentCondition()
    						.setTempFahrenheit(Integer.parseInt(dataAttribute));
    			} else if (qName.equals("temp_c")) {
    				this.myWeatherSet.getWeatherCurrentCondition().setTempCelcius(
    						Integer.parseInt(dataAttribute));
    			} else if (qName.equals("humidity")) {
    				this.myWeatherSet.getWeatherCurrentCondition().setHumidity(
    						dataAttribute);
    			} else if (qName.equals("wind_condition")) {
    				this.myWeatherSet.getWeatherCurrentCondition()
    						.setWindCondition(dataAttribute);
    			}
    			// 'Inner' Tags within "<forecast_conditions>"
    			else if (qName.equals("low")) {
    				int temp = Integer.parseInt(dataAttribute);
    				if (this.usingSITemperature) {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setTempMinCelsius(temp);
    				} else {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setTempMinCelsius(
    									WeatherUtils.fahrenheitToCelsius(temp));
    				}
    			} else if (qName.equals("high")) {
    				int temp = Integer.parseInt(dataAttribute);
    				if (this.usingSITemperature) {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setTempMaxCelsius(temp);
    				} else {
    					this.myWeatherSet.getLastWeatherForecastCondition()
    							.setTempMaxCelsius(
    									WeatherUtils.fahrenheitToCelsius(temp));
    				}
    			}
    		}
    	}
 
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {
                if (qName.equals("forecast_information")) {
                        this.in_forecast_information = false;
                } else if (qName.equals("current_conditions")) {
                        this.in_current_conditions = false;
                } else if (qName.equals("forecast_conditions")) {
                        this.in_forecast_conditions = false;
                }
        }
 
        @Override
        public void characters(char ch[], int start, int length) {
                /*
                 * Would be called on the following structure: <element>characters</element>
                 */
        }
}

