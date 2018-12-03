package com.github.aoc2018

class Day03Test extends AocSpec {
    Day03 sut = new Day03()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2
            '''
        expect:
            sut.first(input) == 4
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '''
#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2
            '''
        expect:
            sut.second(input) == '3'
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
