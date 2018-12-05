package com.github.aoc2018

class Day05Test extends AocSpec {
    Day05 sut = new Day05()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input              | output
            'aA'               | 0
            'AbBa'             | 0
            'abAB'             | 4
            'aabAAB'           | 6
            'dabAcCaCBAcCcaDA' | 10
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = 'dabAcCaCBAcCcaDA'
        expect:
            sut.second(input, letter as Character) == output
        where:
            letter | output
            'a' | 6
            'b' | 8
            'c' | 4
            'd' | 6
    }

    def 'should pass entire second task'() {
        expect:
            sut.second('dabAcCaCBAcCcaDA') == 4
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
