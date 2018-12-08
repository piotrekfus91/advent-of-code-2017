package com.github.aoc2018

class Day08Test extends AocSpec {
    Day08 sut = new Day08()

    def 'should pass sample tests for first task'() {
        given:
            String input = '2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2'
        expect:
            sut.first(input) == 138
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2'
        expect:
            sut.second(input) == 66
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
