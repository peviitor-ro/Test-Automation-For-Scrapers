package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class enelTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            enel.main(null);
            data = enel.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/astrazeneca.py/";
        String companyName = "Enel";
        String careersUrl = "https://jobs.enel.com/en_US/careers/JobOpeningsRomania";
        String jobElementSelector = "p[class='section__header__text__title section__header__text__title--9']";
        String jobTitleSelector = "h2";

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
