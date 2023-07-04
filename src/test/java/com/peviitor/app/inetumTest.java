package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class inetumTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            inetum.main(null);
            data = inetum.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/inetum.py/";
        String companyName = "Inetum";
        String careersUrl = "https://www.inetum.com/en/jobs?facets_query=&search=&f%5B0%5D=region%3A1068";
        String jobElementSelector = "#1068 > label > span.facet-item__count";
        String jobTitleSelector = "h1[class='card-title']";
        
        try {
            results = new utilsTest().initiateTest(
                        data, 
                        scraperApiEndpoint, 
                        companyName, 
                        careersUrl, 
                        jobElementSelector, 
                        jobTitleSelector, 
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.replaceAll("[\\[\\](){}]", "");
                                return result;
                        }
                        },
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.replaceAll("\\s", "");
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
