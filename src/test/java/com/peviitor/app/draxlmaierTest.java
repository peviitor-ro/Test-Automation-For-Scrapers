package com.peviitor.app;
import java.util.ArrayList;
import org.junit.Test;
import org.testng.asserts.Assertion;
import com.google.common.base.Function;

public class draxlmaierTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            draxlmaier.main(null);
            data = draxlmaier.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/draxlmaier.py/";
        String companyName = "Draxlmaier";
        String careersUrl = "https://d-career.org/Draexlmaier/go/DR\u00C4XLMAIER-Job-Opportunities-in-Romania-%28Romanian%29/4196801/?q=&sortColumn=referencedate&sortDirection=desc";
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
                                String result = s.replace("  ", " ");
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
