package com.peviitor.app;

import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class goodyearTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            goodyear.main(null);
            data = goodyear.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "GoodYear";
        String careersUrl = "https://jobs.goodyear.com/search/?createNewAlert=false&q=&locationsearch=Romania";
        String jobElementSelector = "span[class='paginationLabel']";
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
                            return s.replace("  ", " ");
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
