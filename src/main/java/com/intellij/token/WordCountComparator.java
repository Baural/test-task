package com.intellij.token;

/**
 * @author Baurzhan Alzhanov
 * @date on 01.01.2021
 */


import java.util.Comparator;
import java.util.Map;


public class WordCountComparator implements Comparator<Map.Entry<String,Integer>> {
    public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2) {
        if (e1.getValue() < e2.getValue()) return -1;
        else if (e1.getValue() > e2.getValue()) return 1;
        else return e1.getKey().compareTo(e2.getKey());
    }
}