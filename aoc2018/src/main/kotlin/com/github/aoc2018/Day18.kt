package com.github.aoc2018

class Day18 {
    fun first(input: String): Int {
        val map = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toCharArray().toMutableList() }
            .toMutableList()

        var before: MutableList<MutableList<Char>>
        repeat(10) { minute ->
            before = map.copy()
            repeat(before.size) { y ->
                repeat(before[y].size) { x ->
                    when (before[y][x]) {
                        '.' -> map[y][x] = evolveOpen(before, x, y)
                        '|' -> map[y][x] = evolveTree(before, x, y)
                        '#' -> map[y][x] = evolveLumberJack(before, x, y)
                    }
                }
            }
        }
        val lumberyardCount = map.sumBy { it.count { it == '#' } }
        val treeCount = map.sumBy { it.count { it == '|' } }
        return lumberyardCount * treeCount
    }

    fun second(input: String): Int {
        val map = input.split("\n")
            .map { it.trim() }
            .filter { it != "" }
            .map { it.toCharArray().toMutableList() }
            .toMutableList()

        val seen = mutableListOf(map.joinToString { it.joinToString("") + "\n" })
        var before: MutableList<MutableList<Char>>
        repeat(1000) {
            val minute = it + 1
            before = map.copy()
            repeat(before.size) { y ->
                repeat(before[y].size) { x ->
                    when (before[y][x]) {
                        '.' -> map[y][x] = evolveOpen(before, x, y)
                        '|' -> map[y][x] = evolveTree(before, x, y)
                        '#' -> map[y][x] = evolveLumberJack(before, x, y)
                    }
                }
            }
            println(minute)
            debug(map)
            val mapAsString = map.joinToString { it.joinToString("") + "\n" }
            if (seen.contains(mapAsString)) {
                val repeatedFrom = seen.indexOf(mapAsString)
                val repSpan = minute - repeatedFrom
                val inRep = (1000000000 - repeatedFrom) % repSpan
                val resultMap = seen[repeatedFrom + inRep]
                val lumberyardCount = resultMap.count { it == '#' }
                val treeCount = resultMap.count { it == '|' }
                return lumberyardCount * treeCount
            }
            seen.add(mapAsString)
        }
        val lumberyardCount = map.sumBy { it.count { it == '#' } }
        val treeCount = map.sumBy { it.count { it == '|' } }
        return lumberyardCount * treeCount
    }

    fun MutableList<MutableList<Char>>.copy(): MutableList<MutableList<Char>> {
        val result = mutableListOf<MutableList<Char>>()
        this.forEach {
            val r = mutableListOf<Char>()
            r.addAll(it)
            result.add(r)
        }
        return result
    }

    private fun evolveLumberJack(before: MutableList<MutableList<Char>>, centerX: Int, centerY: Int): Char {
        val countLumberyards = (-1 until 2).sumBy { y ->
            (-1 until 2).sumBy { x ->
                if ((x != 0 || y != 0) && centerX + x >= 0 && centerX + x < before[0].size && centerY + y >= 0 && centerY + y < before[0].size && before[centerY + y][centerX + x] == '#') {
                    1
                } else {
                    0
                }
            }
        }
        val countTrees = (-1 until 2).sumBy { y ->
            (-1 until 2).sumBy { x ->
                if ((x != 0 || y != 0) && centerX + x >= 0 && centerX + x < before[0].size && centerY + y >= 0 && centerY + y < before[0].size && before[centerY + y][centerX + x] == '|') {
                    1
                } else {
                    0
                }
            }
        }
        return if (countLumberyards >= 1 && countTrees >= 1) {
            '#'
        } else {
            '.'
        }
    }

    private fun evolveTree(before: MutableList<MutableList<Char>>, centerX: Int, centerY: Int): Char {
        return evolve(centerX, before, centerY, '#', '|')
    }

    private fun evolveOpen(before: MutableList<MutableList<Char>>, centerX: Int, centerY: Int): Char {
        return evolve(centerX, before, centerY, '|', '.')
    }

    private fun evolve(centerX: Int, before: MutableList<MutableList<Char>>, centerY: Int, required: Char, orig: Char): Char {
        val count = (-1 until 2).sumBy { y ->
            (-1 until 2).sumBy { x ->
                if ((x != 0 || y != 0) && centerX + x >= 0 && centerX + x < before[0].size && centerY + y >= 0 && centerY + y < before[0].size && before[centerY + y][centerX + x] == required) {
                    1
                } else {
                    0
                }
            }
        }
        return if (count >= 3) {
            required
        } else {
            orig
        }
    }

    private fun debug(map: MutableList<MutableList<Char>>) {
        repeat(map.size) { y ->
            repeat(map[y].size) { x ->
                print(map[y][x])
            }
            println()
        }
    }
}
