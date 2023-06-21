package com.peviitor.app;
import com.google.common.base.Function;

public class adiTest {
    public static void main(String[] args) throws Exception {
        adi.main(null);
        String data = adi.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/adi.py/";
        String companyName = "ADI";
        String careersUrl = "https://analogdevices.wd1.myworkdayjobs.com/External?q=Romania";
        String jobElementSelector = "p.css-12psxof";
        String jobTitleSelector = "h2";
        utilsTest.initiateTest(
            data, 
            scraperApiEndpoint, 
            companyName, 
            careersUrl, 
            jobElementSelector, 
            jobTitleSelector, 
            new Function<String, String>() {
            @Override
            public String apply(String s) {
                // remove brackets
                String result = s.split(" ")[0];
                return result;
            }
        });
    }
}
