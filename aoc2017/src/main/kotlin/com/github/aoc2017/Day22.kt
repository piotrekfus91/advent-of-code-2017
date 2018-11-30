package com.github.aoc2017

class Day22 {
    fun first(input: String, maxBursts: Int = 10_000): Int {
        val map = startMap(input)
        var x = 0
        var y = 0
        var currentDirection = Direction.UP
        var numberOfInfections = 0
        repeat(maxBursts) {
            when {
                map.getValue(x to y) == '#' -> {
                    currentDirection = currentDirection.turnRight()
                    map[x to y] = '.'
                }
                else -> {
                    currentDirection = currentDirection.turnLeft()
                    map[x to y] = '#'
                    numberOfInfections++
                }
            }
            if (currentDirection == Direction.UP) y--
            if (currentDirection == Direction.DOWN) y++
            if (currentDirection == Direction.LEFT) x--
            if (currentDirection == Direction.RIGHT) x++
        }
        return numberOfInfections
    }

    private fun startMap(input: String): MutableMap<Pair<Int, Int>, Char> {
        val lines = input.split("\n")
        val mapAsStrings = lines
                .map { it.trim() }
                .filter { it != "" }
        val maps = mapAsStrings.withIndex().map { line ->
            val x = line.index - mapAsStrings.size / 2
            line.value.toCharArray().withIndex().map {
                val y = it.index - mapAsStrings.size / 2
                (y to x) to it.value
            }.toMap()
        }
        val map = maps.fold(mapOf<Pair<Int, Int>, Char>()) { acc, x -> acc + x }.toMutableMap().withDefault { '.' }
        return map
    }

    fun second(input: String, maxBursts: Int = 10_000_000): Int {
        val map = startMap(input)
        var x = 0
        var y = 0
        var currentDirection = Direction.UP
        var numberOfInfections = 0
        repeat(maxBursts) {
            when {
                map.getValue(x to y) == '#' -> {
                    currentDirection = currentDirection.turnRight()
                    map[x to y] = 'F'
                }
                map.getValue(x to y) == 'W' -> {
                    map[x to y] = '#'
                    numberOfInfections++
                }
                map[x to y] == 'F' -> {
                    currentDirection = currentDirection.turnAround()
                    map[x to y] = '.'
                }
                else -> {
                    currentDirection = currentDirection.turnLeft()
                    map[x to y] = 'W'
                }
            }
            if (currentDirection == Direction.UP) y--
            if (currentDirection == Direction.DOWN) y++
            if (currentDirection == Direction.LEFT) x--
            if (currentDirection == Direction.RIGHT) x++
        }
        return numberOfInfections
    }

    enum class Direction(val dir: Int) {
        UP(0),
        RIGHT(1),
        DOWN(2),
        LEFT(3);

        fun turnRight(): Direction = Direction.values().first { it.dir == (dir+1)%4 }
        fun turnLeft(): Direction = Direction.values().first { it.dir == (dir+4-1)%4 }
        fun turnAround(): Direction = Direction.values().first { it.dir == (dir + 2)%4 }
    }
}
