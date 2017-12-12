package org.bitbucket.adventofcode2017

import java.util.*

class Day12 {
    fun first(input: String): Int {
        val programsNeighbours = buildProgramNeighbours(input)
        val visited = buildGroup(programsNeighbours, 0, false)
        return visited.size
    }

    fun second(input: String): Int {
        val programsNeighbours = buildProgramNeighbours(input)
        var count = 0
        while(programsNeighbours.isNotEmpty()) {
            buildGroup(programsNeighbours, programsNeighbours.keys.first(), true)
            count++
        }
        return count
    }

    private fun buildGroup(programsNeighbours: MutableMap<Int, List<Int>>, startingPoint: Int, shouldRemove: Boolean): MutableSet<Int> {
        val visited = mutableSetOf(*(programsNeighbours.getValue(startingPoint).toTypedArray()))
        val toVisit: Queue<Int> = LinkedList<Int>()
        toVisit.addAll(programsNeighbours.getValue(startingPoint))
        while (toVisit.isNotEmpty()) {
            val next = toVisit.remove()
            programsNeighbours.getValue(next).filter { !visited.contains(it) }.forEach { toVisit.add(it); visited.add(it) }
        }
        if(shouldRemove) {
            visited.forEach { programsNeighbours.remove(it) }
        }
        return visited
    }

    private fun buildProgramNeighbours(input: String): MutableMap<Int, List<Int>> {
        return input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { it.split("\\s*<->\\s*".toRegex()) }
                .map { it[0].toInt() to it[1].split("\\s*,\\s*".toRegex()).map { it.toInt() }.toList() }
                .toMap()
                .toMutableMap()
    }
}
