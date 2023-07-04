package com.peviitor.app;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

class lsegUtils extends utilsTest {
    @Override
    public Boolean checkNumberOfJobs(
            String appData,
            String companyName,
            String careersUrl,
            String jobElementSelector,
            Function<String, String> callFunctions) throws Exception {

        // set the urls
        String peviitorUrl = "https://api.peviitor.ro/v1/companies/?count=true";

        // convert JSON string to Map
        Map<ArrayList, Object> scraperData = objectMapper.readValue(appData.toString(), Map.class);
        jobs = (ArrayList) scraperData.get("succes");

        // get the number of jobs from the scraper
        scraperJobs = jobs.size();

        // wait for the peviitor api to update
        TimeUnit.SECONDS.sleep(3);

        /* Making request to the Peviitor Api to get the number of jobs */
        // get the number of jobs from the scraper
        String peviitorData = utils.makeRequest(peviitorUrl, "GET");

        // parse the response to get the number of jobs
        Object responseObj = objectMapper.readValue(peviitorData, Object.class);

        // convert JSON string to Map
        Map<String, Object> map = (Map<String, Object>) responseObj;

        // transform the response to a list
        ArrayList<Object> peviitorJobsApi = (ArrayList<Object>) map.get("companies");

        for (Object job : peviitorJobsApi) {
            // get value of the key
            Map<String, Object> jobMap = (Map<String, Object>) job;
            if (jobMap.get("name").toString().equals(companyName)) {
                peviitorJobs = (int) jobMap.get("jobs");
            }
        }

        String careersResponse = utils.makeRequest(careersUrl, "POST", jobElementSelector);
        Map<String, Object> careersmap = objectMapper.readValue(careersResponse, Map.class);
        careerPageJobs = Integer.parseInt(careersmap.get("total").toString());

        return peviitorJobs == scraperJobs && peviitorJobs == careerPageJobs;
        
    };
}

public class lsegTest {
    public static String results = "";

    public static void compare() {
        String data = "";

        try {
            lseg.main(null);
            data = lseg.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/lseg.py/";
        String companyName = "LSEG";
        String careersUrl = "https://refinitiv.wd3.myworkdayjobs.com/wday/cxs/refinitiv/Careers/jobs";
        String jobElementSelector = "{\"appliedFacets\":{\"locationCountry\":[\"f2e609fe92974a55a05fc1cdc2852122\"]},\"limit\":20,\"offset\":0,\"searchText\":\"\"}";;
        String jobTitleSelector = "h2";

        try {
            results = new lsegUtils().initiateTest(
                    data,
                    scraperApiEndpoint,
                    companyName, careersUrl,
                    jobElementSelector,
                    jobTitleSelector
                    );
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
