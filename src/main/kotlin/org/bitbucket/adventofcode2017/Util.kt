package org.bitbucket.adventofcode2017

fun <T> List<T>.sliding(windowSize: Int): List<List<T>> {
    return this.dropLast(windowSize - 1).mapIndexed { i, _ -> this.subList(i, i + windowSize) }
}

fun <T> Array<T>.reverseRangeCyclic(from: Int, to: Int) {
    val size = (to - from + this.size) % this.size
    val list: List<T> = List(size) {
        this[(from + it) % this.size]
    }.reversed()
    repeat(size) {
        this[(from + it) % this.size] = list[it]
    }
}
