package com.github.aoc2017

class Day10 {
    fun first(input: String, size: Int): Int {
        val array: Array<Int> = Array(size) {it}
        var currentPosition = 0
        var skip = 0

        val lengths = input.split("\\s*,\\s*".toRegex())
                .map { it.toInt() }

        lengths.forEach { length ->
            val from = currentPosition
            val to = (from + length) % size
            array.reverseRangeCyclic(from, to)
            currentPosition = (currentPosition + length + skip) % size
            skip++
        }

        return array[0] * array[1]
    }

    fun second(input: String): String {
        val size = 256
        val array: Array<Int> = Array(size) {it}

        val lengths = fromAsciiAndDefault(input)
        var currentPosition = 0
        var skip = 0

        repeat(64) {
            lengths.forEach { length ->
                val from = currentPosition
                val to = (from + length) % size
                array.reverseRangeCyclic(from, to)
                currentPosition = (currentPosition + length + skip) % size
                skip++
            }
        }

        val results = mutableListOf<Int>()
        repeat(256/16) {
            val subarray = array.sliceArray(IntRange(16 * it + 1, 16 * it + 15))
            val result = countXor(subarray, array[16 * it])
            results.add(result)
        }
        return results.map { String.format("%02X", it.toByte()) }.joinToString("").toLowerCase()
    }

    internal fun fromAsciiAndDefault(input: String) =
            input.toByteArray().map { it.toInt() }.toList() + listOf(17, 31, 73, 47, 23)

    internal fun countXor(array: Array<Int>, first: Int): Int {
        return array.fold(first) { acc, curr -> acc xor curr }
    }
}
