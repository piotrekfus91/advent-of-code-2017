package com.github.aoc2018

class Day19 {
    fun first(input: String, ipReg: Int): Int {
        val instructions = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toInstruction() }

        var registers: Registers = mapOf()
        var ip = 0
        while (true) {
            if (ip >= instructions.size) {
                return registers[0]!!
            }
            registers += (ipReg to ip)
            registers = instructions[ip].evaluate(registers)
            ip = registers[ipReg]!!
            ip++
        }
    }

    private fun String.toInstruction(): Instruction {
        val split = this.split(" ")
        val instructionLine = InstructionLine(split[1].toInt(), split[2].toInt(), split[3].toInt())
        return when(split[0]) {
            "addr" -> Addr(instructionLine)
            "addi" -> Addi(instructionLine)
            "mulr" -> Mulr(instructionLine)
            "muli" -> Muli(instructionLine)
            "banr" -> Banr(instructionLine)
            "bani" -> Bani(instructionLine)
            "borr" -> Borr(instructionLine)
            "bori" -> Bori(instructionLine)
            "setr" -> Setr(instructionLine)
            "seti" -> Seti(instructionLine)
            "gtir" -> Gtir(instructionLine)
            "gtri" -> Gtri(instructionLine)
            "gtrr" -> Gtrr(instructionLine)
            "eqir" -> Eqir(instructionLine)
            "eqri" -> Eqri(instructionLine)
            "eqrr" -> Eqrr(instructionLine)
            else -> throw RuntimeException()
        }
    }

    data class InstructionLine(val a: Int, val b: Int, val c: Int)

    abstract class Instruction {
        abstract val instructionLine: InstructionLine
        abstract fun evaluate(input: Registers): Registers
    }

    data class Addr(override val instructionLine: InstructionLine) : Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0) + input.getOrDefault(instructionLine.b, 0)))
    }

    data class Addi(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0) + instructionLine.b))
    }

    data class Mulr(override val instructionLine: InstructionLine) : Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0) * input.getOrDefault(instructionLine.b, 0)))
    }

    data class Muli(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0) * instructionLine.b))
    }

    data class Banr(override val instructionLine: InstructionLine) : Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0).and(input.getOrDefault(instructionLine.b, 0))))
    }

    data class Bani(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0).and(instructionLine.b)))
    }

    data class Borr(override val instructionLine: InstructionLine) : Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0).or(input.getOrDefault(instructionLine.b, 0))))
    }

    data class Bori(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to (input.getOrDefault(instructionLine.a, 0).or(instructionLine.b)))
    }

    data class Setr(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to input.getOrDefault(instructionLine.a, 0))
    }

    data class Seti(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to instructionLine.a)
    }

    data class Gtir(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (instructionLine.a > input.getOrDefault(instructionLine.b, 0)) 1 else 0)
    }

    data class Gtri(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (input.getOrDefault(instructionLine.a, 0) > instructionLine.b) 1 else 0)
    }

    data class Gtrr(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (input.getOrDefault(instructionLine.a, 0) > input.getOrDefault(instructionLine.b, 0)) 1 else 0)
    }

    data class Eqir(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (instructionLine.a == input.getOrDefault(instructionLine.b, 0)) 1 else 0)
    }

    data class Eqri(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (input.getOrDefault(instructionLine.a, 0) == instructionLine.b) 1 else 0)
    }

    data class Eqrr(override val instructionLine: InstructionLine): Instruction() {
        override fun evaluate(input: Registers): Registers =
            input + (instructionLine.c to if (input.getOrDefault(instructionLine.a, 0) == input.getOrDefault(instructionLine.b, 0)) 1 else 0)
    }
}
