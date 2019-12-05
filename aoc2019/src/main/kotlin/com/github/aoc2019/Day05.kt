package com.github.aoc2019

class Day05 {
    fun first(input: String) {
        val registers = readInput(input).toMutableList()
        val input = 1
        runProgram(registers, input)
    }

    fun second(input: String) {
        val registers = readInput(input).toMutableList()
        val input = 5
        runProgram(registers, input)
    }

    private fun runProgram(registers: MutableList<Int>, input: Int) {
        var index = 0
        while (index < registers.size) {
            val param1 = if (registers[index] / 100 % 10 == 0) {
                registers[registers[index + 1]]
            } else {
                registers[index + 1]
            }

            val param2 by lazy {
                if (registers[index] / 1000 % 10 == 0) {
                    registers[registers[index + 2]]
                } else {
                    registers[index + 2]
                }
            }

            val param3Index = if (registers[index] / 10000 % 10 == 0) {
                registers[index + 3]
            } else {
                index + 3
            }

            if (registers[index] % 10 == 1) {
                registers[param3Index] = param1 + param2
                index += 4
            } else if (registers[index] % 10 == 2) {
                registers[param3Index] = param1 * param2
                index += 4
            } else if (registers[index] % 10 == 3) {
                registers[registers[index + 1]] = input
                index += 2
            } else if (registers[index] % 10 == 4) {
                println(registers[registers[index + 1]])
                index += 2
            } else if (registers[index] % 10 == 5) {
                if (param1 != 0) {
                    index = param2
                } else {
                    index += 3
                }
            } else if (registers[index] % 10 == 6) {
                if (param1 == 0) {
                    index = param2
                } else {
                    index += 3
                }
            } else if (registers[index] % 10 == 7) {
                if (param1 < param2) {
                    registers[param3Index] = 1
                } else {
                    registers[param3Index] = 0
                }
                index += 4
            } else if (registers[index] % 10 == 8) {
                if (param1 == param2) {
                    registers[param3Index] = 1
                } else {
                    registers[param3Index] = 0
                }
                index += 4
            } else if (registers[index] % 10 == 9) { // 99
                break
            } else {
                throw Exception()
            }
        }
    }

    private fun readInput(input: String): List<Int> {
        return input.split("\n")
            .filter { it != "" }
            .first()
            .split(",")
            .map { it.toInt() }
    }
}
