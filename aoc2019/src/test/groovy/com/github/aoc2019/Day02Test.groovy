package com.github.aoc2019

class Day02Test extends AocSpec {
    Day02 sut = new Day02()

    def 'should pass sample tests for first task'() {
        expect:
            sut.runProgram(input.split(',').collect { it.toInteger() }).join(',') == output
        where:
            input | output
            '1,0,0,0,99' | '2,0,0,0,99'
            '2,3,0,3,99' | '2,3,0,6,99'
            '2,4,4,5,99,0' | '2,4,4,5,99,9801'
            '1,1,1,4,99,5,6,0,99' | '30,1,1,4,2,5,6,0,99'
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
