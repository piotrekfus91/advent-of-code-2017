package com.github.aoc2018

class Day22 {
    private val extension = 100
    private val torchAvailable = listOf('.', '|')
    private val climbingGearAvailable = listOf('.', '=')
    private val neitherAvailable = listOf('=', '|')

    fun first(depth: Int, targetX: Int, targetY: Int): Int {
        val geologicIndices = Array(targetY+extension) { Array(targetX+extension) { 0 } }
        val erosionLevels = Array(targetY+extension) { Array(targetX+extension) { 0 } }
        val types: Array<Array<Char>> = Array(targetY+extension) { Array(targetX+extension) { ' ' } }
        prepareEnv(targetY, targetX, geologicIndices, erosionLevels, depth, types)
        repeat(targetY+extension) { y ->
            repeat(targetX+extension) { x ->
                print(types[y][x])
            }
            println()
        }

        return types.withIndex().filter { it.index <= targetY }.map { it.value }.sumBy {
            it.withIndex().filter { it.index <= targetX }.map {it.value }.sumBy { c ->
                when (c) {
                    '.' -> 0
                    '=' -> 1
                    '|' -> 2
                    else -> throw RuntimeException()
                }
            }
        }
    }

    fun second(depth: Int, targetX: Int, targetY: Int): Int {
        val geologicIndices = Array(targetY + extension) { Array(targetX + extension) { 0 } }
        val erosionLevels = Array(targetY + extension) { Array(targetX + extension) { 0 } }
        val types: Array<Array<Char>> = Array(targetY + extension) { Array(targetX + extension) { ' ' } }
        prepareEnv(targetY, targetX, geologicIndices, erosionLevels, depth, types)

        val shortestTimes = Array(targetY + extension) { Array(targetX + extension) { mutableMapOf<Char, Int>() } }
        val pointsToCheck = mutableListOf<Point>()
        pointsToCheck.add(Point(0, 0, 'T', 0))
        while (pointsToCheck.isNotEmpty()) {
            pointsToCheck.sortBy { it.shortestTime }
            val pointToCheck = pointsToCheck.removeAt(0)
            if (pointToCheck.shortestTime < shortestTimes[pointToCheck.y][pointToCheck.x].getOrDefault(pointToCheck.equipment, Int.MAX_VALUE)) {
                shortestTimes[pointToCheck.y][pointToCheck.x][pointToCheck.equipment] = pointToCheck.shortestTime
                if (pointToCheck.x > 0) {
                    addPoint(types, pointToCheck.y, pointToCheck.x - 1, pointToCheck, shortestTimes[pointToCheck.y][pointToCheck.x-1].getOrDefault(pointToCheck.equipment, Int.MAX_VALUE), pointsToCheck)
                }
                if (pointToCheck.x < shortestTimes[pointToCheck.y].size - 1) {
                    addPoint(types, pointToCheck.y, pointToCheck.x + 1, pointToCheck, shortestTimes[pointToCheck.y][pointToCheck.x+1].getOrDefault(pointToCheck.equipment, Int.MAX_VALUE), pointsToCheck)
                }
                if (pointToCheck.y > 0) {
                    addPoint(types, pointToCheck.y - 1, pointToCheck.x, pointToCheck, shortestTimes[pointToCheck.y-1][pointToCheck.x].getOrDefault(pointToCheck.equipment, Int.MAX_VALUE), pointsToCheck)
                }
                if (pointToCheck.y < shortestTimes.size - 1) {
                    addPoint(types, pointToCheck.y + 1, pointToCheck.x, pointToCheck, shortestTimes[pointToCheck.y+1][pointToCheck.x].getOrDefault(pointToCheck.equipment, Int.MAX_VALUE), pointsToCheck)
                }
            }
        }

        return listOf(shortestTimes[targetY][targetX]['T']!!, shortestTimes[targetY][targetX]['C']!! + 7).min()!!
    }

    private fun addPoint(types: Array<Array<Char>>, newY: Int, newX: Int, pointToCheck: Point, shortestTime: Int, pointsToCheck: MutableList<Point>) {
        val type = types[newY][newX]
        val currentType = types[pointToCheck.y][pointToCheck.x]
        if (type == '.') {
            if (pointToCheck.equipment == 'C') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in torchAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'T', pointToCheck.shortestTime + 8))
                }
            } else if (pointToCheck.equipment == 'T') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in climbingGearAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'C', pointToCheck.shortestTime + 8))
                }
            } else {
                if (shortestTime >= pointToCheck.shortestTime + 8) {
                    if (currentType in climbingGearAvailable)
                        pointsToCheck.add(Point(newX, newY, 'C', pointToCheck.shortestTime + 8))
                    if (currentType in torchAvailable)
                        pointsToCheck.add(Point(newX, newY, 'T', pointToCheck.shortestTime + 8))
                }
            }
        } else if (type == '=') {
            if (pointToCheck.equipment == 'C') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in neitherAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'N', pointToCheck.shortestTime + 8))
                }
            } else if (pointToCheck.equipment == 'N') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in climbingGearAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'C', pointToCheck.shortestTime + 8))
                }
            } else {
                if (shortestTime >= pointToCheck.shortestTime + 8) {
                    if (currentType in climbingGearAvailable)
                        pointsToCheck.add(Point(newX, newY, 'C', pointToCheck.shortestTime + 8))
                    if (currentType in neitherAvailable)
                        pointsToCheck.add(Point(newX, newY, 'N', pointToCheck.shortestTime + 8))
                }
            }
        } else if (type == '|') {
            if (pointToCheck.equipment == 'N') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in torchAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'T', pointToCheck.shortestTime + 8))
                }
            } else if (pointToCheck.equipment == 'T') {
                if (shortestTime >= pointToCheck.shortestTime + 1) {
                    pointsToCheck.add(Point(newX, newY, pointToCheck.equipment, pointToCheck.shortestTime + 1))
                }
                if (shortestTime >= pointToCheck.shortestTime + 8 && currentType in neitherAvailable) {
                    pointsToCheck.add(Point(newX, newY, 'N', pointToCheck.shortestTime + 8))
                }
            } else {
                if (shortestTime >= pointToCheck.shortestTime + 8) {
                    if (currentType in neitherAvailable)
                        pointsToCheck.add(Point(newX, newY, 'N', pointToCheck.shortestTime + 8))
                    if (currentType in torchAvailable)
                        pointsToCheck.add(Point(newX, newY, 'T', pointToCheck.shortestTime + 8))
                }
            }
        }
    }

    private fun prepareEnv(targetY: Int, targetX: Int, geologicIndices: Array<Array<Int>>, erosionLevels: Array<Array<Int>>, depth: Int, types: Array<Array<Char>>) {
        (0 until targetY + extension).forEach { y ->
            (0 until targetX + extension).forEach { x ->
                if (x == 0 && y == 0) {
                    geologicIndices[y][x] = 0
                } else if (x == targetX && y == targetY) {
                    geologicIndices[y][x] = 0
                } else if (y == 0) {
                    geologicIndices[y][x] = x * 16807
                } else if (x == 0) {
                    geologicIndices[y][x] = y * 48271
                } else {
                    geologicIndices[y][x] = erosionLevels[y][x - 1] * erosionLevels[y - 1][x]
                }
                erosionLevels[y][x] = ((geologicIndices[y][x] + depth) % 20183)
                types[y][x] = when (erosionLevels[y][x] % 3) {
                    0 -> '.'
                    1 -> '='
                    2 -> '|'
                    else -> throw RuntimeException()
                }
            }
        }
    }

    data class Point(val x: Int, val y: Int, val equipment: Char, val shortestTime: Int)
}
