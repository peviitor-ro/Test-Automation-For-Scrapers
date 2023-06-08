package com.peviitor.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openqa.selenium.WebDriver;
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
        String data = "";

        String apiEndpoint = "http://localhost:8000/scraper/based_scraper_py/inetum.py/";
        for (Object job : jobs) {
            Map<String, Object> jobMap = (Map<String, Object>) job;
            

            try{
                driver.get(jobMap.get("job_link").toString()); 

                String jobTitle = driver.findElement(By.className("card-title")).getText();

                System.out.println(jobTitle);

                if (jobTitle.equals(jobMap.get("job_title").toString())) {
                    data = "{\"" + "is_success" + "\": " + "\"" + "Pass" + "\"}";
                    System.out.println("Pass");
                } else {
                    data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\"" + "Job title is not the same" + "\"}";
                    System.out.println("Fail");
                    break;
                } 
            } catch (Exception e) {
                data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\"" + "Job title is not the same" + "\"}";
                System.out.println("Fail");
                break;
            } 
            // wait for 5 seconds
            Thread.sleep(3000);
        }

        driver.quit();
        utils.makeRequest(apiEndpoint, "POST", data);
    }
}
