package com.peviitor.app;

import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class lindeTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            linde.main(null);
            data = linde.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "Linde";
        String careersUrl = "https://linde.csod.com/ux/ats/careersite/20/home?c=linde&country=ro";
        String jobElementSelector = ".checkbox-label";
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
                            return result.get(result.size() - 1)
                                    .replace("(", "")
                                    .replace(")", "");
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
