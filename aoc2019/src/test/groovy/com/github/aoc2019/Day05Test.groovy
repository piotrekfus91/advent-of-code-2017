package com.github.aoc2019

class Day05Test extends AocSpec {
    Day05 sut = new Day05()

    def 'should print result for first task'() {
        given:
            sut.first(taskInput)
    }

    def 'should print result for second task'() {
        given:
            sut.second(taskInput)
    }
}
