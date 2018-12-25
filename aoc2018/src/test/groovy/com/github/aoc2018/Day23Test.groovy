package com.github.aoc2018

class Day23Test extends AocSpec {
    Day23 sut = new Day23()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
pos=<0,0,0>, r=4
pos=<1,0,0>, r=1
pos=<4,0,0>, r=3
pos=<0,2,0>, r=1
pos=<0,5,0>, r=3
pos=<0,0,3>, r=1
pos=<1,1,1>, r=1
pos=<1,1,2>, r=1
pos=<1,3,1>, r=1
            '''
        expect:
            sut.first(input) == 7
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '''
pos=<10,12,12>, r=2
pos=<12,14,12>, r=2
pos=<16,12,12>, r=4
pos=<14,14,14>, r=6
pos=<50,50,50>, r=200
pos=<10,10,10>, r=5
            '''
        expect:
            sut.second(input) == 36
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
