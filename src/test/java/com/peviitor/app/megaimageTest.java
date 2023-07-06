package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class megaimageTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            megaimage.main(null);
            data = megaimage.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/MegaImage.py/";
        String companyName = "MegaImage";
        String careersUrl = "https://cariere.mega-image.ro/joburi";
        String jobElementSelector = "span[class='results']";
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
                            return s.split(" ")[0].replace("(", "");
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
