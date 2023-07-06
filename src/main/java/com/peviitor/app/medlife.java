package com.peviitor.app;

public class medlife {
    public static String pythonScrapersUrl = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/";
    public static String dataObject = '{' + "\"file\": " + "\"medlife.py\"" + '}';
    public static Object data = null;

    public static void main(String[] args) throws Exception {
        data = utils.makeRequest(pythonScrapersUrl, "POST", dataObject);
    }
}
