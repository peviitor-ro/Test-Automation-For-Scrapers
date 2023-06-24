package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;


public class adobeTest {
    public static String results = "";
    public void compare() {
        String data = "";

        try{
            adobe.main(null);
            data = adobe.data.toString();
        }
        catch(Exception e){
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/adobe.py/";
        String companyName = "Adobe";
        String careersUrl = "https://careers.adobe.com/us/en/search-results?keywords=Romania";
        String jobElementSelector = "span[class='result-count']";
        String jobTitleSelector = "h1[class='job-title']";
        try {
            results = utilsTest.initiateTest(
                data, 
                scraperApiEndpoint, 
                companyName, 
                careersUrl, 
                jobElementSelector, 
                jobTitleSelector
            );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void test() {
        compare();
        Assertion assertion = new Assertion();
        assertion.assertEquals( results, "true");
    }
}
