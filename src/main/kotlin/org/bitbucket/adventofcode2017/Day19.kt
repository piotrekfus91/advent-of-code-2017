package org.bitbucket.adventofcode2017

class Day19 {
    fun first(input: String): String {
        val map = loadMap(input)

        var position = 0 to map[0].indexOf('|')
        var currentDirection: Direction? = Direction.DOWN
        val foundChars = mutableListOf<Char>()
        val step = Step(0)

        while (currentDirection != null) {
            position = goForward(map, position, currentDirection, foundChars, step)
            currentDirection = nextDirection(map, position, currentDirection)
        }

        return String(foundChars.toCharArray())
    }

    fun second(input: String): Int {
        val map = loadMap(input)

        var position = 0 to map[0].indexOf('|')
        var currentDirection: Direction? = Direction.DOWN
        val foundChars = mutableListOf<Char>()
        val step = Step(0)

        while (currentDirection != null) {
            position = goForward(map, position, currentDirection, foundChars, step)
            currentDirection = nextDirection(map, position, currentDirection)
        }

        return step.value + 1
    }

    private fun loadMap(input: String): List<CharArray> {
        val longestLine = input.split("\n")
                .filter { it != "" }
                .map { it.length }
                .max()!!

        val map = input.split("\n")
                .filter { it != "" }
                .map { it.padEnd(longestLine, ' ') }
                .map { it.toCharArray() }
        return map
    }

    private fun goForward(map: List<CharArray>, position: Pair<Int, Int>, currentDirection: Direction, foundChars: MutableList<Char>, step: Step): Pair<Int, Int> {
        var x = position.second
        var y = position.first
        do {
            if (map[y][x].isLetter()) {
                foundChars.add(map[y][x])
            }
            if (currentDirection == Direction.UP) if (y-1 >= 0 && map[y-1][x] != ' ') {y--; step.inc() } else return y to x
            else if (currentDirection == Direction.DOWN) if(y+1 < map.size && map[y+1][x] != ' ') {y++; step.inc()} else return y to x
            else if (currentDirection == Direction.LEFT) if(x-1 >= 0 && map[y][x-1] != ' ') {x--; step.inc()} else return y to x
            else if (currentDirection == Direction.RIGHT) if(x+1 < map[y].size && map[y][x+1] != ' ') {x++; step.inc()} else return y to x
        } while(true)
    }

    private fun nextDirection(map: List<CharArray>, position: Pair<Int, Int>, currentDirection: Direction): Direction? {
        val availableDirections = Direction.values().filterNot { it == oposites[currentDirection] }
        for(dir in availableDirections) {
            if (dir == Direction.UP && position.first - 1 >= 0 && map[position.first - 1][position.second] != ' ') return Direction.UP
            else if (dir == Direction.DOWN && position.first + 1 < map.size && map[position.first + 1][position.second] != ' ') return Direction.DOWN
            else if (dir == Direction.LEFT && position.second - 1 >= 0 && map[position.first][position.second - 1] != ' ') return Direction.LEFT
            else if (dir == Direction.RIGHT && position.second + 1 < map[position.first].size && map[position.first][position.second + 1] != ' ') return Direction.RIGHT
        }
        return null
    }

    enum class Direction { UP, DOWN, LEFT, RIGHT }
    val oposites = mutableMapOf(
            Direction.UP to Direction.DOWN,
            Direction.DOWN to Direction.UP,
            Direction.LEFT to Direction.RIGHT,
            Direction.RIGHT to Direction.LEFT
    )

    class Step(var value: Int) {
        fun inc() {
            value++
        }
    }
}
