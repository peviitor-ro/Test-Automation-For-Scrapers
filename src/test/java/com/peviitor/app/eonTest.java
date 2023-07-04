package com.peviitor.app;
import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class eonTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            eon.main(null);
            data = eon.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/eon.py/";
        String companyName = "Eon";
        String careersUrl = "https://careers.eon.com/romania/go/Toate-joburile-din-Romania/3727401/?q=&sortColumn=referencedate&sortDirection=desc";
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
                                ArrayList<String> result = new ArrayList<String>(){{
                                    for (String i : s.split(" ")) {
                                        add(i);
                                    }
                                }};
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
