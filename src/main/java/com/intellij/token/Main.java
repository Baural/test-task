package com.intellij.token;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Baurzhan Alzhanov
 * @date on 01.01.2021
 */


public class Main {

    // directories
    private static final String directory = "src/main/resources"; // where all the files are


    public static void main(String[] args) throws Exception {

        Search searcher = new Search();

        //specifying the indexed .txt files and directories
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                System.out.println("indexing "+file.getName());
                searcher.loadFile(file);
            }
        }

        // Search
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("looking for: ");
            String line = in.nextLine();
            String[] wordList = line.split(" ");
            if (wordList[0].equals("#")) {
                // Report word counts
                if (wordList.length==1)
                    System.err.println("Please enter the number or words");
                else
                    System.err.println("Sorry, I haven't read '"+wordList[2]+"'");
            }
            else if (wordList.length == 1) {
                // Report files containing word
                Set<String> files = searcher.search(wordList[0]);
                if (files.isEmpty()) System.out.println("Sorry, couldn't find anything");
                else {
                    System.out.println("Appears in "+files);
                    for (String file : files) {
                        System.out.println(file + ":" + searcher.file2WordCounts.get(file).get(wordList[0]));
                    }
                }
            }
            else {
                // Report files containing all words
                Set<String> files = searcher.search(wordList);
                if (files.isEmpty()) System.out.println("Sorry, couldn't find anything");
                else {
                    System.out.println("Appears in "+files);
                    for (String file : files) {
                        for (String wordItem : wordList)
                            System.out.println("  "+wordItem+" "+searcher.file2WordCounts.get(file).get(wordItem)+"; in "+ file);
                    }
                }
            }
        }
    }
}
