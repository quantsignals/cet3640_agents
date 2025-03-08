package agent;


public class CalcService extends BaseService {
	private static final String API_URL = "http://api.mathjs.org/v4/?expr=";
	private RestService restService;
    public CalcService(boolean verbose, RestService restService) {
		super(verbose);
		this.restService = restService;
	}
    
	public void setVerbose(boolean verbose){
		super.setVerbose(verbose);
		restService.setVerbose(verbose);
		
	}    

    @Override
    public String getType() {
        return "calc";
    }

    @Override
    public String handleRequest(String request) {
    	if(verbose) System.out.println("CalcService request: "+request);
        if (request.isEmpty()) return "Error: Please provide a request.";
        
        String response = restService.handleRequest(API_URL + encode("(" + request + ")"));
        if(verbose) System.out.println("CalcService response: "+response);
        return response;
    }
    
}
