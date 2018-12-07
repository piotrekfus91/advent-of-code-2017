package com.github.aoc2018

class Day07Test extends AocSpec {
    Day07 sut = new Day07()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
            '''
        expect:
            sut.first(input) == 'CABDFE'
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '''
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
            '''
        expect:
            sut.second(input, 0, 2) == 15
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput, 60, 5))
    }
}
