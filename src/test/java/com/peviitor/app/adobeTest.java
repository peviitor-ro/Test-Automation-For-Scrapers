package com.peviitor.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import java.nio.file.FileSystems;
import java.util.concurrent.TimeUnit;

public class adobeTest {
    public static void main(String[] args) throws Exception {
        // Initialize the adi class
        adobe.main(null);

        // set the urls
        String peviitorUrl = "https://api.peviitor.ro/v1/companies/?count=true";
        String apiEndpoint = "http://localhost:8000/scraper/based_scraper_py/adobe.py/";

        // initialize the variables for the number of jobs from each source
        int peviitorJobs = 0;
        int scraperJobs = 0;
        int careerPageJobs = 0;

        ObjectMapper objectMapper = new ObjectMapper();

        // get path to the chromedriver
        Path currePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String currentPathString = currePath.toString();

        // set the path to the chromedriver
        System.setProperty("webdriver.chrome.driver",
                currentPathString + "/src/test/java/com/peviitor/app/chromedriver");

        // initialize the webdriver
        WebDriver driver = new ChromeDriver();

        // the data object for the request
        String data = "";

        // convert JSON string to Map
        Map<ArrayList, Object> adiData = objectMapper.readValue(adobe.data.toString(), Map.class);
        ArrayList jobs = (ArrayList) adiData.get("succes");

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
            if (jobMap.get("name").toString().equals("Adobe")) {
                peviitorJobs = (int) jobMap.get("jobs");
            }
        }
        /* Making request to the career page to get the number of jobs */
        driver.get("https://careers.adobe.com/us/en/search-results?keywords=Romania");

        // wait for 3 seconds
        Thread.sleep(3000);

        // get the number of jobs from the career page
        String realJobsNumber = driver.findElement(By.className("result-count")).getText();

        // get the element that contains the number of jobs

        careerPageJobs = Integer.parseInt(realJobsNumber);

        System.out.println("Peviitor: " + peviitorJobs);
        System.out.println("Scraper: " + scraperJobs);
        System.out.println("Career page: " + careerPageJobs);

        // check if the number of jobs is the same
        if (scraperJobs == peviitorJobs && peviitorJobs == careerPageJobs) {
            // if the number of jobs is the same, then the test is passed
            System.out.println("First test passed");

            // get the jobs from the scraper
            for (Object job : jobs) {
                // convert JSON string to Map
                Map<String, Object> jobMap = (Map<String, Object>) job;

                try {
                    // get the job link
                    driver.get(jobMap.get("job_link").toString());

                    // wait to load the page
                    Thread.sleep(4000);

                    // get the job title
                    String jobTitle = driver.findElement(By.className("job-title")).getText();

                    System.out.println(jobTitle);

                    // check if the job title is the same
                    if (jobTitle.equals(jobMap.get("job_title").toString())) {
                        data = "{\"" + "is_success" + "\": " + "\"" + "Pass" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                                + "Automated test passed" + "\"}";
                        System.out.println("Pass");
                    } else { // if the job title is not the same
                        data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": "
                                + "\"" + "Job title is not the same" + "\"}";
                        System.out.println("Fail");
                        break;
                    }

                } catch (Exception e) { // if the job link is not valid
                    data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                            + e + "\"}";
                    System.out.println(e);
                    break;
                }
            }

            // close the webdriver
            driver.quit();

        } else { // if the number of jobs is not the same
            System.out.println("First Test failed!");
            data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                    + "Number of jobs is not the same" + "\"}";
        }
        // make request to the api to save the test result
        utils.makeRequest(apiEndpoint, "POST", data);
    }
}
