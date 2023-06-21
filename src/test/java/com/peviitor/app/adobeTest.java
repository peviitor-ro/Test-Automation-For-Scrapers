package com.peviitor.app;

public class adobeTest {
    public static void main(String[] args) throws Exception {
        adobe.main(null);
        String data = adobe.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/adobe.py/";
        String companyName = "Adobe";
        String careersUrl = "https://careers.adobe.com/us/en/search-results?keywords=Romania";
        String jobElementSelector = "span[class='result-count']";
        String jobTitleSelector = "h1[class='job-title']";
        utilsTest.initiateTest(
            data, 
            scraperApiEndpoint, 
            companyName, 
            careersUrl, 
            jobElementSelector, 
            jobTitleSelector
        );
    }
}
