package com.github.aoc2017

class Day15 {
    fun first(startA: Int, startB: Int): Int {
        val multA = 16807L
        val multB = 48271L
        var resultA: Long = startA.toLong()
        var resultB: Long = startB.toLong()
        var count = 0
        val mod = Short.MAX_VALUE

        repeat(40_000_000) {
            resultA = (resultA * multA) % 2147483647
            resultB = (resultB * multB) % 2147483647

            val firstBitsA = resultA.toInt() shl 16 shr 16
            val firstBitsB = resultB.toInt() shl 16 shr 16

            if (firstBitsA == firstBitsB) {
                count++
            }
        }
        return count
    }

    fun second(startA: Int, startB: Int): Int {
        val multA = 16807L
        val multB = 48271L
        var resultA: Long = startA.toLong()
        var resultB: Long = startB.toLong()
        val listA = mutableListOf<Long>()
        val listB = mutableListOf<Long>()

        while(listA.size != 5_000_000) {
            resultA = (resultA * multA) % 2147483647
            if (resultA % 4 == 0L) {
                listA.add(resultA)
            }
        }

        while(listB.size != 5_000_000) {
            resultB = (resultB * multB) % 2147483647
            if (resultB % 8 == 0L) {
                listB.add(resultB)
            }
        }

        return listA.zip(listB).count {
            val firstBitsA = it.first.toInt() shl 16 shr 16
            val firstBitsB = it.second.toInt() shl 16 shr 16

            firstBitsA == firstBitsB
        }
    }
}
