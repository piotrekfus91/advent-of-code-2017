package com.github.aoc2019

import spock.lang.Shared

class Day06Test extends AocSpec {
    Day06 sut = new Day06()
    @Shared String test1 = '''\
COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L'''

    @Shared String test2 = '''\
COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)M
M)SAN'''

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input    | output
            test1 | 42
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input    | output
            test2 | 5
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
