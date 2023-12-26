package com.example.IOUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class IOClass {

    public static String importText (File file) {
        int index = file.toString().lastIndexOf('.');
        if (index > 0) {
            String extension = file.toString().substring(index + 1);
            if (!(extension.equals("txt"))) {
                System.out.println("Extension is not right");
                return null;
            }
        }
        String content = "";
        try {
             content = new String(Files.readAllBytes(Paths.get(file.getPath())));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
        /******CHECKING TO SEE IF IT IS A TEXT FILE***/


}
