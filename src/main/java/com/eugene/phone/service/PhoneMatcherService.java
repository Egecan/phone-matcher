package com.eugene.phone.service;

import com.eugene.phone.util.MapUtil;
import com.eugene.phone.util.NumberIndex;
import com.eugene.phone.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PhoneMatcherService {

    private static final String DELIMITER = "-";
    private static final String QUIT_COMMAND = "q";

    public void runFromConsole(String dictionaryPath){

        Map<String, String> dictionaryCodedMap = fetchDictionaryAsMap(dictionaryPath);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            System.out.println("Enter the numbers you would like to match from dictionary");
            while ((line = br.readLine()) != null) {
                List<String> outputList = new ArrayList<>();
                if (QUIT_COMMAND.equals(line)) {
                    System.out.println("Exit");
                    System.exit(0);
                }
                String strippedLine = StringUtil.stripPunctuation(line);
                if (strippedLine.isEmpty()) {
                    continue;
                }
                outputList.addAll(findWholeWords(dictionaryCodedMap, strippedLine));
                outputList.addAll(findMultiWords(dictionaryCodedMap, strippedLine));
                if (outputList.isEmpty()) {
                    outputList.addAll(findWordsWithDigits(dictionaryCodedMap, strippedLine));
                }
                outputList.forEach(System.out::println);
            }
        } catch (IOException ex) {
            System.err.println("Unexpected input: " + ex);
            System.exit(1);
        }
    }


    public void runBasedOnFile(String dictionaryPath, String[] filePaths){

        Map<String, String> dictionaryCodedMap = fetchDictionaryAsMap(dictionaryPath);

        for (String filePath : filePaths) {
            List<String> numbersList = new ArrayList<>();
            List<String> outputList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                numbersList = br.lines().collect(Collectors.toList());
            } catch (IOException ex) {
                System.err.println("Cannot read file. Please check your file path and contents. Details: " + ex);
            }

            for (String number : numbersList) {

                String strippedLine = StringUtil.stripPunctuation(number);
                if (strippedLine.isEmpty()) {
                    continue;
                }
                outputList.addAll(findWholeWords(dictionaryCodedMap, strippedLine));
                outputList.addAll(findMultiWords(dictionaryCodedMap, strippedLine));
                if (outputList.isEmpty()) {
                    outputList.addAll(findWordsWithDigits(dictionaryCodedMap, strippedLine));
                }
            }
            outputList.forEach(System.out::println);
        }
    }


    // <word, numberRepresentation>
    private Map<String, String> fetchDictionaryAsMap(String path){

        System.out.println("***** Reading dictionary from: " + path + " *****");

        Map<String, String> dictionaryCodedMap = new HashMap<>();
        List<String> dictionaryWords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine())!= null) {
                String strippedLine = StringUtil.stripPunctuation(line);
                if (strippedLine.isEmpty()) {
                    continue;
                }
                dictionaryWords.add(StringUtil.toUpperCase(strippedLine));
            }
        } catch (IOException ex) {
            System.err.println("Cannot read dictionary. Please check your dictionary path specified: " + ex);
        }

        for(String word : dictionaryWords) {
            StringBuilder number = new StringBuilder();
            for (char c : word.toCharArray()) {
                number.append(NumberIndex.getNumberCode(c));
            }
            dictionaryCodedMap.put(word, number.toString());
        }

        return dictionaryCodedMap;
    }


    private Set<String> findWholeWords(Map<String, String> dictionaryCodedMap, String strippedLine) {

        Set<String> words = new HashSet<>();
        if(dictionaryCodedMap.containsValue(strippedLine)) {
            words = MapUtil.getKeysByValue(dictionaryCodedMap, strippedLine);
        }
        return words;
    }


    private Set<String> findMultiWords(Map<String, String> dictionaryCodedMap, String strippedLine) {

        Set<String> words = new HashSet<>();
        for (int index = 1; index < strippedLine.length(); index++) {
            String firstWord = strippedLine.substring(0, index);
            Set<String> firstWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, firstWord);
            if (firstWordMatches.isEmpty()) {
                continue;
            }
            for (String firstMatch : firstWordMatches) {
                String secondWord = strippedLine.substring(index);
                Set<String> secondWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, secondWord);
                for (String secondMatch : secondWordMatches) {
                    words.add(firstMatch + DELIMITER + secondMatch);
                }
            }
        }
        return words;
    }


    private Set<String> findWordsWithDigits(Map<String, String> dictionaryCodedMap, String strippedLine) {

        Set<String> words = new HashSet<>();
        char firstDigit = strippedLine.charAt(0);
        String remainingNumber = strippedLine.substring(1);
        Set<String> remainingWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, remainingNumber);
        for (String remainingWord : remainingWordMatches) {
            words.add(firstDigit + DELIMITER + remainingWord);
        }

        for (int index = 1; index < strippedLine.length(); index++) {
            char currentDigit = strippedLine.charAt(index);

            String firstPart = strippedLine.substring(0, index);
            Set<String> firstWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, firstPart);
            if (firstWordMatches.isEmpty()) {
                continue;
            }
            for (String firstMatch : firstWordMatches) {
                String secondPart = strippedLine.substring(index + 1);
                Set<String> secondWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, secondPart);
                for (String secondMatch : secondWordMatches) {
                    words.add(firstMatch + DELIMITER + currentDigit + DELIMITER + secondMatch);
                }
            }
        }

        char lastDigit = strippedLine.charAt(strippedLine.length() - 1);
        String firstPart = strippedLine.substring(0, strippedLine.length() - 1);
        Set<String> firstWordMatches = MapUtil.getKeysByValue(dictionaryCodedMap, firstPart);
        for (String firstMatch : firstWordMatches) {
            words.add(firstMatch + DELIMITER + lastDigit);
        }
        return words;
    }

}
