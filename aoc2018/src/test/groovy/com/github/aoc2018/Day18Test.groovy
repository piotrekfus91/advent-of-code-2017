package com.github.aoc2018

class Day18Test extends AocSpec {
    Day18 sut = new Day18()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
            '''
        expect:
            sut.first(input) == 1147
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
