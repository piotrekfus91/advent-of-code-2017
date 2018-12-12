package com.github.aoc2018

class Day12 {
    fun first(input: String, initialState: String): Int {
        val patterns = loadPatterns(input)
        var currentState = initialState
        var generation = 0L
        var shift = 0
        while (generation < 20) {
            generation++
            val evolved = evolve(patterns, currentState)
            currentState = evolved.state
            shift -= evolved.leftShift
        }
        return currentState
            .withIndex()
            .filter { it.value == '#' }
            .sumBy { it.index + shift }
    }

    fun second(input: String, initialState: String, limit: Long): Long {
        val patterns = loadPatterns(input)
        var currentState = initialState
        var generation = 0L
        var shift = 0
        val states = mutableListOf<State>()
        states.add(State(currentState, 0))
        while (generation < 1000) {
            generation++
            val evolved = evolve(patterns, currentState)
            currentState = evolved.state
            shift -= evolved.leftShift
            if (states.contains(evolved)) {
                states.add(evolved)
                break
            } else {
                states.add(evolved)
            }
        }

        val plants = currentState.withIndex().filter { it.value == '#' }
        val lastSum = plants.sumBy { it.index + shift }
        val diffsTimesMath = (limit - generation) * plants.size * states.last().leftShift
        return lastSum - diffsTimesMath
    }

    private fun loadPatterns(input: String): List<Pattern> {
        return input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toPattern() }
            .filter { it.replacement != '.' }
    }

    private fun evolve(patterns: List<Pattern>, currentState: String): State {
        var i = 0
        val nextState = mutableListOf<Char>()
        val toMatch = "....${currentState}...."
        while (i < toMatch.length - 4) {
            val part = toMatch.substring(i, i + 5)
            if (patterns.any { it.pattern == part }) {
                nextState.add('#')
            } else {
                nextState.add('.')
            }
            i++
        }
        val nextStateString = nextState.joinToString("")
        val firstPlant = nextStateString.indexOf('#')
        val lastPlant = nextStateString.lastIndexOf('#') + 1
        return State(nextStateString.substring(firstPlant, lastPlant), 2-firstPlant)
    }

    data class Pattern(val pattern: String, val replacement: Char)

    data class State(val state: String, val leftShift: Int)

    private fun String.toPattern(): Pattern {
        val split = this.split(" => ")
        return Pattern(split[0], split[1][0])
    }
}
