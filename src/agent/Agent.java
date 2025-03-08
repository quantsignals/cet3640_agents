package agent;

import java.util.Scanner;

public class Agent {
	boolean verbose;
    private WeatherService weatherService; 
    private CalcService calcService;
    private RestService restService;

    
    public Agent(boolean verbose) {
    	this.verbose=verbose;
    	
    	restService = new RestService(verbose);
    	weatherService = new WeatherService(verbose, restService);
    	calcService = new CalcService(verbose, restService);
    }
    
    public void setVerbose(boolean verbose) {
    	this.verbose = verbose;
    	weatherService.setVerbose(verbose);
    	calcService.setVerbose(verbose);
    	restService.setVerbose(verbose);
    }
    
    public String handleRequest(String service, String request) {
        if (service == null || request == null) {
            return "Error: Input must contain 'service' and 'request'.";
        }

        // REQUEST VERBOSE 
        if(service.equalsIgnoreCase("verbose")) {
        	if(request.equalsIgnoreCase("on") || request.equalsIgnoreCase("true")) {
        		setVerbose(true);
        		return "Verbose mode enabled"; 
        	}
        	if(request.equalsIgnoreCase("off") || request.equalsIgnoreCase("false")){
        		setVerbose(false);
        		return "Verbose mode disabled"; 
        	}
        	return "Set verbose failed: choose on or off";
        }
        
        // REQUEST WEATHER
        if (service.equalsIgnoreCase(weatherService.getType())) {
            return weatherService.handleRequest(request);
        } 
        
        // REQUEST CALCULATION
        if (service.equalsIgnoreCase(calcService.getType())) {
            return calcService.handleRequest(request);
        }

        // REQUEST REST
        if (service.equalsIgnoreCase(restService.getType())) {
            return restService.handleRequest(request);
        }

        
        return "Unknown service: " + service + ". Available services: verbose, weather, calc, rest.";
    }


    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Type service and request.");
        System.out.println("Examples: ");
        System.out.println("calc 1+1");
        System.out.println("weather New York");
        System.out.println("rest https://api.mymemory.translated.net/?q=highway&langpair=en%7Cde");
        System.out.println("verbose on");        
        System.out.println("exit");
        
        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            int separator_index = input.indexOf(' ');
            if (separator_index == -1) {
                System.out.println("Error: Input must be in format 'service request'");
                continue;
            }

            String service = input.substring(0,separator_index);
            String message = input.substring(separator_index+1);
            System.out.println("Result: "+handleRequest(service, message));
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    public static void main(String[] args) {
        new Agent(false).startConsole();
    }
}
