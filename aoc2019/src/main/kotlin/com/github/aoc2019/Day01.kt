package com.github.aoc2019

class Day01 {
    fun first(input: String): Int {
        return readInput(input)
            .map { calculateFuel(it) }
            .sum()
    }

    private fun calculateFuel(input: Int) = input / 3 - 2

    fun second(input: String): Int {
        return readInput(input)
            .map { calculateTotalFuel(0, it) }
            .sum()
    }

    private fun calculateTotalFuel(sum: Int, current: Int): Int {
        val currentValue = calculateFuel(current)
        return if (currentValue <= 0) {
            sum
        } else {
            calculateTotalFuel(sum + currentValue, currentValue)
        }
    }

    private fun readInput(input: String): List<Int> {
        return input.split("\n")
            .filter { it != "" }
            .map { it.toInt() }
    }
}
