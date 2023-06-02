package com.peviitor.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.peviitor.app.App;

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
        int response = App.peviitorJobs;

        System.out.println(response);
        assertEquals(699, response);

    }
}
