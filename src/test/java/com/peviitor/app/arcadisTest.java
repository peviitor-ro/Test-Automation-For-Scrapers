package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class arcadisTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            arcadis.main(null);
            data = arcadis.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/arcadis.py/";
        String companyName = "Arcadis";
        String careersUrl = "https://careers.arcadis.com/search-results?qkstate=Romania";
        String jobElementSelector = "span[class='result-count']";
        String jobTitleSelector = "h1[class='heading job-details__title']";

        try {
            results = utilsTest.initiateTest(
                        data,
                        scraperApiEndpoint,
                        companyName, careersUrl,
                        jobElementSelector,
                        jobTitleSelector,
                        null,
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.replaceAll("\\s", "");
                                return result;
                            }
                        });
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
