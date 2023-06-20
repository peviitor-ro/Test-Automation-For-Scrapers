package com.peviitor.app;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class allianzTest {
    public static void main(String[] args) throws Exception {
        allianz.main(null);
        String data = allianz.data.toString();
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/allianz.py/";
        String companyName = "Allianz";
        String careersUrl = "https://careers.allianz.com/en_US.html/search/?searchby=location&createNewAlert=false&q=&locationsearch=Romania&optionsFacetsDD_department=&optionsFacetsDD_shifttype=&optionsFacetsDD_customfield3=&optionsFacetsDD_customfield2=&optionsFacetsDD_facility=&optionsFacetsDD_customfield4=&inputSearchValue=Romania&quatFlag=false";
        String jobElementSelector = "div[class='cpSearchBar__itemJob']";
        String jobTitleClass = "h2[class='nx-heading--section']";
        utilsTest.initiateTest(data, scraperApiEndpoint, companyName, careersUrl, jobElementSelector, jobTitleClass, new Function<String, String>() {
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
    }
}
