package org.bitbucket.adventofcode2017

class Day13 {
    fun first(input: String): Int {
        val positions = readInput(input)
        return findCatches(positions, 0)
                .map { it.first * it.second }
                .sum()
    }

    fun second(input: String): Int {
        val positions = readInput(input)
        var delay = 0
        while(true) {
            if (findCatches(positions, delay).isEmpty()) {
                return delay
            }
            delay++
        }
    }

    private fun findCatches(positions: List<Pair<Int, Int>>, delay: Int): List<Pair<Int, Int>> {
        return positions.filter { (it.first + delay) % ((it.second - 1) * 2) == 0 }
    }

    private fun readInput(input: String): List<Pair<Int, Int>> {
        return input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { it.split(": ") }
                .map { it[0].toInt() to it[1].toInt() }
    }
}
