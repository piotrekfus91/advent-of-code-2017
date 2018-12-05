package com.github.aoc2018

class Day05 {
    fun first(input: String): Int {
        var currentPos = 0
        var current = input.trim()
        while (currentPos < current.length - 1 && currentPos >= 0) {
            if (readyToRemove(current, currentPos)) {
                current = current.substring(0, currentPos) + current.substring(currentPos + 2, current.length)
                if (currentPos > 0) {
                    currentPos--
                }
            } else {
                currentPos++
            }
        }
        return current.length
    }

    private fun readyToRemove(current: String, currentPos: Int) =
        current[currentPos].toLowerCase() == current[currentPos + 1].toLowerCase()
            && current[currentPos].isUpperCase() == current[currentPos + 1].isLowerCase()

    fun second(input: String): Int {
        val letters = input.toCharArray().map { it.toLowerCase() }.distinct()
        return letters.map { second(input, it) }.min()!!
    }

    fun second(input: String, letter: Char): Int {
        var currentPos = 0
        var current = input.trim()
        while (currentPos < current.length) {
            if (current[currentPos].toLowerCase() == letter.toLowerCase()) {
                current = current.substring(0, currentPos) + current.substring(currentPos + 1, current.length)
            } else {
                currentPos++
            }
        }
        return first(current)
    }
}
