package com.github.aoc2018

class Day10 {
    private val lineRegex = "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>".toRegex()

    fun first(input: String, limit: Int) {
        val points = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toPoint() }
        repeat(limit) {
            val maxy = points.maxBy { it.y }!!.y
            val miny = points.minBy { it.y }!!.y
            if (Math.abs(maxy - miny) < 50) {
                show(points, it)
            }
            points.forEach { point ->
                point.x = point.x + point.vx
                point.y = point.y + point.vy
            }
        }
    }

    private fun show(points: List<Point>, time: Int) {
        println(time)
        val miny = points.minBy { it.y }!!.y
        val maxy = points.maxBy { it.y }!!.y
        val minx = points.minBy { it.x }!!.x
        val maxx = points.maxBy { it.x }!!.x
        (miny until maxy + 1).forEach { y ->
            (minx until maxx + 1).forEach { x ->
                if (points.find { it.x == x && it.y == y } != null) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        repeat(3) { println() }
    }

    private fun String.toPoint(): Point {
        val (x, y, vx, vy) = lineRegex.matchEntire(this)!!.destructured
        return Point(x.toInt(), y.toInt(), vx.toInt(), vy.toInt())
    }

    data class Point(var x: Int, var y: Int, val vx: Int, val vy: Int)
}
