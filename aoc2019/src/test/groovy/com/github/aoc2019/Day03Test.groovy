package com.github.aoc2019

import spock.lang.Shared

class Day03Test extends AocSpec {
    Day03 sut = new Day03()
    @Shared
    String test1 = '''
R8,U5,L5,D3
U7,R6,D4,L4
'''

    @Shared
    String test2 = '''
R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83
'''

    @Shared
    String test3 = '''
R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
'''

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input | output
            test1 | 6
            test2 | 159
            test3 | 135
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input | output
            test1 | 30
            test2 | 610
            test3 | 410
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
