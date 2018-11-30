package com.github.aoc2017

import java.lang.Math.abs
import java.lang.Math.ceil
import java.lang.Math.floor
import java.lang.Math.sqrt

class Day03 {
    fun first(input: Int): Int {
        if (isSquarable(input)) {
            return sqrt(input.toDouble()).toInt() - 1
        }
        return distanceToBorder(input) + distanceToHalfOfline(input)
    }

    internal fun distanceToBorder(input: Int): Int = if (isSquarable(input)) {
        (sqrt(input.toDouble()) / 2).toInt()
    } else {
        ((sqrt(input.toDouble()) + 1) / 2).toInt()
    }

    internal fun distanceToHalfOfline(input: Int): Int {
        val upperSquare = ceil(sqrt(input.toDouble())).toInt()
        val bottomSquare = floor(sqrt(input.toDouble())).toInt()
        val centers = if (upperSquare % 2 == 1) {
            val bottomCenter = bottomSquare * bottomSquare + (upperSquare/2 + 1)
            val topCenter = bottomCenter + bottomSquare
            mutableListOf(bottomCenter, topCenter)
        } else {
            val bottomCenter = bottomSquare * bottomSquare + (bottomSquare/2 + 1)
            val topCenter = bottomCenter + upperSquare
            mutableListOf(bottomCenter, topCenter)
        }
        return centers.map { abs(it - input) }.min()!!
    }

    internal fun isSquarable(input: Int) = sqrt(input.toDouble()) == floor(sqrt(input.toDouble()))

    fun second(input: Int): Int {
        val generated = positionsGenerator.take(500).toList()
        return generated.first { it.sum > input }.sum
    }

    internal val positionsGenerator = kotlin.sequences.sequence {
        var lastValue = Position(0, 0, 1)
        var limit = 1
        val allPositions = mutableListOf(lastValue)

        yield(lastValue)

        while (true) {
            repeat(limit) {
                lastValue = lastValue.copy(x = lastValue.x + 1, sum = countSum(lastValue.x + 1, lastValue.y, allPositions))
                allPositions.add(lastValue)
                yield(lastValue)
            }
            repeat(limit) {
                lastValue = lastValue.copy(y = lastValue.y + 1, sum = countSum(lastValue.x, lastValue.y + 1, allPositions))
                allPositions.add(lastValue)
                yield(lastValue)
            }

            limit++

            repeat(limit) {
                lastValue = lastValue.copy(x = lastValue.x - 1, sum = countSum(lastValue.x - 1, lastValue.y, allPositions))
                allPositions.add(lastValue)
                yield(lastValue)
            }
            repeat(limit) {
                lastValue = lastValue.copy(y = lastValue.y - 1, sum = countSum(lastValue.x, lastValue.y - 1, allPositions))
                allPositions.add(lastValue)
                yield(lastValue)
            }

            limit++
        }
    }

    internal fun countSum(x: Int, y: Int, allPositions: List<Position>): Int {
        return allPositions.filter { abs(it.x - x) <= 1 && abs(it.y - y) <= 1 }
                .map { it.sum }
                .sum()
    }
}

data class Position(val x: Int, val y: Int, val sum: Int)
