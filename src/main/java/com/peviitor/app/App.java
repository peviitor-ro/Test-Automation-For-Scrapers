package com.peviitor.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Demo App
 *
 */
public class App {

    public static String peviitorUrl = " https://api.peviitor.ro/v3/search/?q=inetum&country=Rom%C3%A2nia&page=1";
    public static String scraperUrl = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/";

    public static int peviitorJobs = 0;
    public static int scraperJobs = 0;
    public static int careerPageJobs = 0;

    public static void main(String[] args) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        /*  Making request to the Scraper Api to get the number of jobs */
        // get the number of jobs from the scraper
        String data = '{' + "\"file\": " + "\"inetum.py\"" + '}';
        String scraperData = makeRequest(scraperUrl, "POST", data);

        // // parse the response to get the number of jobs
        Object responseObjScraper = objectMapper.readValue(scraperData, Object.class);

        // convert JSON string to Map
        Map<String, Object> mapScraper = (Map<String, Object>) responseObjScraper;
        ArrayList<Object> scraperJobsApi = (ArrayList<Object>) mapScraper.get("succes");

        // split the response to get the number of jobs
        scraperJobs = Integer.parseInt(scraperJobsApi.get(scraperJobsApi.size() - 4).toString().split(" ")[2]);
        /*********************************************************/

        // wait for the peviitor api to update
        TimeUnit.SECONDS.sleep(5);

        /*  Making request to the Peviitor Api to get the number of jobs */
        // get the number of jobs from the scraper
        String peviitorData = makeRequest(peviitorUrl, "GET");

        // parse the response to get the number of jobs
        Object responseObj = objectMapper.readValue(peviitorData, Object.class);

        // convert JSON string to Map
        Map<String, Object> map = (Map<String, Object>) responseObj;
        Map<String, Object> peviitorJobsApi = (Map<String, Object>) map.get("response");

        peviitorJobs = (int) peviitorJobsApi.get("numFound");
        /*********************************************************/

        /*  Making request to the career page to get the number of jobs */
        Document doc = Jsoup.connect("https://www.inetum.com/en/jobs?facets_query=&search=&f%5B0%5D=region%3A1068").get();
        
        // get the number of jobs from the career page
        Element element = doc.select("#1068").first();

        careerPageJobs = Integer.parseInt(element.text().replace("(", "").replace(")", "").split(" ")[2]);

        System.out.println(scraperJobs);
        System.out.println(peviitorJobs);
        System.out.println(careerPageJobs);

    }

    // This method makes a request to the given url , method and data and returns the response
    public static String makeRequest(String url, String method, String... data) throws IOException {

        // create the url
        URL apiUrl = new URL(url);
        // open the connection
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        // set the request method
        connection.setRequestMethod(method);

        // set the request headers
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // set the request body
        if (data.length > 0) {
            connection.getOutputStream().write(data[0].getBytes());
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
