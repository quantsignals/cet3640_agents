package agent;

import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public abstract class BaseService {

    protected boolean verbose;
    
	public abstract String getType();
    public abstract String handleRequest(String input);

    
    public BaseService(boolean verbose) {
    	this.verbose = verbose;
    }

    public void setVerbose(boolean verbose) {
    	this.verbose = verbose;
    }
    
    protected String encode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }

    protected String decode(String input) {
        return URLDecoder.decode(input, StandardCharsets.UTF_8);
    }
    
    protected String decodeUnicode(String input) {
    	 Properties properties = new Properties();
    	 try {
			properties.load(new StringReader("key=" + input));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return properties.getProperty("key");
    }    
}
