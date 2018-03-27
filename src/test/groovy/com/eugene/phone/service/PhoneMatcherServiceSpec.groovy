package com.eugene.phone.service

import spock.lang.Specification

class PhoneMatcherServiceSpec extends Specification {

    PhoneMatcherService phoneMatcherService = new PhoneMatcherService()

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream()
    final ByteArrayOutputStream errContent = new ByteArrayOutputStream()

    def dictionaryMap

    def setup(){

        System.setOut(new PrintStream(outContent))
        System.setErr(new PrintStream(errContent))

        dictionaryMap = new HashMap<String,String>()
        dictionaryMap.put("am", "26")
        dictionaryMap.put("an", "26")
        dictionaryMap.put("ram", "726")
        dictionaryMap.put("ran", "726")
        dictionaryMap.put("dom", "366")
        dictionaryMap.put("om", "66")
        dictionaryMap.put("don", "366")
        dictionaryMap.put("rando", "72636")
        dictionaryMap.put("random", "726366")
    }


    def "runFromConsole should print the matching word from dictionary for given number representation as input" () {

        given:

        def testString = "22738275\n"
        System.setIn(new ByteArrayInputStream(testString.getBytes()))

        def dictionaryPath = "src/test/resources/test-small-dictionary.txt"

        when:
        phoneMatcherService.runFromConsole(dictionaryPath)

        then:
        outContent.toString().contains("AARDVARK")
    }


    def "runFromConsole should not print any words if there are no matching number representations in dictionary" () {

        given:

        def testString = "22321223175\n"
        System.setIn(new ByteArrayInputStream(testString.getBytes()))

        def dictionaryPath = "src/test/resources/test-small-dictionary.txt"

        when:
        phoneMatcherService.runFromConsole(dictionaryPath)

        then:
        outContent.toString() == "***** Reading dictionary from: src/test/resources/test-small-dictionary.txt *****\n" +
                "Enter the numbers you would like to match from dictionary\n" +
                ""
        outContent.size() == 140
    }


    def "runBasedOnFile should only print the matching words from dictionary for given number representations as input" () {

        given:

        def dictionaryPath = "src/test/resources/test-small-dictionary.txt"
        def filePath = "src/test/resources/test-small-numbers.txt"

        when:
        phoneMatcherService.runBasedOnFile(dictionaryPath, filePath)

        then:
        outContent.toString() == "***** Reading dictionary from: src/test/resources/test-small-dictionary.txt *****\n" +
                "AARDVARK\n" +
                ""
    }

    def "runBasedOnFile should print error message if invalid file path is given as argument" () {

        given:
        def dictionaryPath = "src/test/resources/test-small-dictionary.txt"
        def filePath = "src/test/resources/non-existent-file.txt"

        when:
        phoneMatcherService.runBasedOnFile(dictionaryPath, filePath)

        then:
        errContent.toString().contains("Cannot read file. Please check your file path and contents.")
    }


    def "fetchDictionaryAsMap should read dictionary from file path given as argument" () {

        given:
        def dictionaryPath = "src/test/resources/test-small-dictionary.txt"

        when:
        def result = phoneMatcherService.fetchDictionaryAsMap(dictionaryPath)

        then:
        result instanceof Map<String,String>
        result.containsKey("AARDVARK")
        result.size() == 47
    }


    def "fetchDictionaryAsMap should print error message if invalid file path is given as argument" () {

        given:
        def dictionaryPath = "src/test/resources/non-existent-file.txt"

        when:
        phoneMatcherService.fetchDictionaryAsMap(dictionaryPath)

        then:
        errContent.toString().contains("Cannot read dictionary. Please check your dictionary path specified")
    }


    def "findWholeWords should return all words that match the number representation in dictionary map as a set"() {

        when:
        def result = phoneMatcherService.findWholeWords(dictionaryMap, "26")

        then:
        result instanceof Set<String>
        result.size() == 2
        result.contains("am")
        result.contains("an")
    }


    def "findWholeWords should return an empty set if there are no numbers matching the expected one in dictionary map"() {

        when:
        def result = phoneMatcherService.findWholeWords(dictionaryMap, "555")

        then:
        result instanceof Set<String>
        result.size() == 0
    }


    def "findMultiWords should return all two-word combinations that can be built by separately matching the number representations in dictionary map"() {

        when:
        def result = phoneMatcherService.findMultiWords(dictionaryMap, "726366")

        then:
        result instanceof Set<String>
        result.size() == 4
        result.contains("ram-dom")
        result.contains("ran-dom")
        result.contains("ram-don")
        result.contains("ran-don")
    }


    def "findMultiWords should return an empty set if there are no two-word combinations that can be built from the dictionary map"() {

        when:
        def result = phoneMatcherService.findMultiWords(dictionaryMap, "723216366")

        then:
        result instanceof Set<String>
        result.size() == 0
    }

    def "findWordsWithDigits should return all digit-word, word-digit-word and word-digit combinations that can be built by based on the dictionary map"() {

        when:
        def result = phoneMatcherService.findWordsWithDigits(dictionaryMap, "726366")

        then:
        result instanceof Set<String>
        result.size() == 3
        result.contains("ram-3-om")
        result.contains("rando-6")
        result.contains("ran-3-om")

    }

    def "findWordsWithDigits should return an empty set if there are no words with digits combinations that can be built from the dictionary map"() {

        when:
        def result = phoneMatcherService.findWordsWithDigits(dictionaryMap, "723216366")

        then:
        result instanceof Set<String>
        result.size() == 0
    }

}
