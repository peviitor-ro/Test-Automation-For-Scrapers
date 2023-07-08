package com.peviitor.app;

import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class auchanTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            auchan.main(null);
            data = auchan.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "Auchan";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://cariere.auchan.ro";
        String jobElementSelector = "span[class='k-pager-info k-label']";
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
                    },
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            String result = s.replace(" ", "");
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
