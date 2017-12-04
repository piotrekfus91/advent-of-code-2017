package org.bitbucket.adventofcode2017

class Day04 {
    fun first(input: String): Int {
        return input.split("\n")
                .filter { it != "" }
                .filter { !containsDuplicates(it) }
                .count()
    }

    internal fun containsDuplicates(line: String): Boolean {
        val split = line.trim().split("\\s+".toRegex())
        return split.size != HashSet(split).size
    }


    fun second(input: String): Int {
        return input.split("\n")
                .filter { it != "" }
                .filter { !containsAnagrams(it) }
                .count()
    }

    internal fun containsAnagrams(line: String): Boolean {
        val split = line.trim().split("\\s+".toRegex())
        return split.size != HashSet(split.map { it.toByteArray().sorted() }).size
    }
}
