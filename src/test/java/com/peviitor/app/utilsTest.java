package com.peviitor.app;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

public class utilsTest {

    public static String testResult = "true";
    // the data object for the request
    public static String data = "";

    // initialize the variables for the number of jobs from each source
    // public static int peviitorJobs = 0;
    public static int scraperJobs = 0;
    public static int careerPageJobs = 0;

    public static ObjectMapper objectMapper = new ObjectMapper();

    static ArrayList jobs;

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

        Boolean firstTestResult = false;
        if (callFunctions.length >= 1) {
            firstTestResult = checkNumberOfJobs(appData, companyName, careersUrl, jobElementSelector, callFunctions[0]);
        } else {
            firstTestResult = checkNumberOfJobs(appData, companyName, careersUrl, jobElementSelector, null);
        }

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
            // System.out.println("Peviitor: " + peviitorJobs);
            System.out.println("Scraper: " + scraperJobs);
            System.out.println("Career page: " + careerPageJobs);

            data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                    + "Number of jobs is not the same" + "\"}";
            testResult = "false";
        }
        // make request to the api to save the test result
        utils.makeRequest(apiEndpoint, "POST", data);
        return testResult;
    };

    public Boolean checkNumberOfJobs(
            String appData,
            String companyName,
            String careersUrl,
            String jobElementSelector,
            Function<String, String> callFunctions) throws Exception {

        // set the urls
        String peviitorUrl = "https://api.peviitor.ro/v1/companies/?count=true";

        // initialize the driver
        WebDriver driver = webdriver();
        WebDriverWait wait = wait(driver);

        // convert JSON string to Map
        Map<ArrayList, Object> scraperData = objectMapper.readValue(appData.toString(), Map.class);
        jobs = (ArrayList) scraperData.get("succes");

        // get the number of jobs from the scraper
        scraperJobs = jobs.size();

        /* Making request to the career page to get the number of jobs */
        driver.get(careersUrl);

        // wait for 2 seconds
        Thread.sleep(2000);

        String realJobsNumber;
        if (callFunctions != null) {
            Document doc = Jsoup.parse(driver.getPageSource());
            Element jobElement = doc.select(jobElementSelector).first();
            realJobsNumber = callFunctions.apply(jobElement.text());
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(jobElementSelector)));
            realJobsNumber = driver.findElement(By.cssSelector(jobElementSelector)).getText();
        }

        careerPageJobs = Integer.parseInt(realJobsNumber);

        // close the driver
        driver.close();

        return scraperJobs == careerPageJobs;
    }

    public Boolean checkLink(String jobTitleSelector, Function<String, String> callFunctions) throws Exception {
       // initialize the driver
            WebDriver driver = webdriver();
            WebDriverWait wait = wait(driver);
        // get the jobs from the scraper
        for (Object job : jobs) {
            

            // convert JSON string to Map
            Map<String, Object> jobMap = (Map<String, Object>) job;

            try {
                // get the job link
                driver.get(jobMap.get("job_link").toString());

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(jobTitleSelector)));
                // get the job title
                String jobTitle;
                String jobTitleScraper = jobMap.get("job_title").toString();

                if (callFunctions != null) {
                    Document doc = Jsoup.parse(driver.getPageSource());
                    Element jobElement = doc.select(jobTitleSelector).first();
                    jobTitle = callFunctions.apply(jobElement.text());
                    jobTitleScraper = callFunctions.apply(jobTitleScraper);
                } else {
                    jobTitle = driver.findElement(By.cssSelector(jobTitleSelector)).getText();
                }

                // remove spaces from the jobs title
                jobTitle = jobTitle.replaceAll(" ", "");
                jobTitleScraper = jobTitleScraper.replaceAll(" ", "");

                // check if the job title is the same
                if (jobTitle.equals(jobTitleScraper)) {
                    data = "{\"" + "is_success" + "\": " + "\"" + "Pass" + "\"" + "," + "\"" + "logs" + "\": " + "\""
                            + "Automated test passed" + "\"}";
                    testResult = "true";
                    System.out.println("Automated test passed");
                } else { // if the job title is not the same
                    data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": "
                            + "\"" + "Job title is not the same" + "\"}";
                    System.out.println("Automated test failed");
                    System.out.println(jobTitle);
                    System.out.println(jobTitleScraper);
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
        driver.close();
        return testResult == "true";
    }

    public static WebDriver webdriver() {
        // get path to the chromedriver
        Path currePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String currentPathString = currePath.toString();

        // set the path to the chromedriver
        System.setProperty("webdriver.chrome.driver",
                currentPathString + "scripts/chromedriver");

        // initialize the webdriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }

    public static WebDriverWait wait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait;
    }
}
