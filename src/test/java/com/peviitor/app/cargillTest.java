package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class cargillTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            cargill.main(null);
            data = cargill.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/cargill.py/";
        String companyName = "Cargill";
        String careersUrl = "https://careers.cargill.com/en/search-jobs/Romania/23251/2/798549/46/25/50/2";
        String jobElementSelector = "section[id='search-results']";
        String jobTitleSelector = "div[class='jd-text-info']";

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
                                String result = s.split(" ")[0];
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
