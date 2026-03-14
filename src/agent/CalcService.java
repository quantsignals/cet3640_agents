package agent;

public class CalcService extends BaseService {

    private static final String API_URL =
            "https://api.mathjs.org/v4/?expr=";

    private final HttpClient httpClient;

    public CalcService(boolean verbose, HttpClient httpClient) {
        super(verbose);
        this.httpClient = httpClient;
    }

    @Override
    public String getType() {
        return "calc";
    }

    @Override
    public String getDescription() {
        return "Evaluates a math expression, for example: calc 2*3+5";
    }

    @Override
    public String getInputDescription() {
        return "A mathematical expression";
    }
    
	public String getExample() { 
		return "calc 2*3+5"; 
	}

    @Override
    public String handleRequest(String request) {
        if (verbose) {
            System.out.println("CalcService request: " + request);
        }

        if (request == null || request.trim().isEmpty()) {
            return "Error: Please provide an expression.";
        }

        String encoded =
                httpClient.encode("(" + request.trim() + ")");

        String response =
                httpClient.get(API_URL + encoded, verbose);

        if (verbose) {
            System.out.println("CalcService response: " + response);
        }

        return response;
    }
}
