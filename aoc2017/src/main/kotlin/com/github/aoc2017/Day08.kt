package com.github.aoc2017

class Day08 {
    fun first(input: String): Int {
        val instructions = buildInstructions(input)
        val registers = mutableMapOf<String, Int>().withDefault { 0 }
        instructions.forEach {
            if (buildCondition(registers, it.conditionRegister, it.comparator, it.conditionValue)) {
                val newValue = it.operation(registers.getValue(it.register))
                registers[it.register] = newValue
            }
        }
        return registers.maxBy { it.value }!!.value
    }

    fun second(input: String): Int {
        val instructions = buildInstructions(input)
        val registers = mutableMapOf<String, Int>().withDefault { 0 }
        var maxValue = Int.MIN_VALUE
        instructions.forEach {
            if (buildCondition(registers, it.conditionRegister, it.comparator, it.conditionValue)) {
                val newValue = it.operation(registers.getValue(it.register))
                if (newValue > maxValue) maxValue = newValue
                registers[it.register] = newValue
            }
        }
        return maxValue
    }

    private fun buildInstructions(input: String): List<Instruction> {
        return input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { buildInstruction(it) }
    }

    private fun buildInstruction(line: String): Instruction {
        val pattern = """(\w+) (\w+) (-?\d+) if (\w+) ([><=!]+) (-?\d+).*""".toRegex()
        pattern.matchEntire(line)?.let { match ->
            val (register, operationType, value, conditionRegister, comparision, conditionValue) = match.destructured
            return Instruction(
                    register,
                    if (operationType == "inc") { x -> x + value.toInt() } else { x -> x - value.toInt() },
                    conditionRegister,
                    comparision,
                    conditionValue.toInt()
            )
        }
        throw RuntimeException(line)
    }

    private fun buildCondition(registers: Map<String, Int>, register: String, operator: String, conditionValue: Int): Boolean {
        return when(operator) {
            "==" -> registers.getValue(register) == conditionValue
            "!=" -> registers.getValue(register) != conditionValue
            "<" -> registers.getValue(register) < conditionValue
            ">" -> registers.getValue(register) > conditionValue
            ">=" -> registers.getValue(register) >= conditionValue
            else -> registers.getValue(register) <= conditionValue
        }
    }
}

data class Instruction(
        val register: String,
        val operation: (Int) -> Int,
        val conditionRegister: String,
        val comparator: String,
        val conditionValue: Int
)
