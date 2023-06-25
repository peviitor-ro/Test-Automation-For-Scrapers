package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class astrazenecaTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            astrazeneca.main(null);
            data = astrazeneca.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "AstraZeneca";
        String careersUrl = "https://careers.astrazeneca.com/location/romania-jobs/7684/798549/2";
        String jobElementSelector = "h1";
        String jobTitleSelector = "h1";

        try {
            results = utilsTest.initiateTest(
                        data,
                        scraperApiEndpoint,
                        companyName, careersUrl,
                        jobElementSelector,
                        jobTitleSelector,
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.split(" ")[0];
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
