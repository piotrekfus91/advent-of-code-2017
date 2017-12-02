package org.bitbucket.adventofcode2017

fun <T> List<T>.sliding(windowSize: Int): List<List<T>> {
    return this.dropLast(windowSize - 1).mapIndexed { i, _ -> this.subList(i, i + windowSize) }
}