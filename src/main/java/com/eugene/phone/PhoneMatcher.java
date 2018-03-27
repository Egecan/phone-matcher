package com.eugene.phone;

import com.eugene.phone.service.PhoneMatcherService;


public class PhoneMatcher {

    private static final String DICTIONARY_PROPERTY = "dictionary";
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/dictionary.txt";

    private PhoneMatcherService phoneMatcherService;

    private PhoneMatcher() {
        phoneMatcherService = new PhoneMatcherService();
    }

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
