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

fun <T1, T2> Collection<T1>.combine(other: Iterable<T2>): List<Pair<T1, T2>> {
    return combine(other, {thisItem: T1, otherItem: T2 -> Pair(thisItem, otherItem) })
}

fun <T1, T2, R> Collection<T1>.combine(other: Iterable<T2>, transformer: (thisItem: T1, otherItem:T2) -> R): List<R> {
    return this.flatMap { thisItem -> other.map { otherItem -> transformer(thisItem, otherItem) }}
}
