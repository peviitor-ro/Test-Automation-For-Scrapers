package com.peviitor.app;

import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class hpTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            hp.main(null);
            data = hp.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "HP";
        String careersUrl = "https://jobs.hp.com/search-results/?business_unit[]=Romania";
        String jobElementSelector = "div[id='new-live-results']";
        String jobTitleSelector = ".jobHeaderInfo > h1";

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
                            ArrayList<String> result = new ArrayList<String>() {
                                {
                                    for (String i : s.split(" ")) {
                                        add(i);
                                    }
                                }
                            };
                            return result.get(result.size() - 1);
                        }
                    },
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            return s.replace("\u2013", "-")
                                    .replace("  ", " ");
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
