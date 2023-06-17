package com.peviitor.app;

public class adpTest {
    public static void main(String[] args) throws Exception {
        adp.main(null);
        String data = adp.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/adp.py/";
        String companyName = "ADP";
        String careersUrl = "https://jobs.adp.com/job-search-results/?language=en&location=Romania&country=RO&radius=25";
        String jobElementSelector = "span[id='live-results-counter']";
        String jobTitleClass = "h1[class='jobdetail-title']";
        utilsTest.initiateTest(data, scraperApiEndpoint, companyName, careersUrl, jobElementSelector, jobTitleClass);
    }
}
