package com.github.aoc2018

class Day13 {
    fun first(input: String): String {
        val map = loadMap(input)
        val height = map.size
        val width = map.maxBy { it.size }!!.size
        val carts = initCarts(height, width, map)

        while (true) {
            carts.sort()
            carts.forEach { cart ->
                cart.move(map)
                val collision = carts.find { cart.id != it.id && cart.x == it.x && cart.y == it.y }
                if (collision != null) {
                    return "${cart.x},${cart.y}"
                }
            }
        }
    }

    fun second(input: String): String {
        val map = loadMap(input)

        val height = map.size
        val width = map.maxBy { it.size }!!.size
        val carts = initCarts(height, width, map)

        while (true) {
            carts.sort()
            var i = 0
            while (i < carts.size) {
                val cart = carts[i]
                cart.move(map)
                val collision = carts.find { cart.id != it.id && cart.x == it.x && cart.y == it.y }
                if (collision != null) {
                    val collisionIndex = carts.indexOf(collision)
                    carts.removeIf { it.id == cart.id || it.id == collision.id }
                    if (collisionIndex < i) {
                        i--
                    }
                } else {
                    i++
                }
                if (carts.size == 1) {
                    return "${carts[0].x},${carts[0].y}"
                }
            }
        }
    }

    private fun initCarts(height: Int, width: Int, map: List<CharArray>): MutableList<Cart> {
        val carts = mutableListOf<Cart>()

        repeat(height) { y ->
            repeat(width) { x ->
                if (map[y].size > x && map[y][x] in listOf('>', '<', '^', 'v')) {
                    carts.add(Cart(x, y, from(map[y][x]), Cart.CrossroadDecision.LEFT))
                }
            }
        }
        return carts
    }

    private fun loadMap(input: String): List<CharArray> {
        return input.split("\n")
            .filter { it.trim() != "" }
            .map { it.toCharArray() }
    }

    data class Cart (var x: Int, var y: Int, var direction: Direction, var crossroadDecision: CrossroadDecision) : Comparable<Cart> {
        companion object {
            var generator: Int = 0
        }

        val id = generator++

        override fun compareTo(other: Cart): Int {
            if (y == other.y) {
                return x - other.x
            }
            return y - other.y
        }

        fun move(map: List<CharArray>) {
            if (map[y][x] == '+') {
                turnOnCrossroad()
            }
            if (map[y][x] in listOf('/', '\\')) {
                turnOnBend(map)
            }
            moveOneStep()
        }

        private fun moveOneStep() {
            when (direction) {
                Direction.LEFT -> x--
                Direction.RIGHT -> x++
                Direction.UP -> y--
                Direction.DOWN -> y++
            }
        }

        private fun turnOnCrossroad() {
            when (crossroadDecision) {
                CrossroadDecision.LEFT -> {
                    direction = turnLeft(direction)
                    crossroadDecision = CrossroadDecision.STRAIGHT
                }
                CrossroadDecision.STRAIGHT -> {
                    crossroadDecision = CrossroadDecision.RIGHT
                }
                CrossroadDecision.RIGHT -> {
                    direction = turnRight(direction)
                    crossroadDecision = CrossroadDecision.LEFT
                }
            }
        }

        private fun turnLeft(direction: Direction): Direction {
            return when (direction) {
                Direction.LEFT -> Direction.DOWN
                Direction.UP -> Direction.LEFT
                Direction.RIGHT -> Direction.UP
                Direction.DOWN -> Direction.RIGHT
            }
        }

        private fun turnRight(direction: Direction): Direction {
            return turnLeft(turnLeft(turnLeft(direction)))
        }

        private fun turnOnBend(map: List<CharArray>) {
            if (direction == Direction.DOWN) {
                direction = if (map[y][x] == '\\') Direction.RIGHT else Direction.LEFT
            } else if (direction == Direction.UP) {
                direction = if (map[y][x] == '\\') Direction.LEFT else Direction.RIGHT
            } else if (direction == Direction.LEFT) {
                direction = if (map[y][x] == '\\') Direction.UP else Direction.DOWN
            } else {
                direction = if (map[y][x] == '\\') Direction.DOWN else Direction.UP
            }
        }

        enum class Direction { UP, DOWN, LEFT, RIGHT }
        enum class CrossroadDecision { LEFT, STRAIGHT, RIGHT }

    }

    fun from(c: Char): Cart.Direction {
        return when (c) {
            '>' -> Cart.Direction.RIGHT
            '<' -> Cart.Direction.LEFT
            '^' -> Cart.Direction.UP
            'v' -> Cart.Direction.DOWN
            else -> throw RuntimeException()
        }
    }
}
