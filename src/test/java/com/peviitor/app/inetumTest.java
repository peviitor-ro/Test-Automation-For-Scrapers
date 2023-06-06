package com.peviitor.app;

import java.lang.reflect.Array;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;

public class inetumTest {

    public static void main( String[] args ) throws Exception {
        // change the path to the chromedriver when running on your machine
        System.setProperty("webdriver.chrome.driver", "/Users/laurentiumarianbaluta/Desktop/github/Test-Automation-For-Scrapers/src/test/java/com/peviitor/app/chromedriver");
        
        inetum.main(null);
        ObjectMapper objectMapper = new ObjectMapper();

        WebDriver driver = new ChromeDriver();

        Map<ArrayList, Object> inetumData = objectMapper.readValue(inetum.data.toString(), Map.class);
        ArrayList jobs = (ArrayList) inetumData.get("succes");
        for (Object job : jobs) {
            Map<String, Object> jobMap = (Map<String, Object>) job;
            System.out.println(jobMap.get("job_title"));

            driver.get(jobMap.get("job_link").toString());
            break;
        }
    }
}
