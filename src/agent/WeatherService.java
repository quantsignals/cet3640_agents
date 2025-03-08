package agent;

public class WeatherService extends BaseService {
	private static final String BASE_URL = "https://wttr.in/";
	private RestService restService;
	
    public WeatherService(boolean verbose, RestService restService) {
		super(verbose);
		this.restService = restService;
	}

	

	public void setVerbose(boolean verbose){
		super.setVerbose(verbose);
		restService.setVerbose(verbose);
	}
	
    @Override
    public String getType() {
        return "weather";
    }

    @Override
    public String handleRequest(String city) {
    	if(verbose) System.out.println("WeatherService request: "+city);
        if (city.isEmpty()) return "Error: Please provide a city name.";

        String response = restService.handleRequest(BASE_URL + encode(city) + "?format=%25C+%25t");
        if(verbose) System.out.println("WeatherService response: "+ response);
        if (response.startsWith("Error")) return response;

        // Extract weather condition and temperature from response
        int lastSpaceIndex = response.lastIndexOf(" ");
        if (lastSpaceIndex == -1) return "Error: Unexpected response from weather API.";

        String condition = response.substring(0, lastSpaceIndex); // Extract condition text
        String temperature = response.substring(lastSpaceIndex + 1).replace("+", "").replace("°F", "").trim(); // Extract numeric temperature

        if(verbose) System.out.println("WeatherService condition: "+condition);
        if(verbose) System.out.println("WeatherService temperature: "+temperature);
        return temperature;
    }
}
