package com.github.aoc2018

class Day21Test extends AocSpec {
    Day21 sut = new Day21()

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput, 1))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput, 1))
    }
}
