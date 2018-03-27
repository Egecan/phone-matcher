package com.eugene.phone.util

import spock.lang.Specification

class MapUtilSpec extends Specification {

    Map<String,String> wordsMap

    def setup() {
        wordsMap = new HashMap<String,String>()
        wordsMap.put("am", "26")
        wordsMap.put("an", "26")
        wordsMap.put("random", "726366")
    }

    def "getKeysByValue should return a set of all keys that has the expected value"() {

        when:
        def result = MapUtil.getKeysByValue(wordsMap, "26")

        then:
        result instanceof Set<String>
        result.size() == 2
        result.contains("am")
        result.contains("an")
    }

    def "getKeysByValue should return an empty set when none of the keys has the expected value"() {

        when:
        def result = MapUtil.getKeysByValue(wordsMap, "5555")

        then:
        result instanceof Set<String>
        result.size() == 0
    }

}
