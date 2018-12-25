package com.github.aoc2018

class Day20 {
    fun first(input: String): Int {
        val visitedIn = prepareDistances(input)
        return visitedIn.values.max()!!
    }

    fun second(input: String): Int {
        val visitedIn = prepareDistances(input)
        return visitedIn.values.count { it >= 1000 }
    }

    private fun prepareDistances(input: String): MutableMap<Point, Int> {
        val readyInput = input.substring(1, input.length - 1)
        var x = 0
        var y = 0
        val visitedIn = mutableMapOf<Point, Int>()
        var steps = 0
        var pos = 0
        val returnToPoints = mutableListOf<Point>()
        val returnToSteps = mutableListOf<Int>()
        while (pos < readyInput.length) {
            val c = readyInput[pos]
            when (c) {
                'N' -> y++
                'S' -> y--
                'W' -> x--
                'E' -> x++
                '(' -> {
                    returnToPoints.add(Point(x, y))
                    returnToSteps.add(steps)
                }
                ')' -> {
                    val lastPoint = returnToPoints.removeAt(returnToPoints.size - 1)
                    x = lastPoint.x
                    y = lastPoint.y
                    steps = returnToSteps.removeAt(returnToSteps.size - 1)
                }
                '|' -> {
                    val lastPoint = returnToPoints.last()
                    x = lastPoint.x
                    y = lastPoint.y
                    steps = returnToSteps.last()
                }
            }
            val p = Point(x, y)
            if (!visitedIn.containsKey(p)) {
                visitedIn[p] = ++steps
            }
            pos++
        }
        return visitedIn
    }

    data class Point(val x: Int, val y: Int)
}
