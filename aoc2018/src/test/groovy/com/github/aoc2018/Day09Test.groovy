package com.github.aoc2018

class Day09Test extends AocSpec {
    Day09 sut = new Day09()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(players, lastMarble) == output
        where:
            players | lastMarble | output
            9       | 25         | 32
            10      | 1618       | 8317
            13      | 7999       | 146373
            17      | 1104       | 2764
            21      | 6111       | 54718
            30      | 5807       | 37305
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(441, 71032))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(441, 71032))
    }
}
