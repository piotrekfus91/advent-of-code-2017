package com.github.aoc2018

class Day02 {
    fun first(input: String): Int {
        val counts = readInput(input)
            .map { it.toCharArray() }
            .map {
                it.groupBy { it }.mapValues { it.value.size }
            }
        val doubles = counts.count { it.values.contains(2) }
        val triples = counts.count { it.values.contains(3) }
        return doubles * triples
    }
    
    fun second(input: String): String {
        val boxes = readInput(input)
        boxes.forEach { first ->
            boxes.forEach { second ->
                if (first != second) {
                    var count = 0
                    var lastPos = -1
                    repeat(first.length) {
                        if(first[it] != second[it]) {
                            count++
                            lastPos = it
                        }
                    }
                    if (count == 1) {
                        return first.substring(0, lastPos) + first.substring(lastPos + 1)
                    }
                }
            }
        }
        throw IllegalArgumentException()
    }

    private fun readInput(input: String): List<String> {
        return input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
    }
}
