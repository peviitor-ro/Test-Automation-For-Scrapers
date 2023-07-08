package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class intelTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            intel.main(null);
            data = intel.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "Intel";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://jobs.intel.com/en/search-jobs/Romania/599/2/798549/46/25/50/2";
        String jobElementSelector = "#search-results > h1";
        String jobTitleSelector = "h1";

        try {
            results = new utilsTest().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName, careersUrl,
                    jobElementSelector,
                    jobTitleSelector,
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            return s.split(" ")[2];
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
