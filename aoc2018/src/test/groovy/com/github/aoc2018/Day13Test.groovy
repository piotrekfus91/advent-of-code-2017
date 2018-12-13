package com.github.aoc2018

import spock.lang.Specification

class Day13Test extends Specification implements InputHelper {
    Day13 sut = new Day13()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
/->-\\        
|   |  /----\\
| /-+--+-\\  |
| | |  | v  |
\\-+-/  \\-+--/
  \\------/   
            '''
        expect:
            sut.first(input) == '7,3'
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
