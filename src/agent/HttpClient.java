package agent;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpClient {

    public String encode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }

    public String get(String urlString, boolean verbose) {
        try {
            if (verbose) {
                System.out.println("HttpClient GET: " + urlString);
            }

            URL url = URI.create(urlString).toURL();

            Scanner scanner = new Scanner(url.openStream());
            String response =
                    scanner.useDelimiter("\\A").hasNext()
                    ? scanner.next()
                    : "";
            scanner.close();

            if (verbose) {
                System.out.println("HttpClient response: " + response);
            }

            return response;

        } catch (IOException | IllegalArgumentException e) {
            return "Error: Unable to reach the server.";
        }
    }
}
