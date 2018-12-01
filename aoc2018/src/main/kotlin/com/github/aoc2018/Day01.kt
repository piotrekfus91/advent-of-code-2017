package com.github.aoc2018

class Day01 {
    fun first(input: String) = readInput(input).sum()

    fun second(input: String): Int {
        val frequencies = readInput(input)
        var sum = 0
        val visited = mutableSetOf(sum)
        while(true) {
            frequencies.forEach {
                sum += it
                if (visited.contains(sum)) {
                    return sum
                } else {
                    visited.add(sum)
                }
            }
        }
    }

    private fun readInput(input: String): List<Int> {
        return input.split(",?\\s+".toRegex())
            .filter { it != "" }
            .map { it.toInt() }
    }
}
