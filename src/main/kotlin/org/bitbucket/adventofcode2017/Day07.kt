package org.bitbucket.adventofcode2017

class Day07 {
    fun first(input: String): String {
        val programs = buildPrograms(input)
        var sampleProgram = programs.getValue(programs.keys.first())
        while(true) {
            val filter = programs.values.firstOrNull { it.children.contains(sampleProgram.name) } ?: break
            sampleProgram = filter
        }
        return sampleProgram.name
    }

    fun second(input: String): Int {
        val programs = buildPrograms(input)
        return try {
            checkWeights(programs, programs.getValue(first(input)))
        } catch (e: MinFoundException) {
            e.x
        }
    }

    private fun checkWeights(programs: Map<String, Program>, program: Program): Int {
        if (program.children.isEmpty()) {
            return program.weight
        }
        val map = program.children.map { checkWeights(programs, programs.getValue(it) ) }
                .groupBy { it }
                .map { it.key to it.value.size }
        if (map.size > 1) {
            val valuesOrderedByOccurences = map.sortedBy { it.second }.map { it.first }
            val children = program.children.map { programs.getValue(it) }
            val programWithWrongWeight = children.first { checkWeights(programs, it) == valuesOrderedByOccurences.first() }
            throw MinFoundException(valuesOrderedByOccurences.last() - valuesOrderedByOccurences.first() + programWithWrongWeight.weight)
        }
        return program.weight + map.sumBy { it.first * it.second }
    }

    private fun buildPrograms(input: String): Map<String, Program> {
        return input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { buildProgram(it) }
                .map { it.name to it }
                .toMap()
    }

    private fun buildProgram(line: String): Program {
        val splitByProgramList = line.split("->").map { it.trim() }
        val children = if (splitByProgramList.size > 1) splitByProgramList[1].split(",").map { it.trim() } else listOf()
        val pattern = Regex("([a-z]+) \\((\\d+)\\)")
        val results = pattern.matchEntire(splitByProgramList[0])!!.groupValues
        return Program(results[1], results[2].toInt(), children)
    }
}

data class Program(val name: String, val weight: Int, val children: List<String>)
class MinFoundException(val x: Int) : Exception()
