package com.github.aoc2018

import spock.lang.Specification

class Day15Test extends Specification implements InputHelper {
    Day15 sut = new Day15()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input << [
                '''
#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
#######
''',
                '''
#######
#G..#E#
#E#E.E#
#G.##.#
#...#E#
#...E.#
#######
''',
                '''
#######   
#E..EG#
#.#G.E#
#E.##E#
#G..#.#
#..E#.#   
#######
''',
                '''
#######   
#E.G#.#
#.#G..#
#G.#.G#   
#G..#.#
#...E.#
#######
''',
                '''
#######   
#.E...#   
#.#..G#
#.###.#   
#E#G#G#   
#...#G#
#######
''',
                '''
#########   
#G......#
#.E.#...#
#..##..G#
#...##..#   
#...#...#
#.G...G.#   
#.....G.#   
#########
'''
            ]
            output << [27730, 36334, 39514, 27755, 28944, 18740]
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        expect:
            sut.second(input) == output
        where:
            input << [
'''
#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
#######
''',
'''
#######
#E..EG#
#.#G.E#
#E.##E#
#G..#.#
#..E#.#
#######
''',
'''
#######
#E.G#.#
#.#G..#
#G.#.G#
#G..#.#
#...E.#
#######
''',
'''
#######
#.E...#
#.#..G#
#.###.#
#E#G#G#
#...#G#
#######
''',
'''
#########   
#G......#   
#.E.#...#
#..##..G#   
#...##..#   
#...#...#   
#.G...G.#   
#.....G.#   
#########
'''
            ]
            output << [4988, 31284, 3478, 6474, 1140]
    }


    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
