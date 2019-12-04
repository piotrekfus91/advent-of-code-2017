package com.github.aoc2019

class Day04Test extends AocSpec {
    Day04 sut = new Day04()

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
