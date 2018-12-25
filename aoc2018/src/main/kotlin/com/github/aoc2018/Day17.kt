package com.github.aoc2018

class Day17 {
    private val xRegex = "x=(\\d+), y=(\\d+)..(\\d+)".toRegex()
    private val yRegex = "y=(\\d+), x=(\\d+)..(\\d+)".toRegex()
    private val spring = Point(500, 0)
    private val floodableFields = listOf('.', '|', '~')

    private var minX = 0
    private var maxX = 0
    private var maxY = 0

    fun first(input: String): Int {
        val map = fillWater(input)

        return map.sumBy { it.count { it in listOf('~', '|') } }
    }

    fun second(input: String): Int {
        val map = fillWater(input)

        return map.sumBy { it.count { it in listOf('~') } }
    }

    private fun fillWater(input: String): Array<Array<Char>> {
        val clays = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .flatMap { it.toClay() }
            .toSet()

        minX = clays.minBy { it.x }!!.x - 2
        maxX = clays.maxBy { it.x }!!.x + 3
        maxY = clays.maxBy { it.y }!!.y + 1

        val map = setupMap(maxX, maxY, clays)

        val toPoorPoints = mutableListOf(Point(spring.x, spring.y))

        while (toPoorPoints.isNotEmpty()) {
            val point = toPoorPoints.removeAt(0)
            if (map[point.y][point.x] in listOf('.', '|')) {
                val toPoor = pour(point.x, point.y, map)
                toPoorPoints.addAll(toPoor)
            }
        }

        printState(map)
        return map
    }

    private fun pour(startX: Int, startY: Int, map: Array<Array<Char>>): List<Point> {
//        printState(map)
        var y = startY + 1
        while (y < map.size && map[y][startX] == '.') {
            map[y][startX] = '|'
            y++
        }
        if (y >= map.size) {
            return listOf()
        }
        if (map[y][startX] != '|') {
            return floodLevel(startX, y - 1, map)
        } else {
            return listOf()
        }
    }

    private fun floodLevel(startX: Int, startY: Int, map: Array<Array<Char>>): List<Point> {
//        printState(map)
        map[startY][startX] = '~'
        var poorPointLeft: Point? = null
        var poorPointRight: Point? = null
        var x = startX - 1
        var maxLeft = startX - 1
        var maxRight = startX + 1
        while (x >= 0 && map[startY][x] in floodableFields && map[startY+1][x] != '.' && map[startY+1][x] != '|') {
            map[startY][x] = '~'
            maxLeft = x
            x--
        }
        if (x >= 0 && map[startY+1][x] == '.') {
            map[startY][x] = '|'
            poorPointLeft = Point(x, startY)
        }
        x = startX + 1
        while (x < map[startY].size && map[startY][x] in floodableFields && map[startY+1][x] != '.' && map[startY+1][x] != '|') {
            map[startY][x] = '~'
            maxRight = x
            x++
        }
        if (x < map[startY].size && map[startY+1][x] == '.') {
            map[startY][x] = '|'
            poorPointRight = Point(x, startY)
        }
        if (poorPointLeft == null && poorPointRight == null) {
            return floodLevel(startX, startY - 1, map)
        }
        (maxLeft until maxRight + 1).forEach { x ->
            if (map[startY][x] != '#') {
                map[startY][x] = '|'
            }
        }
        return listOfNotNull(poorPointLeft, poorPointRight)
    }

    private fun setupMap(maxX: Int, maxY: Int, points: Set<Point>): Array<Array<Char>> {
        val map = Array(maxY) { Array(maxX) { '.' } }
        repeat(maxY) { y ->
            repeat(maxX) { x ->
                if (points.contains(Point(x, y))) {
                    map[y][x] = '#'
                } else if (x == 500 && y == 0) {
                    map[y][x] = '.'
                }
            }
        }
        return map
    }

    private fun printState(map: Array<Array<Char>>) {
        println("Printing state!")
        (0 until maxY).map { y ->
            (minX until maxX).map { x ->
                print(map[y][x])
            }
            println()
        }
        println()
        println()
    }

    data class Point(val x: Int, val y: Int) : Comparable<Point> {
        override fun compareTo(other: Point): Int = when {
            x == other.x -> y - other.y
            else -> x - other.x
        }
    }

    private fun String.toClay(): Set<Point> {
        return if (this.startsWith("x")) {
            val (x, y1, y2) = xRegex.matchEntire(this)!!.destructured
            (y1.toInt() until y2.toInt()+1).map { y -> Point(x.toInt(), y) }
        } else {
            val (y, x1, x2) = yRegex.matchEntire(this)!!.destructured
            (x1.toInt() until x2.toInt()+1).map { x -> Point(x, y.toInt()) }
        }.toSortedSet()
    }

}
