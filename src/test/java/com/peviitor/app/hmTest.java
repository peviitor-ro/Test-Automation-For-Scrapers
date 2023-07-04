package com.peviitor.app;

import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class hmTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            hm.main(null);
            data = hm.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "HM";
        String careersUrl = "https://career.hm.com/search/?l=cou%3Aro";
        String jobElementSelector = "h6[class='jobs-results-bar__text']";
        String jobTitleSelector = "div[class='text text--title-large bold-red']";

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
                            return result.get(result.size() - 2);
                        }
                    },
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            return s.replace("Stores", "")
                                    .replace(" ", "")
                                    .replace("Steering&Leadership", "");
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
