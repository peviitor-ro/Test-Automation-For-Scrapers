package com.peviitor.app;

import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class novartisTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            novartis.main(null);
            data = novartis.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "Novartis";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://www.novartis.com/ro-ro/cariere/cauta-un-job?search_api_fulltext=&country%5B0%5D=LOC_RO&early_talent=All&page=0";
        String jobElementSelector = "div[class='view-header']";
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
                            ArrayList<String> result = new ArrayList<String>() {
                                {
                                    for (String i : s.split(" ")) {
                                        add(i);
                                    }
                                }
                            };
                            return result.get(result.size() - 2);
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
