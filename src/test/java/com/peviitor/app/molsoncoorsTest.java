package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

import java.util.ArrayList;

import com.google.common.base.Function;

public class molsoncoorsTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            molsoncoors.main(null);
            data = molsoncoors.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "MolsonCoors";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://jobs.molsoncoors.com/MolsonCoors_GBSRomania/search/?q=Romania&startrow=1";
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
                        };
                    },
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            return s.replace("Title:", "");
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
