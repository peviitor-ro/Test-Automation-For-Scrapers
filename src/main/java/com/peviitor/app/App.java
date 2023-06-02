package com.peviitor.app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;


/**
 * Demo App
 *
 */
public class App {

    public static String peviitorUrl = "https://api.peviitor.ro/v3/search/?q=continental&country=Rom%C3%A2nia&page=1";
    public static String scraperUrl = "https://www.peviitor.ro/locuri-de-munca/continental";
    public static int peviitorJobs = 0;
    public static int scraperJobs = 0;
    public static void main(String[] args) throws Exception {
            // get the number of jobs from the scraper
            String peviitorData = makeRequest(peviitorUrl, "GET");

            // parse the response to get the number of jobs
            ObjectMapper objectMapper = new ObjectMapper();
            Object responseObj = objectMapper.readValue(peviitorData, Object.class);

            // convert JSON string to Map
            Map<String, Object> map = (Map<String, Object>) responseObj;
            
            Map<String, Object> peviitorJobsApi = (Map<String, Object>) map.get("response");

            peviitorJobs = (int) peviitorJobsApi.get("numFound");

    }

    // This method makes a request to the given url and returns the response as a string
    public static String makeRequest(String url, String method ) throws IOException {

        // create the url
        URL apiUrl = new URL(url);
        // open the connection
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        // set the request method
        connection.setRequestMethod(method);

        // set the request headers
        if (method == "POST") {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
        }

        // get the response code
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // close the connection
        reader.close();
        connection.disconnect();

        return response.toString();
    }

}
