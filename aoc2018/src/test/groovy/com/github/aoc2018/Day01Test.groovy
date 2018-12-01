package com.github.aoc2018

class Day01Test extends AocSpec {
    Day01 sut = new Day01()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input        | output
            '+1, +1, +1' | 3
            '+1, +1, -2' | 0
            '-1, -2, -3' | -6
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input                | output
            '+1, -1'             | 0
            '+3, +3, +4, -2, -4' | 10
            '-6, +3, +8, +5, -6' | 5
            '+7, +7, -2, -7, -4' | 14
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
