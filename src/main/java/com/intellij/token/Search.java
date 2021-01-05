package com.intellij.token;

import java.io.*;
import java.util.*;

/**
 * @author Baurzhan Alzhanov
 * @date on 01.01.2021
 */


public class Search {
    Map<String, Map<String,Integer>> file2WordCounts;	// filename -> (map word -> count)
    Map<String,Integer> numWords;						// filename -> # words
    Map<String,Integer> numFiles;						// word -> # files containing it

    Search() {
        file2WordCounts = new TreeMap<String, Map<String,Integer>>();
        numWords = new TreeMap<String,Integer>();
        numFiles = new TreeMap<String,Integer>();
    }

    void loadFile(File file) throws Exception {
        Map<String,Integer> wordCounts = new TreeMap<String,Integer>();
        //tokenization algorithm (simple splitting by words)
        // Loop over lines
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        int n=0; // # words
        while ((line = in.readLine()) != null) {
            // Loop over all the words split out of the string, adding to map or incrementing count
            String[] words = line.toLowerCase().split("\\W+"); // this says to split by one or more non-word characters
            for (String word: words) {
                if (word.isEmpty()) continue;
                if (wordCounts.containsKey(word)) {
                    // Increment the count
                    wordCounts.put(word, wordCounts.get(word)+1);
                }
                else {
                    // Add the new word
                    wordCounts.put(word, 1);
                }
                n++;
            }
        }
        in.close();

        file2WordCounts.put(file.getName(), wordCounts);
        numWords.put(file.getName(), n);
    }

    //Returns the names of the files in which the wordItem appears
    public Set<String> search(String wordItem) {
        Set<String> good = new TreeSet<String>();
        for (String file : file2WordCounts.keySet()) {
            if (file2WordCounts.get(file).containsKey(wordItem)) good.add(file);
        }
        return good;
    }

     //Returns the names of the files in which all the wordList appear
    public Set<String> search(String[] wordList) {
        Set<String> good = new TreeSet<String>(file2WordCounts.keySet());

        // Intersection what was good for the previous wordList with what is good for this wordItem
        for (String wordItem : wordList)
            good.retainAll(search(wordItem));

        return good;
    }
}
