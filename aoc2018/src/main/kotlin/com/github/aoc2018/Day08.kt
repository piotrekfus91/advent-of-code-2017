package com.github.aoc2018

import java.util.ArrayDeque
import java.util.Queue

class Day08 {
    fun first(input: String): Int {
        return buildRoot(input).deepSumOfMetadata()
    }

    fun second(input: String): Int {
        return buildRoot(input).sumOfReferencesMetadata()
    }

    private fun buildRoot(input: String): Node =
        input.trim()
            .split("\\s+".toRegex())
            .map { it.toInt() }
            .let { ArrayDeque(it) }
            .let { readNode(it) }

    private fun readNode(queue: Queue<Int>): Node {
        val numberOfChildren = queue.poll()
        val numberOfMetadata = queue.poll()
        val children = (0 until numberOfChildren).map { readNode(queue) }
        val metadata = (0 until numberOfMetadata).map { queue.poll()!! }
        return Node(children, metadata)
    }

    data class Node(val children: List<Node>, val metadata: List<Int>) {
        fun deepSumOfMetadata(): Int {
            return metadata.sum() + children.map { it.deepSumOfMetadata() }.sum()
        }

        fun sumOfReferencesMetadata(): Int =
            if (children.isEmpty()) {
                metadata.sum()
            } else {
                metadata.map { children.getOrNull(it - 1)?.run { sumOfReferencesMetadata() } ?: 0 }.sum()
            }
    }
}
