package com.example;
import java.util.*;

import com.example.porterstemmerbackend.PStemmer;
import com.example.porterstemmerbackend.UtilityClass;

import java.util.Scanner;

public class Main  {

    public static void main (String args []) {
        //String word = "Consultantative";
        //System.out.println(PStemmer.PorterStemmer(word));
        input();
    }

    private static void input () {
        Boolean runLoop = true; 
        Scanner scanner = new Scanner(System.in);
        String input = "";
        
        do {
            System.out.print("Please enter a word : ");
            input = scanner.nextLine();
            PStemmer.PorterStemmer(input);
            System.out.println("%%%%%%%%%");
           UtilityClass.printAllSteps();
        //    System.out.println(UtilityClass.getStepRecorderArrayList().size());
            System.out.print("\nWould you like to continue? Answer 'yes' or 'no'");
            input = scanner.nextLine();
            if (!(input.equals("yes"))) {
                runLoop = false;
            }
        } while (runLoop);
    }
}