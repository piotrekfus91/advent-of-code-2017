package com.github.aoc2018

class Day11 {
    private val MAP_SIZE = 300

    fun first(input: Int): String {
        val calculation = findForSize(input, 3)
        return "${calculation.x},${calculation.y}"
    }

    fun second(input: Int): String {
        val (size, calculation) = (3 until MAP_SIZE - 2)
            .map { it to findForSize(input, it) }
            .maxBy { it.second.score }!!
        return "${calculation.x},${calculation.y},$size"
    }

    private fun findForSize(input: Int, size: Int): Calculation {
        val matrix = Array(MAP_SIZE) { IntArray(MAP_SIZE) }
        (0 until MAP_SIZE).forEach { x ->
            (0 until MAP_SIZE).forEach { y ->
                matrix[x][y] = calcPowerLevel(x - 1, y - 1, input)
            }
        }
        var result = Int.MIN_VALUE
        var bestX = -1
        var bestY = -1
        (0 until MAP_SIZE-size).forEach { x ->
            (0 until MAP_SIZE-size).forEach { y ->
                var sum = 0
                (0 until size).forEach { a ->
                    (0 until size).forEach { b ->
                        sum += matrix[x + a][y + b]
                    }
                }
                if (sum > result) {
                    result = sum
                    bestX = x
                    bestY = y
                }
            }
        }
        return Calculation(bestX-1, bestY-1, result)
    }

    fun calcPowerLevel(x: Int, y: Int, serial: Int): Int {
        val rackId = x + 10
        var powerLevel = rackId * y
        powerLevel += serial
        powerLevel *= rackId
        powerLevel = (powerLevel / 100) % 10
        powerLevel -= 5
        return powerLevel
    }

    data class Calculation(val x: Int, val y: Int, val score: Int)
}
