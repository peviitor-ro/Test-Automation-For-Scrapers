package com.peviitor.app;
import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

public class adiTest {
    public static String results = "";
    public void compare() {
        String data = "";
        try{
            adi.main(null);
            data = adi.data.toString();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/adi.py/";
        String companyName = "ADI";
        String careersUrl = "https://analogdevices.wd1.myworkdayjobs.com/External?q=Romania";
        String jobElementSelector = "p.css-12psxof";
        String jobTitleSelector = "h2";
        try {
            results = utilsTest.initiateTest(
                data, 
                scraperApiEndpoint, 
                companyName, 
                careersUrl, 
                jobElementSelector, 
                jobTitleSelector, 
                new Function<String, String>() {
                @Override
                public String apply(String s) {
                    String result = s.split(" ")[0];
                    return result;
                }
            });
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void test() {
        compare();
        Assertion assertion = new Assertion();
        assertion.assertEquals( results, "true");
    }
}
