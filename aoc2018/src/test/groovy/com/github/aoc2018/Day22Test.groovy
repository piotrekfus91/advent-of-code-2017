package com.github.aoc2018

class Day22Test extends AocSpec {
    Day22 sut = new Day22()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(510, 10, 10) == 114
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(3879, 8, 713))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(510, 10, 10) == 45
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(3879, 8, 713))
    }
}
