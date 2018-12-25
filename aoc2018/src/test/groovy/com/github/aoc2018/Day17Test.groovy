package com.github.aoc2018

class Day17Test extends AocSpec {
    Day17 sut = new Day17()

    def 'should print result for sample data'() {
        given:
            String input = '''
x=495, y=2..7
y=7, x=495..501
x=501, y=3..7
x=498, y=2..4
x=506, y=1..2
x=498, y=10..13
x=504, y=10..13
y=13, x=498..504
'''
        expect:
            sut.first(input) == 57
    }

    def 'should print result for other data'() {
        given:
            String input = '''
x=495, y=4..10
x=505, y=4..10
x=499, y=6..8
x=501, y=6..8
y=10, x=495..505
y=8, x=499..501
'''
        expect:
            sut.first(input) == 76
    }

    def 'should print result for pouring into clay'() {
        given:
            String input = 'x=500, y=3..5'
        expect:
            sut.first(input) == 10
    }

    def 'should print result for pouring into nested clay'() {
        given:
            String input = '''
x=500, y=2..5
x=503, y=2..5
y=5, x=500..503
x=495, y=4..8
x=507, y=3..8
y=8, x=495..507
'''
        expect:
            sut.first(input) == 74
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
