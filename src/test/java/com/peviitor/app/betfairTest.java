package com.peviitor.app;
import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class betfairTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            betfair.main(null);
            data = betfair.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/betfair.py/";
        String companyName = "Betfair";
        String careersUrl = "https://www.betfairromania.ro/find-a-job/?search=&country=Romania&pagesize=1000#results";
        String jobElementSelector = "p[class='job-count']";
        String jobTitleSelector = "h2";

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
                                return result.get(result.size() - 3);
                            }
                        },
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                String result = s.toUpperCase();
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
