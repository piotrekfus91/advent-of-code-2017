package com.github.aoc2018

class Day09 {
    fun first(players: Int, lastMarble: Int): Long {
        val circle = Circle(players)

        repeat(lastMarble) { marbleId ->
            val marble = marbleId + 1
            circle.add(marble)
        }

        return circle.winnerPoints()
    }

    fun second(players: Int, lastMarble: Int): Long {
        return first(players, lastMarble * 100)
    }

    class Circle(private var players: Int) {
        private var currentNode: Node = Node(0)
        private val playersPoints = mutableMapOf<Int, Long>()
        private var currentPlayer = -1

        init {
            currentNode.left = currentNode
            currentNode.right = currentNode
        }

        fun add(marble: Int) {
            currentPlayer = (currentPlayer+1) % players

            if (marble % 23 == 0) {
                val points = playersPoints.getOrDefault(currentPlayer, 0)
                repeat(7) { currentNode = currentNode.left }
                playersPoints[currentPlayer] = points + marble + currentNode.marble
                currentNode.left.right = currentNode.right
                currentNode.right.left = currentNode.left
                currentNode = currentNode.right
            } else {
                val newNode = Node(marble).apply {
                    left = currentNode.right
                    right = currentNode.right.right
                }
                currentNode.right.right.left = newNode
                currentNode.right.right = newNode
                currentNode = newNode
            }
        }

        fun winnerPoints(): Long = playersPoints.values.max()!!

        data class Node(val marble: Int) {
            lateinit var left: Node
            lateinit var right: Node
        }
    }
}
