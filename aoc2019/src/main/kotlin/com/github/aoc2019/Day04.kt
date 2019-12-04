package com.github.aoc2019

class Day04 {
    fun first(input: String): Int {
        val predicates = listOf(
            containsPair(),
            isGrowing()
        )
        return findMatches(input, predicates)
    }

    fun second(input: String): Int {
        val predicates = listOf(
            containsPair(),
            isGrowing(),
            containsGroupOfTwo()
        )
        return findMatches(input, predicates)
    }

    private fun findMatches(input: String, predicates: List<(List<Int>) -> Boolean>): Int {
        val split = input.split("\n").find { it != "" }!!.split("-")
        val from = split[0].toInt()
        val to = split[1].toInt()

        return (from until to).filter { value ->
            val ints = value.toString().toCharArray().map { Character.getNumericValue(it) }
            predicates.all { it(ints) }
        }.count()
    }

    private fun containsPair() = { value: List<Int> -> value.windowed(2, step = 1).any { (a, b) -> a == b } }
    private fun isGrowing() = { value: List<Int> -> value.windowed(2, step = 1).all { (a, b) -> a <= b } }
    private fun containsGroupOfTwo() = { value: List<Int> -> value.groupBy { it }.any { it.value.size == 2 } }
}
