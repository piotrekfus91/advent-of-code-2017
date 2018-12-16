package com.github.aoc2018

class Day16 {
    private val builders = listOf<(InstructionLine) -> Instruction>(
        { line -> Addr(line) },
        { line -> Addi(line) },
        { line -> Mulr(line) },
        { line -> Muli(line) },
        { line -> Banr(line) },
        { line -> Bani(line) },
        { line -> Borr(line) },
        { line -> Bori(line) },
        { line -> Bori(line) },
        { line -> Setr(line) },
        { line -> Seti(line) },
        { line -> Gtir(line) },
        { line -> Gtri(line) },
        { line -> Gtrr(line) },
        { line -> Eqir(line) },
        { line -> Eqri(line) },
        { line -> Eqrr(line) }
    )

    private val opcodeToInstruction = mapOf<Int, (InstructionLine) -> Instruction>(
        0 to { line -> Muli(line) },
        1 to { line -> Seti(line) },
        2 to { line -> Bani(line) },
        3 to { line -> Gtri(line) },
        4 to { line -> Gtrr(line) },
        5 to { line -> Eqrr(line) },
        6 to { line -> Addi(line) },
        7 to { line -> Gtir(line) },
        8 to { line -> Eqir(line) },
        9 to { line -> Mulr(line) },
        10 to { line -> Addr(line) },
        11 to { line -> Borr(line) },
        12 to { line -> Bori(line) },
        13 to { line -> Eqri(line) },
        14 to { line -> Banr(line) },
        15 to { line -> Setr(line) }
    )

    fun first(input: String): Int {
        val samples = input.split("\n\n").map { it.toSample() }
        return samples.filter { sample ->
            val instructions = builders.map { it.invoke(sample.instructionLine) }
            instructions.count { it.evaluate(sample.before) == sample.after } >= 3
        }.count()
    }

    fun second(input: String): Int {
        val instructionLines = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toInstructionLine() }
        val instructions = instructionLines.map { line -> opcodeToInstruction[line.opcode]!!.invoke(line) }
        val registers = mapOf<Int, Int>().withDefault { 0 }
        val afterInstructions = instructions.fold(registers) { acc, instruction -> instruction.evaluate(acc) }
        return afterInstructions[0]!!
    }

    fun guessRegisters(input: String) {
        val samples = input.split("\n\n").map { it.toSample() }
        val possibilities = mutableListOf<Pair<String, Int>>()
        samples.forEach { sample ->
            val instructions = builders.map { it.invoke(sample.instructionLine) }
            instructions.forEach { instruction ->
                if (instruction.evaluate(sample.before) == sample.after) {
                    possibilities.add(instruction.javaClass.simpleName to instruction.instructionLine.opcode)
                }
            }
        }
        possibilities.groupBy { it.first }
            .map { it.key to it.value.map { it.second }.distinct().sorted().filterNot { it in opcodeToInstruction.keys } }
            .forEach(::println)
    }

    data class InstructionLine(val opcode: Int, val a: Int, val b: Int, val c: Int)
    data class Sample(val before: Registers, val instructionLine: InstructionLine, val after: Registers)

    private fun String.toSample(): Sample {
        val split = this.split("\n")
        return Sample(split[0].toBefore(), split[1].toInstructionLine(), split[2].toAfter())
    }

    private fun String.toBefore(): Registers {
        val values = this.substring("After:  [".length, this.length - 1).split(", ").map { it.toInt() }
        return values.withIndex().map { it.index to it.value}.toMap()
    }

    private fun String.toInstructionLine(): InstructionLine {
        val values = this.split("\\s+".toRegex()).map { it.toInt() }
        return InstructionLine(values[0], values[1], values[2], values[3])
    }

    private fun String.toAfter(): Registers {
        val values = this.substring("After:  [".length, this.length - 1).split(", ").map { it.toInt() }
        return values.withIndex().map { it.index to it.value}.toMap()
    }

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

typealias Registers = Map<Int, Int>
