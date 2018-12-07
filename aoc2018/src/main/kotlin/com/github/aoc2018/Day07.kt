package com.github.aoc2018

class Day07 {
    private val lineRegex = "Step (\\w) must be finished before step (\\w) can begin.".toRegex()

    fun first(input: String): String {
        val predecessors = loadInput(input)

        val allLetters = predecessors.flatMap { listOf(it.prev, it.next) }.toMutableSet()
        val result = mutableListOf<Char>()

        while (allLetters.isNotEmpty()) {
            val letter = allLetters.filter { candidate -> !predecessors.any { it.next == candidate } }.sorted().first()
            predecessors.removeAll { it.prev == letter }
            allLetters.remove(letter)
            result.add(letter)
        }
        return result.joinToString("")
    }

    fun second(input: String, rest: Int, workersNumber: Int): Int {
        val predecessors = loadInput(input)
        val allLetters = predecessors.flatMap { listOf(it.prev, it.next) }.toMutableSet()
        val result = mutableListOf<Char>()

        val workerTimes = mutableMapOf<Int, Int>()
        val workersLetters = mutableMapOf<Int, Char>()
        repeat(workersNumber) { workerTimes[it] = 0 }

        var time = 0
        while (allLetters.isNotEmpty()) {
            val workerTimesToClean = mutableListOf<Int>()
            workerTimes.forEach { (worker, limitTime) ->
                if (limitTime == time) {
                    val letter = workersLetters.remove(worker)
                    if (letter != null) {
                        workerTimesToClean.add(worker)
                        predecessors.removeAll { it.prev == letter }
                        allLetters.remove(letter)
                        result.add(letter)
                    }
                }
            }
            workerTimesToClean.forEach { workerTimes[it] = time }

            val letters = allLetters
                .filter { candidate -> !predecessors.any { it.next == candidate } }
                .filter { !workersLetters.values.contains(it) }
                .sorted()
            val letterTimes = letters.map { it to (it - 'A' + 1 + rest) }

            letterTimes.forEach { (letter, letterTime) ->
                val workerTime = workerTimes.entries.find { it.value <= time }
                if (workerTime != null) {
                    workerTimes[workerTime.key] = time + letterTime
                    workersLetters[workerTime.key] = letter
                }
            }
            time++
        }
        return time - 1
    }

    private fun loadInput(input: String): MutableList<Predecessor> =
        input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toPredecessor() }
            .toMutableList()

    private fun String.toPredecessor(): Predecessor {
        val (prev, next) = lineRegex.matchEntire(this)!!.destructured
        return Predecessor(prev[0], next[0])
    }

    data class Predecessor(val prev: Char, val next: Char)
}
