package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;
import java.util.Map;

import com.google.common.base.Function;

import java.net.URL;
import java.net.HttpURLConnection;


class medlifeUtils extends utilsTest {
    @Override
    public Boolean checkLink(String jobTitleSelector, Function<String, String> callFunctions) throws Exception {

        // get the jobs from the scraper
        for (Object job : jobs) {
            
            // convert JSON string to Map
            Map<String, Object> jobMap = (Map<String, Object>) job;

            try {
                // get status code
                HttpURLConnection cn = (HttpURLConnection)new URL(jobMap.get("job_link").toString() ).openConnection();
                int status = cn.getResponseCode();

                // check if the job title is the same
                if (status == 200) {
                    data = "{\"" + "is_success" + "\": " + "\"" + "Pass" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                            + "Automated test passed" + "\"}";
                    System.out.println("Automated test passed");
                } else { // if the job title is not the same
                    data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": "
                            + "\"" + "Job title is not the same" + "\"}";
                    System.out.println("Automated test failed");
                    System.out.println("Status code is not 200");
                    testResult = "false";
                    break;
                }

            } catch (Exception e) { // if the job link is not valid
                data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                        + e + "\"}";
                System.out.println(e);
                testResult = "false";
                break;
            }
        }
        return testResult == "true";
    }
}

public class medlifeTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            medlife.main(null);
            data = medlife.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/medlife.py/";
        String companyName = "Medlife";
        String careersUrl = "https://www.medlife.ro/cariere/lista-joburi";
        String jobElementSelector = ".title-header-listing > div > p";
        String jobTitleSelector = ".mlj-aplicatie-title-job";

        try {
            results = new medlifeUtils().initiateTest(
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
