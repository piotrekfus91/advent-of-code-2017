package com.github.aoc2018

class Day14 {
    fun first(input: Int): String {
        val recipes = mutableListOf<Short>(3, 7)
        val elvesPositions = mutableListOf(0, 1)
        var isMulti = false
        while (recipes.size < input + 10) {
            val values = elvesPositions.map { recipes[it] }
            val nextRecipe = values.sum().toShort()
            if (nextRecipe >= 10) {
                isMulti = true
                recipes.add((nextRecipe / 10).toShort())
                recipes.add((nextRecipe % 10).toShort())
            } else {
                isMulti = false
                recipes.add(nextRecipe)
            }
            values.withIndex().forEach {
                elvesPositions[it.index] = (elvesPositions[it.index] + 1 + it.value) % recipes.size
            }
        }
        return if (isMulti) {
            recipes.subList(recipes.size - 11, recipes.size - 1).joinToString("")
        } else {
            recipes.subList(recipes.size - 10, recipes.size).joinToString("")
        }
    }

    fun second(input: Int): Int {
        val recipes = mutableListOf<Short>(3, 7)
        val elvesPositions = mutableListOf(0, 1)
        while (true) {
            val values = elvesPositions.map { recipes[it] }
            val nextRecipe = values.sum().toShort()
            if (nextRecipe >= 10) {
                recipes.add((nextRecipe / 10).toShort())
                recipes.add((nextRecipe % 10).toShort())
            } else {
                recipes.add(nextRecipe)
            }
            values.withIndex().forEach {
                elvesPositions[it.index] = (elvesPositions[it.index] + 1 + it.value) % recipes.size
            }
            if (recipes.size > 10 && recipes.subList(recipes.size - 10, recipes.size).joinToString("").contains(input.toString())) {
                return recipes.joinToString("").indexOf(input.toString())
            }
        }
    }
}
