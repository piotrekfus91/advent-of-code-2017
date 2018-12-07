package com.github.aoc2018

class Day06Test extends AocSpec {
    Day06 sut = new Day06()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
            '''
        expect:
            sut.first(input) == 17
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '''
1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
            '''
        expect:
            sut.second(input, 32) == 16
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput, 10000))
    }
}
