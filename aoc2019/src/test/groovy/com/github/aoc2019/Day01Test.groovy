package com.github.aoc2019

class Day01Test extends AocSpec {
    Day01 sut = new Day01()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input    | output
            '12'     | 2
            '14'     | 2
            '1969'   | 654
            '100756' | 33583
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input    | output
            '14'     | 2
            '1969'   | 966
            '100756' | 50346
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
