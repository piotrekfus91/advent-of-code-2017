package com.github.aoc2018

class Day11Test extends AocSpec {
    Day11 sut = new Day11()

    def 'should calc power level'() {
        expect:
            sut.calcPowerLevel(x, y, serial) == result
        where:
            x   | y   | serial | result
            3   | 5   | 8      | 4
            122 | 79  | 57     | -5
            217 | 196 | 39     | 0
            101 | 153 | 71     | 4
    }

    def 'should print result for sample data'() {
        expect:
            sut.first(input) == output
        where:
            input | output
            18    | '33,45'
            42    | '21,61'
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(8772))
    }

    def 'should print result for second task sample data'() {
        expect:
            sut.second(input) == output
        where:
            input | output
            18    | '90,269,16'
            42    | '232,251,12'
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(8772))
    }
}
