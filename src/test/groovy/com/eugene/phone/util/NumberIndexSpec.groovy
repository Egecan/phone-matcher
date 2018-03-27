package com.eugene.phone.util

import spock.lang.Specification

class NumberIndexSpec extends Specification {


    def "getNumberCode should bring the number code for given char value"() {

        when:
        def result = NumberIndex.getNumberCode('D' as char)

        then:
        result == '3' as char
    }

    def "getNumberCode should return the char value back if it cannot find a matching number code"() {

        when:
        def result = NumberIndex.getNumberCode('1' as char)

        then:
        result == '1' as char
    }
}
