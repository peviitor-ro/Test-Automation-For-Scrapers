package com.peviitor.app;

import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class electronicartsTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            electronicarts.main(null);
            data = electronicarts.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "ElectronicArts";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://ea.gr8people.com/jobs?page=1&geo_location=ChIJw3aJlSb_sUARlLEEqJJP74Q";
        String jobElementSelector = "span[class='btn-toolbar-text']";
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
                            return result.get(result.size() - 1);
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
