package com.github.aoc2018

class Day02Test extends AocSpec {
    Day02 sut = new Day02()

    def 'should pass sample tests for first task'() {
        given:
            String input = '''
abcdef
bababc
abbcde
abcccd
aabcdd
abcdee
ababab
            '''
        expect:
            sut.first(input) == 12
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(taskInput))
    }

    def 'should pass sample tests for second task'() {
        given:
            String input = '''
abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz
            '''
        expect:
            sut.second(input) == 'fgij'
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(taskInput))
    }
}
