package com.github.aoc2018

import spock.lang.Specification

class Day14Test extends Specification implements InputHelper {
    Day14 sut = new Day14()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input | output
            9     | '5158916779'
            5     | '0124515891'
            18    | '9251071085'
            2018  | '5941429882'
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(633601))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input | output
            51589 | 9
            01245 | 5
            92510 | 18
            59414 | 2018
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(633601))
    }
}
