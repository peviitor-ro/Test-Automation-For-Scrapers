package com.peviitor.app;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws Exception {
        
        Path currePath = FileSystems.getDefault().getPath("").toAbsolutePath();
        String currentPathString = currePath.toString();
        Process p = Runtime.getRuntime().exec("ls " + currentPathString + "/src/test/java/com/peviitor/app/");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        ArrayList<String> files = new ArrayList<String>();

        // exclude files
        List<String> exclude = new ArrayList<String>() {{
            add("main.java");
            add("chromedriver");
            add("utilsTest.java");
        }};

        // get all test files
        for (String line = stdInput.readLine(); line != null; line = stdInput.readLine()) {
            if (line.contains(".java") && exclude.contains(line) == false)
            files.add(line);
        }

        // run all the tests
        for (String file : files) {
            String className = file.split("\\.")[0];
            Class<?> clazz = Class.forName("com.peviitor.app." + className);
            clazz.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
        }
    }
}

