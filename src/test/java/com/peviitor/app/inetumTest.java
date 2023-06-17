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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class inetumTest {

    public static void main( String[] args ) throws Exception {
        // Initialize the inetum class
        inetum.main(null);

        // set the urls
        String peviitorUrl = "https://api.peviitor.ro/v3/search/?q=inetum&country=Rom%C3%A2nia&page=1";
        String apiEndpoint = "https://dev.laurentiumarian.ro/scraper/based_scraper_py/inetum.py/";

        // initialize the variables for the number of jobs from each source
        int peviitorJobs = 0;
        int scraperJobs = 0;
        int careerPageJobs = 0;
        
        ObjectMapper objectMapper = new ObjectMapper();

        // the data object for the request
        String data = "";

        // convert JSON string to Map
        Map<ArrayList, Object> inetumData = objectMapper.readValue(inetum.data.toString(), Map.class);
        ArrayList jobs = (ArrayList) inetumData.get("succes");

        // get the number of jobs from the scraper
        scraperJobs = jobs.size();

        // wait for the peviitor api to update
        TimeUnit.SECONDS.sleep(3);

        /*  Making request to the Peviitor Api to get the number of jobs */
        // get the number of jobs from the scraper
        String peviitorData = utils.makeRequest(peviitorUrl, "GET");

        // parse the response to get the number of jobs
        Object responseObj = objectMapper.readValue(peviitorData, Object.class);

        // convert JSON string to Map
        Map<String, Object> map = (Map<String, Object>) responseObj;
        Map<String, Object> peviitorJobsApi = (Map<String, Object>) map.get("response");

        peviitorJobs = (int) peviitorJobsApi.get("numFound");

        /*  Making request to the career page to get the number of jobs */
        Document doc = Jsoup.connect("https://www.inetum.com/en/jobs?facets_query=&search=&f%5B0%5D=region%3A1068").get();
        
        // get the number of jobs from the career page
        Element element = doc.select("#1068").first();

        careerPageJobs = Integer.parseInt(element.text().replace("(", "").replace(")", "").split(" ")[2]);

        System.out.println("Peviitor: " + peviitorJobs);
        System.out.println("Scraper: " + scraperJobs);
        System.out.println("Career page: " + careerPageJobs);
        
        // check if the number of jobs is the same
        if (scraperJobs == peviitorJobs && peviitorJobs == careerPageJobs) {
            // if the number of jobs is the same, then the test is passed
            System.out.println("First test passed");
        
            // get path to the chromedriver
            Path currePath = FileSystems.getDefault().getPath("").toAbsolutePath();
            String currentPathString = currePath.toString();

            // set the path to the chromedriver
            System.setProperty("webdriver.chrome.driver", currentPathString + "/src/test/java/com/peviitor/app/chromedriver");

            // initialize the webdriver
            WebDriver driver = new ChromeDriver();

            // get the jobs from the scraper
            for (Object job : jobs) {
                // convert JSON string to Map
                Map<String, Object> jobMap = (Map<String, Object>) job;
                
                try{
                    // get the job link
                    driver.get(jobMap.get("job_link").toString()); 

                    // get the job title
                    String jobTitle = driver.findElement(By.className("card-title")).getText();

                    System.out.println(jobTitle);

                    // check if the job title is the same
                    if (jobTitle.equals(jobMap.get("job_title").toString())) {
                        data = "{\"" + "is_success" + "\": " + "\"" + "Pass" + "\"}";
                        System.out.println("Pass");
                    } else { // if the job title is not the same
                        data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\"" + "Job title is not the same" + "\"}";
                        System.out.println("Fail");
                        break;
                    } 

                } catch (Exception e) { // if the job link is not valid
                    data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\"" + e + "\"}";
                    System.out.println("Fail");
                    break;
                } 

                // wait for 3 seconds
                Thread.sleep(3000);
            }

            // close the webdriver
            driver.quit();
            
        } else { // if the number of jobs is not the same
            System.out.println("First Test failed!");
            data = "{\"" + "is_success" + "\": " + "\"" + "Fail" + "\"" + "," + "\"" + "logs" + "\": " + "\"" + "Number of jobs is not the same" + "\"}";
        }
        // make request to the api to save the test result
        utils.makeRequest(apiEndpoint, "POST", data);
    }
}
