package com.peviitor.app;

public class inetum {
    public static String pythonScrapersUrl = "http://localhost:8000/scraper/based_scraper_py/";
    public static String dataObject = '{' + "\"file\": " + "\"inetum.py\"" + '}';
    public static Object data = null;

    public static void main(String[] args) throws Exception {
        data = utils.makeRequest(pythonScrapersUrl, "POST", dataObject);
    }

}
