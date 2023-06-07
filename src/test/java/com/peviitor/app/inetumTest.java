package com.peviitor.app;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

import java.nio.file.FileSystems;

public class inetumTest {

    public static void main( String[] args ) throws Exception {
        
        // current Path
        Path currePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String currentPathString = currePath.toString();

        System.setProperty("webdriver.chrome.driver", currentPathString + "/src/test/java/com/peviitor/app/chromedriver");

        inetum.main(null);
        ObjectMapper objectMapper = new ObjectMapper();

        WebDriver driver = new ChromeDriver();

        Map<ArrayList, Object> inetumData = objectMapper.readValue(inetum.data.toString(), Map.class);
        ArrayList jobs = (ArrayList) inetumData.get("succes");
        for (Object job : jobs) {
            Map<String, Object> jobMap = (Map<String, Object>) job;

            try{
                driver.get(jobMap.get("job_link").toString()); 
                // cookie button

                WebElement cookieButton = driver.findElement(By.className("agree-button"));
                cookieButton.click();

                String jobTitle = driver.findElement(By.className("card-title")).getText();

                System.out.println(jobTitle);

                if (jobTitle.equals(jobMap.get("job_title").toString())) {
                    System.out.println("Job title is the same");
                } else {
                    System.out.println("Job title is not the same");
                }
                
            } finally {
                driver.quit();
            }
            break;
        }
    }
}
