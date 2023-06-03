package com.peviitor.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class utils {
    // This method makes a request to the given url , method and data and returns
    // the response
    public static String makeRequest(String url, String method, String... data) throws IOException {

        // create the url
        URL apiUrl = new URL(url);
        // open the connection
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        // set the request method
        connection.setRequestMethod(method);

        // set the request headers
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // set the request body
        if (data.length > 0) {
            connection.getOutputStream().write(data[0].getBytes());
        }

        // get the response code
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // close the connection
        reader.close();
        connection.disconnect();

        return response.toString();
    }
}
