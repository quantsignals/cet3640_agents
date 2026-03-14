package agent;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GenericService extends BaseService {

   // complete here

    public GenericService(
            boolean verbose,
            HttpClient httpClient,
            String type,
            String description,
            String inputDescription,
            String example,
            String urlTemplate,
            String extractPath,
            String resultPrefix,
            boolean inputOptional) {
    		super(verbose);
           // complete here
    }

    // complete below
    public String getType() {
        return "";
    }

    
    public String getDescription() {
        return "";
    }

    
    public String getInputDescription() {
        return "";
    }
    
    public String getExample() {
    	return "";
    }

    
    public String handleRequest(String request) {
       // complete here
       return "";
    }
 
     public static String extractPathAsString(String jsonText, String path) {
    	 if (path == null || path.trim().equals("")){
    		   return jsonText; // not a JSON case
    	   }
    	   
    	  JsonObject obj = JsonParser.parseString(jsonText).getAsJsonObject();
          String[] parts = path.split("\\.");


            for (int i = 0; i < parts.length - 1; i++) {
                obj = obj.getAsJsonObject(parts[i]);
            }

            return obj.get(parts[parts.length - 1]).getAsString();
       }
   
    
    
    
}
