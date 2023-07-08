package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class adpTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            adp.main(null);
            data = adp.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "ADP";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://jobs.adp.com/job-search-results/?language=en&location=Romania&country=RO&radius=25";
        String jobElementSelector = "span[id='live-results-counter']";
        String jobTitleSelector = "h1[id='gtm-jobdetail-title']";

        try {
            results = new utilsTest().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName, careersUrl,
                    jobElementSelector,
                    jobTitleSelector,
                    null,
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            String result = s
                                    .replaceAll("\\s", "")
                                    .replaceAll("--", "\u2014");
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
