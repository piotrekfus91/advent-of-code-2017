package org.bitbucket.adventofcode2017

class Day16 {
    private val exchangeRegex = Regex("x(\\d+)/(\\d+)")
    private val partnerRegex = Regex("p([a-z])/([a-z])")

    fun first(letters: String, input: String): String {
        val actions = buildActions(input)
        val chars = letters.toCharArray()
        doActions(chars, actions)
        return String(chars)
    }

    private fun doActions(letters: CharArray, actions: MutableList<Action>) {
        actions.forEach {
            it.perform(letters)
        }
    }

    fun second(letters: String, input: String): String {
        val actions = buildActions(input)
        val chars = letters.toCharArray()
        val times = 1000000000
        val visited = mutableListOf<String>()
        repeat(times) {
            val s = String(chars)
            if (visited.contains(s)) {
                return visited[times % visited.size]
            }
            visited.add(s)
            doActions(chars, actions)
        }
        return String(chars)
    }

    private fun buildActions(input: String): MutableList<Action> {
        val actions = mutableListOf<Action>()
        input.split("\\s*,\\s*".toRegex()).forEach {
            actions.add(when {
                it.startsWith("s") -> spin(it)
                it.startsWith("x") -> exchange(it)
                it.startsWith("p") -> partner(it)
                else -> throw RuntimeException(it)
            })
        }
        return actions
    }

    private fun spin(arg: String): Action {
        val num = arg.substring(1).toInt()
        return Spin(num)
    }

    private fun exchange(arg: String): Action {
        exchangeRegex.matchEntire(arg)?.let { match ->
            val (posA, posB) = match.destructured
            return Exchange(posA.toInt(), posB.toInt())
        }
        error(arg)
    }

    private fun partner(arg: String): Action {
        partnerRegex.matchEntire(arg)?.let { match ->
            val (charA, charB) = match.destructured
            return Partner(charA[0], charB[0])
        }
        error(arg)
    }

    private fun error(arg: String): Nothing {
        throw RuntimeException(arg)
    }
}

interface Action {
    fun perform(letters: CharArray)
    fun swap(letters: CharArray, posA: Int, posB: Int) {
        val charA = letters[posA]
        val charB = letters[posB]
        letters[posA] = charB
        letters[posB] = charA
    }
}

class Spin(private val num: Int) : Action {
    override fun perform(letters: CharArray) {
        val tail = letters.copyOfRange(letters.size - num, letters.size)
        val head = letters.copyOfRange(0, letters.size - num)
        repeat(tail.size) { i ->
            letters[i] = tail[i]
        }
        repeat(head.size) { i ->
            letters[tail.size + i] = head[i]
        }
    }
}

class Exchange(private val posA: Int, private val posB: Int) : Action {
    override fun perform(letters: CharArray) {
        swap(letters, posA, posB)
    }
}

class Partner(private val charA: Char, private val charB: Char) : Action {
    override fun perform(letters: CharArray) {
        val posA = letters.indexOf(charA)
        val posB = letters.indexOf(charB)
        swap(letters, posA, posB)
    }
}