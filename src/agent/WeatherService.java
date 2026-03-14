package agent;

import com.google.gson.Gson;

public class WeatherService extends BaseService {

    private static final String BASE_URL =
            "https://weather.m-a-rhode.workers.dev?city=";

    private final HttpClient httpClient;
    private final Gson gson;

    public WeatherService(boolean verbose, HttpClient httpClient) {
        super(verbose);
        this.httpClient = httpClient;
        this.gson = new Gson();
    }

    @Override
    public String getType() {
        return "weather";
    }

    @Override
    public String getDescription() {
        return "Gets weather for a city, for example: weather New York";
    }

    @Override
    public String getInputDescription() {
        return "A city name";
    }
    
    public String getExample() {
    	return "weather New York";
    }

    @Override
    public String handleRequest(String city) {
        if (verbose) {
            System.out.println("WeatherService request: " + city);
        }

        if (city == null || city.trim().isEmpty()) {
            return "Error: Please provide a city name.";
        }

        String encoded = httpClient.encode(city.trim());
        String response = httpClient.get(BASE_URL + encoded, verbose);

        if (verbose) {
            System.out.println("WeatherService raw response: " + response);
        }

        if (response.startsWith("Error:")) {
            return response;
        }

        try {
            WeatherApiResponse weatherResponse =
                    gson.fromJson(response, WeatherApiResponse.class);

            if (weatherResponse == null
                    || weatherResponse.resolved_location == null
                    || weatherResponse.weather == null
                    || weatherResponse.weather.current_weather == null
                    || weatherResponse.weather.current_weather_units == null) {
                return "Error: Unable to parse weather response.";
            }

            String cityName = weatherResponse.resolved_location.name;
            double temperature = weatherResponse.weather.current_weather.temperature;
            String temperatureUnit =
                    weatherResponse.weather.current_weather_units.temperature;
            double windspeed = weatherResponse.weather.current_weather.windspeed;
            String windspeedUnit =
                    weatherResponse.weather.current_weather_units.windspeed;

            String result = cityName
                    + ": "
                    + temperature + " " + temperatureUnit
                    + ", wind " + windspeed + " " + windspeedUnit;

            if (verbose) {
                System.out.println("WeatherService parsed result: " + result);
            }

            return result;

        } catch (Exception e) {
            return "Error: Unable to parse weather response.";
        }
    }

    private static class WeatherApiResponse {
        ResolvedLocation resolved_location;
        WeatherData weather;
    }

    private static class ResolvedLocation {
        String name;
    }

    private static class WeatherData {
        CurrentWeatherUnits current_weather_units;
        CurrentWeather current_weather;
    }

    private static class CurrentWeatherUnits {
        String temperature;
        String windspeed;
    }

    private static class CurrentWeather {
        double temperature;
        double windspeed;
        int weathercode;
    }
}
