package com.peviitor.app;
import com.google.common.base.Function;

public class inetumTest {

    public static void main( String[] args ) throws Exception {
        inetum.main(null);
        String data = inetum.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/inetum.py/";
        String companyName = "Inetum";
        String careersUrl = "https://www.inetum.com/en/jobs?facets_query=&search=&f%5B0%5D=region%3A1068";
        String jobElementSelector = "#1068 > label > span.facet-item__count";
        String jobTitleClass = "h1[class='card-title']";
        utilsTest.initiateTest(data, scraperApiEndpoint, companyName, careersUrl, jobElementSelector, jobTitleClass, new Function<String, String>() {
            @Override
            public String apply(String s) {
                // remove brackets
                String result = s.replaceAll("[\\[\\](){}]", "");
                return result;
            }
        },
        new Function<String, String>() {
            @Override
            public String apply(String s) {
                // remove spaces
                String result = s.replaceAll("\\s", "");
                return result;
            }
        }
        );
    }
}
