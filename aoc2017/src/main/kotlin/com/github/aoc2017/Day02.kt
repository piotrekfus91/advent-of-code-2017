package com.github.aoc2017

class Day02 {
    fun first(input: String): Int {
        return toInts(input)
                .map { it.min() }
                .zip(toInts(input).map { it.max() })
                .map { it.second!! - it.first!! }
                .sum()
    }

    fun second(input: String): Int {
        return toInts(input).map { line ->
            val dividend = line.find { dividend -> line.any { divisor -> dividend != divisor && dividend % divisor == 0 } }!!
            val divisor = line.find { divisor -> dividend != divisor && dividend % divisor == 0}!!
            dividend / divisor
        }.sum()
    }

    private fun toInts(input: String): List<List<Int>> {
        return input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map {
                    it.split("\\s+".toRegex())
                            .filter { it != "" }
                            .map { it.toInt() }
                }
    }
}