package agent;

import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class RestService extends BaseService{

	public RestService(boolean verbose) {
		super(verbose);
	}
	
	public String getType() {
    	return "rest";
    }
    	
    public String handleRequest(String sURL) {
        try {
        	if(verbose) System.out.println("Rest service request: "+sURL);
        	URL url = URI.create(sURL).toURL();
        	Scanner scanner = new Scanner(url.openStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            if(verbose) System.out.println("Rest service response: "+ response);
            return response;
        } catch (Exception e) {
            return "Rest Service Error: Unable to reach the server.";
        }
    }

}
