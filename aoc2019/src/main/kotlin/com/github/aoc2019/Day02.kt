package com.github.aoc2019

class Day02 {
    fun first(input: String): Int {
        val registers = readInput(input).toMutableList()
        registers[1] = 12
        registers[2] = 2
        return runProgram(registers)[0]
    }

    fun runProgram(registers: MutableList<Int>): MutableList<Int> {
        var index = 0
        while (index < registers.size) {
            if (registers[index] == 1) {
                registers[registers[index + 3]] = registers[registers[index + 1]] + registers[registers[index + 2]]
            } else if (registers[index] == 2) {
                registers[registers[index + 3]] = registers[registers[index + 1]] * registers[registers[index + 2]]
            } else if (registers[index] == 99) {
                break
            } else {
                throw Exception()
            }
            index += 4
        }
        return registers
    }

    fun second(input: String): Int {
        repeat(100) { noun ->
            repeat(100) { verb ->
                val registers = readInput(input).toMutableList()
                registers[1] = noun
                registers[2] = verb
                val output = runProgram(registers)[0]
                if (output == 19690720) {
                    return 100 * noun + verb
                }
            }
        }
        throw Exception()
    }

    private fun readInput(input: String): List<Int> {
        return input
            .split("\n")
            .first()
            .split(",")
            .filter { it != "" }
            .map { it.toInt() }
    }
}
