package org.bitbucket.adventofcode2017

class Day05 {
    fun first(input: String): Int {
        return goThroughMaze(input) { array, position -> array[position] += 1 }
    }

    fun second(input: String): Int {
        return goThroughMaze(input) { array, position ->
            if (array[position] >= 3) {
                array[position] -= 1
            } else {
                array[position] += 1
            }
        }
    }

    private fun goThroughMaze(input: String, f: (IntArray, Int) -> Unit): Int {
        val steps = input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { it.toInt() }
                .toIntArray()
        var current = 0
        var jumps = 0
        do {
            jumps++
            val step = steps[current]
            f(steps, current)
            current += step
        } while (current >= 0 && current < steps.size)
        return jumps
    }
}
