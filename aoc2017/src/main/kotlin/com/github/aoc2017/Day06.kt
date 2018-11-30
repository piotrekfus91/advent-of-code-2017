package com.github.aoc2017

class Day06 {
    fun first(input: String): Int {
        val banks = buildBanks(input)
        val visited = buildVisited(banks)
        return visited.size
    }

    fun second(input: String): Int {
        val banks = buildBanks(input)
        val visited = buildVisited(banks)
        return visited.size - visited.withIndex().first { it.value.contentEquals(banks) }.index
    }

    private fun buildBanks(input: String): IntArray {
        return input.split("\\s+".toRegex())
                .map { it.toInt() }
                .toIntArray()
    }

    private fun buildVisited(banks: IntArray): MutableList<IntArray> {
        val visited = mutableListOf<IntArray>()
        do {
            visited.add(banks.copyOf())
            val maxValue = banks.max()!!
            val maxPosition = banks.indexOfFirst { it == maxValue }
            banks[maxPosition] = 0
            repeat(maxValue) {
                banks[(maxPosition + 1 + it) % banks.size] += 1
            }
        } while (visited.none { banks.contentEquals(it) })
        return visited
    }
}
