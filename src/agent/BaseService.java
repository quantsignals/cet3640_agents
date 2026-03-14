package agent;

public abstract class BaseService {

    protected boolean verbose;

    public BaseService(boolean verbose) {
        this.verbose = verbose;
    }

    public abstract String getType();

    public abstract String getDescription();

    public abstract String getInputDescription();
    
    public abstract String getExample();

    public abstract String handleRequest(String input);

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
