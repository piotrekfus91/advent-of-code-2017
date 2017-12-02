package org.bitbucket.adventofcode2017

class Day01 {
    fun first(input: String): Int {
        return (input + input[0]).toCharArray()
                .toList()
                .sliding(2)
                .filter { it[0] == it[1] }
                .map { it[0] - '0' }
                .sum()
    }

    fun second(input: String): Int {
        return input.take(input.length / 2)
                .withIndex()
                .filter { it.value == input.get(input.length / 2 + it.index) }
                .map { it.value - '0' }
                .map { it * 2 }
                .sum()
    }
}