package com.github.aoc2018

class Day23 {
    private val lineRegex = "pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)".toRegex()
    private val startPoint = Point(0, 0, 0)

    fun first(input: String): Int {
        val nanorobots = loadNanorobots(input)
        val bestRadiusNanorobot = nanorobots.maxBy { it.r }!!
        return nanorobots.count { bestRadiusNanorobot.inRadius(it.point) }
    }

    fun second(input: String): Int {
        val nanorobots = loadNanorobots(input)
        var minX = nanorobots.minBy { it.point.x }!!.point.x
        var minY = nanorobots.minBy { it.point.y }!!.point.y
        var minZ = nanorobots.minBy { it.point.z }!!.point.z
        var maxX = nanorobots.maxBy { it.point.x }!!.point.x
        var maxY = nanorobots.maxBy { it.point.y }!!.point.y
        var maxZ = nanorobots.maxBy { it.point.z }!!.point.z

        val maxDifference = listOf(maxX - minX, maxY - minY, maxZ - minZ).max()!!

        var distance = 1
        while (distance < maxDifference) {
            distance *= 2
        }

        while (true) {
            var targetCount = 0
            lateinit var best: Point
            var bestDistance = Int.MAX_VALUE

            for (x in minX..maxX step distance) {
                for (y in minY..maxY step distance) {
                    for (z in minZ..maxZ step distance) {
                        val p = Point(x, y, z)
                        val count = nanorobots.count { it.inRadius(p) }
                        if (count > targetCount) {
                            targetCount = count
                            best = p
                            bestDistance = startPoint.distance(p)
                        } else if (count == targetCount) {
                            if (startPoint.distance(p) < bestDistance) {
                                best = p
                                bestDistance = startPoint.distance(p)
                            }
                        }
                    }
                }
            }
            if (distance == 1) {
                return bestDistance
            } else {
                minX = best.x - distance
                minY = best.y - distance
                minZ = best.z - distance
                maxX = best.x + distance
                maxY = best.y + distance
                maxZ = best.z + distance
                distance /= 2
            }
        }
    }

    private fun loadNanorobots(input: String): List<Nanorobot> =
        input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toNanorobot() }

    data class Point(val x: Int, val y: Int, val z: Int) {
        fun distance(other: Point): Int {
            return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z)
        }
    }

    data class Nanorobot(val point: Point, val r: Int) {
        fun inRadius(other: Point): Boolean {
            return point.distance(other) <= r
        }
    }

    private fun String.toNanorobot(): Nanorobot {
        val (x, y, z, r) = lineRegex.matchEntire(this)!!.destructured
        return Nanorobot(Point(x.toInt(), y.toInt(), z.toInt()), r.toInt())
    }
}
