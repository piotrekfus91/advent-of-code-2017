package com.github.aoc2017

class Day14 {
    val day10 = Day10()
    val size = 128

    fun first(input: String): Int {
        return toBinaryStrings(input)
                .map { it.toCharArray().count { it == '1' } }
                .sum()
    }

    fun second(input: String): Int {
        val binaryStrings = toBinaryStrings(input)
        val disk = Array(size) { IntArray(size) }
        initDisk(disk, binaryStrings)
        var regions = 0
        repeat(size) { x ->
            repeat(size) { y ->
                if(disk[x][y] == 1) {
                    regions++
                    removeRegion(disk, x, y)
                }
            }
        }
        return regions
    }

    private fun initDisk(disk: Array<IntArray>, binaryStrings: List<String>) {
        repeat(size) { x ->
            repeat(size) { y ->
                disk[x][y] = if (binaryStrings[x][y] == '1') 1 else 0
            }
        }
    }

    private fun removeRegion(b: Array<IntArray>, x: Int, y: Int) {
        val currentValue = b[x][y]
        if (currentValue == 1) {
            b[x][y] = 0
            if (x - 1 >= 0) removeRegion(b, (x-1 + size) % size, y)
            if (x + 1 < size) removeRegion(b, (x+1) % size, y)
            if (y - 1 >= 0) removeRegion(b, x, (y-1 + size) % size)
            if (y + 1 < size) removeRegion(b, x, (y+1) % size)
        }
    }

    private fun toBinaryStrings(input: String): List<String> {
        return Array(size) { it }
                .map { "$input-$it" }
                .map { day10.second(it) }
                .map { toBinary(it) }
    }

    internal fun toBinary(hash: String): String {
        return hash.toCharArray()
                .map { if (it >= '0' && it <= '9') it - '0' else it - 'a' + 10 }
                .map { it.toString(2) }
                .map { it.padStart(4, '0') }
                .joinToString("")
    }
}
