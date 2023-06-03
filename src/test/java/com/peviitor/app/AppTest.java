package com.peviitor.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception
    {
        App.main(null);
        int peviitorJobs = App.peviitorJobs;
        int scraperJobs = App.scraperJobs;
        int careerPageJobs = App.careerPageJobs;

        assertEquals(scraperJobs, peviitorJobs, careerPageJobs);

    }
}
