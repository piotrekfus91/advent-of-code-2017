package com.github.aoc2017

class Day20 {
    fun first(input: String): Int {
        val particles = loadParticles(input)
        return particles.minBy { it.a.x * it.a.x + it.a.y * it.a.y + it.a.z * it.a.z }!!.idx
    }

    fun second(input: String): Int {
        val particles = loadParticles(input)
        val combinations = particles.combine(particles)
                .filter { it.first != it.second }
                .map { it.toList().sorted() }
                .map { it[0] to it[1] }
                .distinct()

        val collisions = combinations.map { collisionTime(it) to it }
                .filter { it.first >= 0 }
                .distinctBy { it.second.toList().sorted() }
                .sortedBy { it.first }

        val collisionsBySecond = collisions.groupBy { it.first }
                .map { it.key to it.value.map { it.second } }
                .sortedBy { it.first }

        val allCollidedParticles = mutableSetOf<Particle>()
        collisionsBySecond.forEach { collisionBySecond ->
            val collidedParticlesInThisSecond = mutableSetOf<Particle>()
            collisionBySecond.second.forEach {
                if(!allCollidedParticles.contains(it.first) && !allCollidedParticles.contains(it.second)) {
                    collidedParticlesInThisSecond.add(it.first)
                    collidedParticlesInThisSecond.add(it.second)
                }
            }
            allCollidedParticles.addAll(collidedParticlesInThisSecond)
        }

        return particles.size - allCollidedParticles.size
    }

    private fun collisionTime(pair: Pair<Particle, Particle>): Int {
        val timeOfCollision = listOf(timeForX(pair), timeForY(pair), timeForZ(pair)).distinct()
        return if (timeOfCollision.size == 1 && timeOfCollision.all { it >= 0 }) timeOfCollision[0] else -1
    }

    private fun timeForX(pair: Pair<Particle, Particle>): Int {
        val a = a(pair.first.a.x, pair.second.a.x)
        val b = b(pair.first.a.x, pair.second.a.x, pair.first.v.x, pair.second.v.x)
        val c = c(pair.first.p.x, pair.second.p.x)
        return solveEquation(a, b, c)
    }

    private fun timeForY(pair: Pair<Particle, Particle>): Int {
        val a = a(pair.first.a.y, pair.second.a.y)
        val b = b(pair.first.a.y, pair.second.a.y, pair.first.v.y, pair.second.v.y)
        val c = c(pair.first.p.y, pair.second.p.y)
        return solveEquation(a, b, c)
    }

    private fun timeForZ(pair: Pair<Particle, Particle>): Int {
        val a = a(pair.first.a.z, pair.second.a.z)
        val b = b(pair.first.a.z, pair.second.a.z, pair.first.v.z, pair.second.v.z)
        val c = c(pair.first.p.z, pair.second.p.z)
        return solveEquation(a, b, c)
    }

    private fun solveEquation(a: Int, b: Int, c: Int): Int {
        return if (a == 0) {
            linear(b, c)
        } else {
            square(a, b, c)
        }
    }

    private fun linear(b: Int, c: Int): Int {
        val t = -1.0 * c / b
        return if (t.isFinite() && t.toInt().toDouble() == t && t >= 0) t.toInt() else -1
    }

    private fun square(a: Int, b: Int, c: Int): Int {
        val delta = delta(a, b, c)
        val t1 = t1(delta, a, b)
        val t2 = t2(delta, a, b)
        val t = listOf(t1, t2).filter { it.isFinite() && it.toInt().toDouble() == it }.filter { it >= 0 }.min()
        return t?.toInt() ?: -1
    }

    private fun a(a1: Int, a2: Int): Int = a1 - a2
    private fun b(a1: Int, a2: Int, v1: Int, v2: Int): Int = 2 * (v1 - v2) + (a1 - a2)
    private fun c(p1: Int, p2: Int): Int = 2 * (p1 - p2)

    private fun delta(a: Int, b: Int, c: Int): Double = (b * b - 4 * a * c).toDouble()
    private fun t1(delta: Double, a: Int, b: Int): Double = (-1 * b - Math.sqrt(delta)) / (2 * a)
    private fun t2(delta: Double, a: Int, b: Int): Double = (-1 * b + Math.sqrt(delta)) / (2 * a)

    private fun loadParticles(input: String): List<Particle> {
        return input.split("\n".toRegex())
                .map { it.trim() }
                .filterNot { it == "" }
                .withIndex()
                .map { toParticle(it.index, it.value) }
    }

    private fun toParticle(idx: Int, line: String): Particle {
        val triples = line.split(">,?".toRegex())
                .map { it.trim() }
                .filter { it != "" }
                .map { it.substring("x=<".length) }
                .map { it.split(",") }
                .map { it.map { it.trim().toInt() } }
                .map { Triple(it[0], it[1], it[2]) }
        return Particle(idx, triples[0], triples[1], triples[2])
    }
}

data class Particle(val idx: Int, val p: Triple, val v: Triple, val a: Triple) : Comparable<Particle> {
    override fun compareTo(other: Particle): Int = idx - other.idx
}

data class Triple(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Triple): Int = x - other.x + y - other.y + z - other.z
}
