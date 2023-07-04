package com.peviitor.app;
import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class danoneTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            danone.main(null);
            data = danone.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "Danone";
        String careersUrl = "https://careers.danone.com/en-global/jobs.html?countries=Romania";
        String jobElementSelector = "span[class='dn-job-card-count__item']";
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
                                return s;
                            }
                        },
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.toUpperCase();
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
