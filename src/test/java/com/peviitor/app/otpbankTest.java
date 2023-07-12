package com.peviitor.app;

import org.junit.Test;
import org.testng.asserts.Assertion;

import com.google.common.base.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// impor javascriptExecutor
import org.openqa.selenium.JavascriptExecutor;

import java.util.Map;

class otpbankUtils extends utilsTest{
    @Override
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

                // get the job title
                String jobTitleScraper = jobMap.get("job_title").toString();

                JavascriptExecutor js = (JavascriptExecutor) driver;
                
                
                String jobTitle = (String) js.executeScript("return document.getElementsByTagName('h1')[0].innerHTML;");
                

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
}

public class otpbankTest {
    public static String results = "";

    public void compare() {
        String data = "";

        try {
            otpbank.main(null);
            data = otpbank.data.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        String companyName = "OTPBank";
        String scraperApiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/" + companyName + ".py/";
        String careersUrl = "https://cariere.otpbank.ro/Posturi";
        String jobElementSelector = "div[class='pull-left page-subtitle-counter']";
        String jobTitleSelector = "h1";

        try {
            results = new otpbankUtils().initiateTest(
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
