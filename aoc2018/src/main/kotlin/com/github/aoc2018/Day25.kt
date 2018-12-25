package com.github.aoc2018

class Day25 {
    fun first(input: String): Int {
        val constellations = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.split(",") }
            .map { it.map { it.toInt() } }
            .map { Point(it) }
            .map { Constellation(mutableSetOf(it)) }

        constellations.forEach { constellation ->
            constellations.filter { it.canMerge(constellation) }.forEach(constellation::merge)
        }

        return constellations.distinct().count()
    }

    data class Point(val coords: List<Int>) {
        fun distance(other: Point): Int = coords.zip(other.coords).sumBy { Math.abs(it.first - it.second) }
    }

    data class Constellation(val points: MutableSet<Point>) {
        fun canMerge(constellation: Constellation): Boolean =
            points.any { point -> constellation.points.any { it.distance(point) <= 3 } }

        fun merge(other: Constellation) {
            points.addAll(other.points)
            other.points.addAll(points)
        }
    }
}
