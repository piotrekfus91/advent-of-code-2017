package com.github.aoc2018

import spock.lang.Specification

class Day25Test extends Specification implements InputHelper {
    Day25 sut = new Day25()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input << [
                '''
 0,0,0,0
 3,0,0,0
 0,3,0,0
 0,0,3,0
 0,0,0,3
 0,0,0,6
 9,0,0,0
12,0,0,0
''',
                '''
-1,2,2,0
0,0,2,-2
0,0,0,-2
-1,2,0,0
-2,-2,-2,2
3,0,2,-1
-1,3,2,2
-1,0,-1,0
0,2,1,-2
3,0,0,0
''',
                '''
1,-1,0,1
2,0,-1,0
3,2,-1,0
0,0,3,1
0,0,-1,-1
2,3,-2,0
-2,2,0,0
2,-2,0,-1
1,-1,0,-1
3,2,0,2
''',
                '''
1,-1,-1,-2
-2,-2,0,1
0,2,1,3
-2,3,-2,1
0,2,3,-2
-1,-1,1,-2
0,-2,-1,0
-2,2,3,-1
1,2,2,0
-1,-2,0,-2
'''
            ]
            output << [2, 4, 3, 8]
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

}
