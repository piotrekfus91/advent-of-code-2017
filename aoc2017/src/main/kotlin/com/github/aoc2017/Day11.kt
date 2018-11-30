package com.github.aoc2017

import java.lang.Math.abs

class Day11 {
    fun first(input: String): Int {
        val directions = input.split("\\s*,\\s*".toRegex())
        return directions.fold(Coordinates(0, 0, 0)) { acc, direction -> move(direction, acc) }.distance()
    }

    fun second(input: String): Int {
        val directions = input.split("\\s*,\\s*".toRegex())
        var zeros = Coordinates(0, 0, 0)
        val distance = directions.fold(zeros to zeros) { pair, direction ->
            val current = pair.first
            val max = pair.second
            val afterMove = move(direction, current)
            afterMove to listOf(max, afterMove).maxBy { it.distance() }!!
        }
        return distance.second.distance()
    }

    private fun move(direction: String, acc: Coordinates): Coordinates {
        return when (direction) {
            "n" -> acc.copy(x = acc.x + 1, z = acc.z - 1)
            "s" -> acc.copy(x = acc.x - 1, z = acc.z + 1)
            "ne" -> acc.copy(x = acc.x + 1, y = acc.y - 1)
            "sw" -> acc.copy(x = acc.x - 1, y = acc.y + 1)
            "nw" -> acc.copy(y = acc.y + 1, z = acc.z - 1)
            "se" -> acc.copy(y = acc.y - 1, z = acc.z + 1)
            else -> throw RuntimeException()
        }
    }
}

data class Coordinates(val x: Int, val y: Int, val z: Int) {
    fun distance(): Int = listOf(abs(x), abs(y), abs(z)).max()!!
}
