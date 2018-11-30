package com.github.aoc2017

class Day21 {
    val start = """
            .#.
            ..#
            ###
            """.split("\n").map { it.trim() }.filter { it != "" }

    fun first(input: String, repeat: Int): Int {
        val enhancementsBook = input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { toPatterns(it) }
        var current = start
        repeat(repeat) { iteration ->
            if (current.size % 2 == 0) {
                current = findReplacements(current, enhancementsBook, 2)
            } else {
                current = findReplacements(current, enhancementsBook, 3)
            }
        }
        return current.sumBy { it.count { it == '#' } }
    }

    private fun findReplacements(current: List<String>, enhancementsBook: List<Pair<Set<Pattern>, Pattern>>, blockSize: Int): MutableList<String> {
        val replacements = mutableListOf<String>()
        repeat(current.size / blockSize) { x ->
            repeat(current.size / blockSize) { y ->
                val patternToCheck = mutableListOf<String>()
                repeat(blockSize) { a ->
                    val patternLine = current[x * blockSize + a].substring(y*blockSize, y*blockSize+blockSize)
                    patternToCheck.add(patternLine)
                }
                val replacement = enhancementsBook.first { it.first.contains(patternToCheck) }.second
                replacement.withIndex().forEach { line ->
                    val lineIdx = x * (blockSize + 1) + line.index
                    val existing = if (replacements.size > lineIdx) replacements.removeAt(lineIdx) else ""
                    replacements.add(lineIdx, existing + line.value)
                }
            }
        }
        return replacements
    }

    private fun toPatterns(line: String): Pair<Set<Pattern>, Pattern> {
        val inAndOut = line.split("=>")
                .map { it.trim() }
                .map { it.split("/") }
        return transformations(inAndOut[0]) to inAndOut[1]
    }

    private fun transformations(input: Pattern): Set<Pattern> {
        val flipped1 = input.map { it.reversed() }
        val flipped2 = input.reversed()
        val flipped3 = input.reversed().map { it.reversed() }
        val rotated = rotate(input)
        val rotated1 = rotate(flipped1)
        val rotated2 = rotate(flipped2)
        val rotated3 = rotate(flipped3)
        return listOf(rotated, rotated1, rotated2, rotated3).flatMap { it }.toSet()
    }

    private fun rotate(input: Pattern): List<Pattern> {
        val array = input.toTypedArray().map { it.toCharArray() }.toTypedArray()
        val rotated1 = transpose(array).reversed().map { String(it) }
        val rotated2 = transpose(array).map { it.reversed().toCharArray() }.map { String(it) }
        val rotated3 = transpose(array).toList().map { String(it) }
        return listOf(input, rotated1, rotated2, rotated3)
    }

    private fun transpose(input: Array<CharArray>): Array<CharArray> {
        val transpose = Array(input.size) { CharArray(input.size) { ' ' } }
        repeat(input.size) { x->
            repeat(input[x].size) { y->
                transpose[x][y] = input[y][x]
            }
        }
        return transpose
    }
}

typealias Pattern = List<String>
