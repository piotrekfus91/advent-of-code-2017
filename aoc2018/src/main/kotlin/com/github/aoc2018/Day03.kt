package com.github.aoc2018

class Day03 {
    private val claimRegex = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".toRegex()

    fun first(input: String): Int {
        val claims = readInput(input)
        val overlappedPoints = mutableSetOf<Point>()
        val width = claims.map { it.leftStart + it.rectangleWidth }.max()!!
        val height = claims.map { it.topStart + it.rectangleHeight }.max()!!
        repeat(width) { x ->
            repeat(height) { y ->
                val p = Point(x, y)
                val overlapping = claims.filter { it.contains(p) }
                if (overlapping.size > 1) {
                    overlappedPoints.add(p)
                }
            }
        }
        return overlappedPoints.size
    }

    fun second(input: String): String {
        val claims = readInput(input)
        val claim = claims.filter { claim ->
            claims.filter { c -> !claim.disjoint(c) }.size == 1
        }
        assert(claim.size == 1)
        return claim[0].id
    }

    private fun readInput(input: String): List<Claim> =
        input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { claimRegex.matchEntire(it) }
            .map { it!!.destructured }
            .map { (id, leftStart, topStart, rectangleWidth, rectangleHeight) ->
                Claim(id, leftStart.toInt(), topStart.toInt(), rectangleWidth.toInt(), rectangleHeight.toInt())
            }

    data class Point(val x: Int, val y: Int)

    data class Claim(val id: String, val leftStart: Int, val topStart: Int, val rectangleWidth: Int, val rectangleHeight: Int) {
        fun contains(point: Point): Boolean {
            return point.x >= leftStart && point.x < leftStart + rectangleWidth
                    && point.y >= topStart && point.y < topStart + rectangleHeight
        }

        fun disjoint(claim: Claim): Boolean {
            return (leftStart >= claim.leftStart + claim.rectangleWidth || leftStart + rectangleWidth <= claim.leftStart)
                || (topStart >= claim.topStart + claim.rectangleHeight || topStart + rectangleHeight <= claim.topStart)
        }
    }
}
