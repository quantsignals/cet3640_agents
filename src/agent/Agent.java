package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agent {
    private boolean verbose;
    private final List<BaseService> services;

    public Agent(boolean verbose) {
        this.verbose = verbose;
        this.services = new ArrayList<>();

        HttpClient httpClient = new HttpClient();
        
        
        services.add(new GenericService(
        	    verbose,
        	    httpClient,
        	    "temperature",
        	    "Gets the current temperature for a city",
        	    "a city name",
        	    "temperature New York",
        	    "https://weather.m-a-rhode.workers.dev?city={input}",
        	    "weather.current_weather.temperature",
        	    "Temperature: ",
        	    false
        	));

        services.add(new CalcService(verbose, httpClient));
        services.add(new WeatherService(verbose, httpClient));

    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
        for (BaseService service : services) {
            service.setVerbose(verbose);
        }
    }

    public String listServices() {
        String str = "Available services:\n";

        for (BaseService service : services) {
        	str = str + "- " + service.getType() +"\n";
        	str = str + "  Description: " + service.getDescription() + "\n";
        	str = str + "  Input: " + service.getInputDescription() + "\n\n";
        }

        return str;
    }

    public BaseService findServiceByType(String type) {
        for (BaseService service : services) {
            if (service.getType().equalsIgnoreCase(type)) {
                return service;
            }
        }
        return null;
    }

    public String handleRequest(String serviceType, String request) {
        if (serviceType == null || request == null) {
            return "Error: Input must include both a service and a request.";
        }

        if (serviceType.equalsIgnoreCase("verbose")) {
            if (request.equalsIgnoreCase("on") || request.equalsIgnoreCase("true")) {
                setVerbose(true);
                return "Verbose mode enabled.";
            }
            if (request.equalsIgnoreCase("off") || request.equalsIgnoreCase("false")) {
                setVerbose(false);
                return "Verbose mode disabled.";
            }
            return "Error: Use 'verbose on' or 'verbose off'.";
        }

        if (serviceType.equalsIgnoreCase("services") || serviceType.equalsIgnoreCase("help")) {
            return listServices();
        }

        BaseService service = findServiceByType(serviceType);
        if (service == null) {
            return "Unknown service: " + serviceType + ". Type 'services' to list available services.";
        }

        if (verbose) {
            System.out.println("Agent dispatching to service: " + service.getType());
        }

        return service.handleRequest(request);
    }

    public void startConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Agent Service Console.");
        System.out.println("Type 'services' to list services.");
        System.out.println("Examples:");
        for(BaseService service : services) {
        	System.out.println("  "+service.getExample());
        }
        System.out.println("  verbose on");
        System.out.println("  exit");
        
        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (input.equalsIgnoreCase("services") || input.equalsIgnoreCase("help")) {
                System.out.println(listServices());
                continue;
            }

            int spaceIndex = input.indexOf(' ');
            if (spaceIndex == -1) {
                System.out.println("Error: Input must be in the form 'service request'.");
                continue;
            }

            String serviceType = input.substring(0, spaceIndex).trim();
            String request = input.substring(spaceIndex + 1).trim();

            String result = handleRequest(serviceType, request);
            System.out.println("Result: " + result);
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    public static void main(String[] args) {
    	new Agent(false).startConsole();
    }
}
