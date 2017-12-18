package org.bitbucket.adventofcode2017

import java.util.*
import kotlin.collections.ArrayList

class Day17 {
    fun first(step: Int) = run(2017, 2017, step)
    fun second(step: Int) = run(50000000, 0, step)

    internal fun run(limit: Int, before: Int, skip: Int): Int {
        val insertPositions = buildPositions(limit, skip)

        var after = insertPositions[before] + 1
        repeat(limit) {
            val revertedIndex = limit - it
            val position = insertPositions[revertedIndex]
            if (position == after) {
                return revertedIndex
            } else if (position < after) {
                after--
            }
        }
        return 0
    }

    private fun buildPositions(limit: Int, skip: Int): ArrayList<Int> {
        val positions = LinkedList<Int>()
        positions.add(0)
        var currentPosition = 0
        repeat(limit) {
            val i = it + 1
            currentPosition = (currentPosition + skip) % i + 1
            positions.add(currentPosition)
        }

        return ArrayList(positions)
    }
}
