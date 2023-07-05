package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.Map;

class medicoverUtils extends utilsTest {
    @Override
    public String initiateTest(
            String appData,
            String scraperApiEndpoint,
            String companyName,
            String careersUrl,
            // this selectors must be visible on the careers page
            String jobElementSelector,
            String jobTitleSelector,
            Function<String, String>... callFunctions) throws Exception {

        System.out.println("Testing " + companyName + " scraper...");

        String apiEndpoint = scraperApiEndpoint;

        Boolean firstTestResult = checkNumberOfJobs(appData, companyName, careersUrl, jobElementSelector, null); // first test is true because the number of jobs is not displayed on the
                                        // careers page

        // check if the number of jobs is the same
        if (firstTestResult) {
            // if the number of jobs is the same, then the test is passed
            System.out.println("First test passed");

            Boolean secondTestResult = false;
            if (callFunctions.length >= 2) {
                secondTestResult = checkLink(jobTitleSelector, callFunctions[1]);
            } else {
                secondTestResult = checkLink(jobTitleSelector, null);
            }

            if (secondTestResult) {
                System.out.println("Second test passed");

            } else {
                System.out.println("Second test failed");
            }

        } else { // if the number of jobs is not the same
            System.out.println("First Test failed!");
            System.out.println("Peviitor: " + peviitorJobs);
            System.out.println("Scraper: " + scraperJobs);
            System.out.println("Career page: " + careerPageJobs);

            data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                    + "Number of jobs is not the same" + "\"}";
            testResult = "false";
        }
        // // make request to the api to save the test result
        // utils.makeRequest(apiEndpoint, "POST", data);
        return testResult;
    };

    @Override
    public Boolean checkNumberOfJobs(
            String appData,
            String companyName,
            String careersUrl,
            String jobElementSelector,
            Function<String, String> callFunction) throws Exception {

        Map<ArrayList, Object> scraperData = objectMapper.readValue(appData.toString(), Map.class);
        jobs = (ArrayList) scraperData.get("succes");   
        return true;
    };
}

public class medicoverTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            medicover.main(null);
            data = medicover.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/medicover.py/";
        String companyName = "Medicover";
        String careersUrl = null;
        String jobElementSelector = null;
        String jobTitleSelector = "div[class='job-title']";

        try {
            results = new medicoverUtils().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName, careersUrl,
                    jobElementSelector,
                    jobTitleSelector,
                    null,
                    new Function<String, String>() {
                        @Override
                        public String apply(String s) {
                            return s.replace(" ", "");
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
