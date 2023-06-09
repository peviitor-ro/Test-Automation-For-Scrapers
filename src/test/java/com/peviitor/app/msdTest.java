package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

public class msdTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            msd.main(null);
            data = msd.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "MSD";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://jobs.msd.com/gb/en/search-results?qcountry=Romania";
        String jobElementSelector = "span[class='result-count']";
        String jobTitleSelector = "h1";

        try {
            results = new utilsTest().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName, careersUrl,
                    jobElementSelector,
                    jobTitleSelector);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void test() {
        compare();
        Assertion assertion = new Assertion();
        assertion.assertEquals(results, "true");
    }
}
