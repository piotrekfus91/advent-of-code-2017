package com.github.aoc2018

class Day20Test extends AocSpec {
    Day20 sut = new Day20()

    def 'should pass sample tests for first task'() {
        expect:
            sut.first(input) == output
        where:
            input        | output
            '^WNE$' | 3
            '^ENWWW(NEEE|SSE(EE|N))$' | 10
            '^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$' | 18
            '^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$' | 23
            '^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$' | 31
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
