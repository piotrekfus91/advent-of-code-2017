package com.github.aoc2019

import kotlin.math.abs

class Day03 {
    fun first(input: String): Int {
        val wires = readInput(input)

        val map = mutableMapOf<Point, Char>()
        val steps = mutableMapOf<Point, Int>()

        wires.withIndex().forEach {
            val marker = it.index.toChar() + '1'.toInt()
            var currentPoint = Point(0, 0)
            it.value.forEach { instruction ->
                when (instruction.direction) {
                    'U' -> currentPoint = markWire(instruction, currentPoint, map, marker, { y -> y + 1 }, { x -> x })
                    'D' -> currentPoint = markWire(instruction, currentPoint, map, marker, { y -> y - 1 }, { x -> x })
                    'L' -> currentPoint = markWire(instruction, currentPoint, map, marker, { y -> y }, { x -> x - 1 })
                    'R' -> currentPoint = markWire(instruction, currentPoint, map, marker, { y -> y }, { x -> x + 1 })
                }
            }
        }

        return map.filter { it.value == 'X' }.keys.map { abs(it.x) + abs(it.y) }.min()!!
    }

    private fun markWire(instruction: Instruction, startPoint: Point, map: MutableMap<Point, Char>, marker: Char, modifyY: (Int) -> Int, modifyX: (Int) -> Int): Point {
        var y = startPoint.y
        var x = startPoint.x
        repeat(instruction.distance) {
            y = modifyY(y)
            x = modifyX(x)
            val point = Point(x, y)
            val currentState = map[point]
            if (currentState == null) {
                map[point] = marker
            } else if (currentState != marker) {
                map[point] = 'X'
            }
        }
        return Point(x, y)
    }

    fun second(input: String): Int {
        val wires = readInput(input)

        val map = mutableMapOf<Point, Char>()
        val steps = mutableListOf<MutableMap<Point, Int>>()

        wires.withIndex().forEach {
            val marker = it.index.toChar() + '1'.toInt()
            var currentPoint = Point(0, 0)
            var currentStep = 0
            val currentSteps = mutableMapOf<Point, Int>()
            steps.add(currentSteps)
            it.value.forEach { instruction ->
                when (instruction.direction) {
                    'U' -> currentPoint = markStep(instruction, currentPoint, map, currentStep, currentSteps, marker, { y -> y + 1 }, { x -> x })
                    'D' -> currentPoint = markStep(instruction, currentPoint, map, currentStep, currentSteps, marker, { y -> y - 1 }, { x -> x })
                    'L' -> currentPoint = markStep(instruction, currentPoint, map, currentStep, currentSteps, marker, { y -> y }, { x -> x - 1 })
                    'R' -> currentPoint = markStep(instruction, currentPoint, map, currentStep, currentSteps, marker, { y -> y }, { x -> x + 1 })
                }
                currentStep += instruction.distance
            }
        }

        return map.filter { it.value == 'X' }.keys.map { point -> steps.map { it[point]!! }.sum() }.min()!!
    }

    private fun markStep(instruction: Instruction, startPoint: Point, map: MutableMap<Point, Char>, startStep: Int, steps: MutableMap<Point, Int>, marker: Char, modifyY: (Int) -> Int, modifyX: (Int) -> Int): Point {
        var y = startPoint.y
        var x = startPoint.x
        var step = startStep
        repeat(instruction.distance) {
            step++
            y = modifyY(y)
            x = modifyX(x)
            val point = Point(x, y)
            val currentState = map[point]
            if (currentState == null) {
                map[point] = marker
            } else if (currentState != marker) {
                map[point] = 'X'
            }

            val s = steps[point]
            if (s == null) {
                steps[point] = step
            }
        }
        return Point(x, y)
    }

    private fun readInput(input: String): List<List<Instruction>> {
        return input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.split(",").map { Instruction(it[0], it.drop(1).toInt()) } }
    }

    data class Instruction(val direction: Char, val distance: Int)
    data class Point(val x: Int, val y: Int)
}
