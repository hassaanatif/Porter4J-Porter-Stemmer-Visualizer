package com.example.porterstemmergui.utilities;

import com.example.porterstemmerbackend.PStemmer;
import com.example.porterstemmerbackend.StepsRecorder;
import com.example.porterstemmerbackend.UtilityClass;
import com.example.porterstemmergui.AbstractObjects.WordCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsKeeper {

     public static HashMap<String, ArrayList<StepsRecorder>> stepsByWords = new HashMap<>();
     public static HashMap<String, Integer> wordsAndCounts = new HashMap<>();

     public static void clearData () {
         wordsAndCounts.clear();
         stepsByWords.clear();
         UtilityClass.getStepRecorderArrayList().clear();
     }
    public static void CreateStepsByWords (String corpus) {
        //String preprocessed_corpus = UtilityClass.removePunctuation(corpus);

        wordsAndCounts = UtilityClass.extractWords(corpus);

        for (Map.Entry<String, Integer> map : wordsAndCounts.entrySet()) {
           // System.out.println("key is : "+ map.getKey());
            PStemmer.PorterStemmer(map.getKey());
            ArrayList<StepsRecorder> tmpStepRecorderArrayList = new ArrayList<>(UtilityClass.getStepRecorderArrayList());
            stepsByWords.put(map.getKey(), tmpStepRecorderArrayList);
            if (map.getKey().equals("consultantative"))
                System.out.println("size desired is : " + UtilityClass.getStepRecorderArrayList().size());
            UtilityClass.cleanStepRecorderArray();
        }

    }

    public static ArrayList<WordCell> CreateWordCellsFromWords () {
    ArrayList<WordCell> allWordCells = new ArrayList<>();

        for (Map.Entry<String, ArrayList<StepsRecorder>> words : stepsByWords.entrySet()) {
            WordCell wordCell = new WordCell("", words.getKey(),getStem(words.getKey(),
                    words.getValue()),
                    String.valueOf(words.getValue().size()));
            allWordCells.add(wordCell);
        }
        return  allWordCells;
    }

    private static String getStem (String word, ArrayList<StepsRecorder> stepsRecorderArrayList) {
    return stepsRecorderArrayList.size() == 0? word : stepsRecorderArrayList.get(stepsRecorderArrayList
            .size() - 1).getTransformation();
    }
}
