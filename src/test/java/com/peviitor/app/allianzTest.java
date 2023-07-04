package com.peviitor.app;
import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.testng.asserts.Assertion;

public class allianzTest {
    public static String results = "";

    public void compare() {
        String data = "";
        try {
            allianz.main(null);
            data = allianz.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/allianz.py/";
        String companyName = "Allianz";
        String careersUrl = "https://careers.allianz.com/en_US.html/search/?searchby=location&createNewAlert=false&q=&locationsearch=Romania&optionsFacetsDD_department=&optionsFacetsDD_shifttype=&optionsFacetsDD_customfield3=&optionsFacetsDD_customfield2=&optionsFacetsDD_facility=&optionsFacetsDD_customfield4=&inputSearchValue=Romania&quatFlag=false";
        String jobElementSelector = "div[class='cpSearchBar__itemJob']";
        String jobTitleClass = "h2[class='nx-heading--section']";
        
        try {
            results = new utilsTest().initiateTest(
                        data, 
                        scraperApiEndpoint, 
                        companyName, 
                        careersUrl, 
                        jobElementSelector, 
                        jobTitleClass, 
                        new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                Pattern pattern = Pattern.compile("\\((.*?)\\)");
                                Matcher matcher = pattern.matcher(s);
                                String result = "";
                                
                                if (matcher.find()) {
                                    result = matcher.group(1);
                                }
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
