package it.antanysavage.sm.player.weather;

public class WeatherUtils {
	 
    public static int fahrenheitToCelsius(int tFahrenheit) {
            return (int) ((5.0f / 9.0f) * (tFahrenheit - 32));
    }

    public static int celsiusToFahrenheit(int tCelsius) {
            return (int) ((9.0f / 5.0f) * tCelsius + 32);
    }
}
