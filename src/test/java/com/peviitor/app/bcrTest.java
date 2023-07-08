package com.peviitor.app;

import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class bcrTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            bcr.main(null);
            data = bcr.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "BCR";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://erstegroup-careers.com/bcr/search/?createNewAlert=false&q=&locations";
        String jobElementSelector = "span[class='paginationLabel']";
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
