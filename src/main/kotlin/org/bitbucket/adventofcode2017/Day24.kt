package org.bitbucket.adventofcode2017

class Day24 {
    fun first(input: String): Int {
        val allBridges = buildBridges(input)
        return allBridges.map { it.sumBy { it.sum() } }.max()!!
    }

    fun second(input: String): Int {
        val allBridges = buildBridges(input)
        val longest = allBridges.map { it.size }.max()!!
        return allBridges.filter { it.size == longest }.map { it.map { it.sum() }.sum() }.max()!!
    }

    private fun buildBridges(input: String): List<List<List<Int>>> {
        val pins = input.split("\n")
                .map { it.trim() }
                .filterNot { it == "" }
                .map { it.split("/").map { it.toInt() } }
        val startingPins = pins.filter { 0 in it }
        return startingPins.flatMap { build(listOf(it), it.sorted()[1], pins) }
    }

    private fun build(used: List<List<Int>>, lastValue: Int, all: List<List<Int>>): List<List<List<Int>>> {
        val available = (all - used).filter { lastValue in it }
        return if (available.isEmpty()) {
            listOf(used)
        } else {
            available.flatMap { build(used + listOf(it), if(it[0] == lastValue) it[1] else it[0], all) }
        }
    }
}
