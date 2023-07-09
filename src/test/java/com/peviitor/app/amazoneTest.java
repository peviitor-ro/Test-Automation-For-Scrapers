package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class amazoneTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            amazon.main(null);
            data = amazon.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "Amazon";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://www.amazon.jobs/en/locations/bucharest-romania";
        String jobElementSelector = "span.job-count > span";
        String jobTitleSelector = "h1[class='title']";

        try {
            results = new utilsTest().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName,
                    careersUrl,
                    jobElementSelector,
                    jobTitleSelector,
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            String result = s.replace("(", "")
                                            .replace(")", "");
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
