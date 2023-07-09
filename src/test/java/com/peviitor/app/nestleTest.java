package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;
import java.util.ArrayList;

public class nestleTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            nestle.main(null);
            data = nestle.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "Nestle";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://www.nestle.ro/jobs/search-jobs?keyword=Romania&country=&location=&career_area=All";
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
