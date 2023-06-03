package com.peviitor.app;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class scrapers {
    public static String pythonScrapersUrl = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/";

    public static void main(String[] args) throws Exception {
        ArrayList<String> pythonScrapers = getPythonScrappers();
        System.out.println(pythonScrapers);
    }

    public static ArrayList<String> getPythonScrappers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // get all scrappers
        String allScrappers = utils.makeRequest(App.scraperUrl, "GET");

        // parse the response to get scrappers
        ArrayList responseObjScrappers = objectMapper.readValue(allScrappers, ArrayList.class);

        // convert JSON string to Map
        Object allScrapersObject = (Object) responseObjScrappers.get(1);

        ArrayList<String> pythonScrapers = new ArrayList<String>();

        // this is the list of all scrappers
        for (Map.Entry<String, Object> entry : ((Map<String, Object>) allScrapersObject).entrySet()) {
            Object value = entry.getValue();

            // transform the value to a JSON object
            String scraperObj = "{\"" + "file" + "\": " + "\"" + value.toString() + "\"" + "}";
            pythonScrapers.add(scraperObj);

        }

        return pythonScrapers;
    }
}
