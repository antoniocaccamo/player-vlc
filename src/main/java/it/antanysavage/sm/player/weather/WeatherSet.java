package it.antanysavage.sm.player.weather;

import java.util.ArrayList;

/**
 * Combines one WeatherCurrentCondition with a List of
 * WeatherForecastConditions.
 */
public class WeatherSet {
        
        // ===========================================================
        // Fields
        // ===========================================================
	
		private String forecastDate    = null;
		private String currentDateTime = null;
 
        private WeatherCurrentCondition myCurrentCondition = null;
        private ArrayList<WeatherForecastCondition> myForecastConditions = 
                new ArrayList<WeatherForecastCondition>(4);
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public WeatherCurrentCondition getWeatherCurrentCondition() {
                return myCurrentCondition;
        }
 
        public void setWeatherCurrentCondition(
                        WeatherCurrentCondition myCurrentWeather) {
                this.myCurrentCondition = myCurrentWeather;
        }
 
        public ArrayList<WeatherForecastCondition> getWeatherForecastConditions() {
                return this.myForecastConditions;
        }
 
        public WeatherForecastCondition getLastWeatherForecastCondition() {
                return this.myForecastConditions
                                .get(this.myForecastConditions.size() - 1);
        }

		public String getForecastDate() {
			return forecastDate;
		}

		public void setForecastDate(String forecastDate) {
			this.forecastDate = forecastDate;
		}

		public String getCurrentDateTime() {
			return currentDateTime;
		}

		public void setCurrentDateTime(String currentDateTime) {
			this.currentDateTime = currentDateTime;
		}
        
        
}
