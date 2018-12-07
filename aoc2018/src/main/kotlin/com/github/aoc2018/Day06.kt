package com.github.aoc2018

class Day06 {
    var generator: Int = 0

    fun first(input: String): Int {
        val points = loadPoints(input)

        val width = points.map { it.p.x }.max()!! + 1
        val height = points.map { it.p.y }.max()!! + 1
        val distances = mutableListOf<MarkedPoint>()

        repeat(width) { x ->
            repeat(height) { y ->
                val p = Point(x, y)
                val closestPointDistance = points.map { it.p.manhattan(p) }.min()!!
                val closestPoints = points.filter { it.p.manhattan(p) == closestPointDistance }
                if (closestPoints.size == 1) {
                    distances.add(MarkedPoint(p, closestPoints[0].id))
                }
            }
        }

        val idsToRemove = mutableSetOf<Int>()
        repeat(width) { x ->
            distances.find { it.p.x == x && it.p.y == 0 }?.let { idsToRemove.add(it.id) }
            distances.find { it.p.x == x && it.p.y == height - 1 }?.let { idsToRemove.add(it.id) }
        }
        repeat(height) { y ->
            distances.find { it.p.x == 0 && it.p.y == y }?.let { idsToRemove.add(it.id) }
            distances.find { it.p.x == width - 1 && it.p.y == y }?.let { idsToRemove.add(it.id) }
        }

        return distances.filterNot { it.id in idsToRemove }.groupBy { it.id }.maxBy { it.value.size }!!.value.size
    }

    fun second(input: String, max: Int): Int {
        val points = loadPoints(input)
        val width = points.map { it.p.x }.max()!! + 1
        val height = points.map { it.p.y }.max()!! + 1

        var total = 0
        repeat(width) { x ->
            repeat(height) { y ->
                val sum = points.map { it.p }
                    .map { Point(x, y).manhattan(it) }
                    .sum()
                if (sum < max) {
                    total++
                }
            }
        }

        return total
    }

    private fun loadPoints(input: String): List<MarkedPoint> =
        input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.split(", ") }
            .map { MarkedPoint(Point(it[0].toInt(), it[1].toInt()), generator++) }

    data class Point(val x: Int, val y: Int) {
        fun manhattan(other: Point): Int {
            return Math.abs(x - other.x) + Math.abs(y - other.y)
        }
    }

    data class MarkedPoint(val p: Point, val id: Int)
}
