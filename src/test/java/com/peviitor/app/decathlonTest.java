package com.peviitor.app;
import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class decathlonTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            decathlon.main(null);
            data = decathlon.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "Decathlon";
        String careersUrl = "https://apply.workable.com/decathlon-romania/";
        String jobElementSelector = "small[class='styles--1qZQx']";
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
                                return s.split(" ")[0];
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
