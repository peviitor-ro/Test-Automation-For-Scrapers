package com.peviitor.app;
import com.google.common.base.Function;

public class amazoneTest {
    public static void main(String[] args) throws Exception {
        amazon.main(null);
        String data = amazon.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/amazon.py/";
        String companyName = "Amazon";
        String careersUrl = "https://www.amazon.jobs/en/locations/bucharest-romania";
        String jobElementSelector = "span.job-count > span.sr-only";
        String jobTitleSelector = "h1[class='title']";
        utilsTest.initiateTest(
            data, 
            scraperApiEndpoint, 
            companyName, 
            careersUrl, 
            jobElementSelector, 
            jobTitleSelector, 
            new Function<String, String>() {
            @Override
            public String apply(String s) {
                String result = s.split(" ")[0];
                return result;
            }
        },
        new Function<String, String>() {
            @Override
            public String apply(String s) {
                String result = s.replaceAll(" ", "");
                return result;
            }
        });
    }
}
