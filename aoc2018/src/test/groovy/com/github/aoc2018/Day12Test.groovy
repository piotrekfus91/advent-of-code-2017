package com.github.aoc2018

class Day12Test extends AocSpec {
    Day12 sut = new Day12()
    String initilState = '#......##...#.#.###.#.##..##.#.....##....#.#.##.##.#..#.##........####.###.###.##..#....#...###.##'

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #
            '''
        expect:
            sut.first(input, '#..#.#..##......###...###', 20) == 325
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput, initilState, 20))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput, initilState, 50000000000L))
    }
}
