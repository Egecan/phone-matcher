package com.eugene.phone;

import com.eugene.phone.service.PhoneMatcherService;


public class PhoneMatcher {

    private static final String DICTIONARY_PROPERTY = "dictionary";
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/dictionary.txt";

    private PhoneMatcherService phoneMatcherService;

    private PhoneMatcher() {
        phoneMatcherService = new PhoneMatcherService();
    }

    /**
     * Phone Matcher is a program that brings possible word matches from a dictionary for a list of provided phone numbers
     * The program can read the numbers via console, or from files if given as command-line arguments. There is a default
     * dictionary, but the user can provide his/her own dictionary via system property if required.
     * @param args
     */
    public static void main(String[] args) {

        PhoneMatcher phoneMatcher = new PhoneMatcher();

        PhoneMatcherService phoneMatcherService = phoneMatcher.phoneMatcherService;

        String dictionaryPath = System.getProperty(DICTIONARY_PROPERTY);

        if(dictionaryPath == null) {
            dictionaryPath = DEFAULT_DICTIONARY_PATH;
        }

        if (args.length > 0) {
            phoneMatcherService.runBasedOnFile(dictionaryPath, args);
        } else {
            phoneMatcherService.runFromConsole(dictionaryPath);
        }
    }

}
