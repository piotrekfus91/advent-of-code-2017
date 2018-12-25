package com.github.aoc2018

class Day19Test extends AocSpec {
    Day19 sut = new Day19()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
seti 5 0 1
seti 6 0 2
addi 0 1 0
addr 1 2 3
setr 1 0 0
seti 8 0 4
seti 9 0 5
'''
        expect:
            sut.first(input, 0) == 6
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput, 3))
    }
}
