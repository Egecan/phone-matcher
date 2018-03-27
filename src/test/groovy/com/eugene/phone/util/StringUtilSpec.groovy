package com.eugene.phone.util

import spock.lang.Specification

class StringUtilSpec extends Specification {


    def "toUpperCase should convert all letters into uppercase ones"() {

        when:
        def result = StringUtil.toUpperCase("aardvark")

        then:
        result == "AARDVARK"
    }

    def "toUpperCase should not change numbers or punctuation while converting all letters into uppercase ones"() {

        when:
        def result = StringUtil.toUpperCase("123aar_)dvark")

        then:
        result == "123AAR_)DVARK"
    }

    def "stripPunctuation should remove all punctuation and whitespaces from a given word"() {

        when:
        def result = StringUtil.stripPunctuation("12.,3aa*/-%r_)dva  rk  ")

        then:
        result == "123aardvark"
    }

}
