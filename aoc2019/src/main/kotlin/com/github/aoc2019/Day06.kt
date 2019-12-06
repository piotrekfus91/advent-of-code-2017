package com.github.aoc2019

class Day06 {
    fun first(input: String): Int {
        val orbits = readInput(input)
        val planetOrbitees = mutableMapOf<String, MutableList<String>>()
        orbits.forEach { orbit ->
            val orbitees = planetOrbitees.getOrDefault(orbit.center, mutableListOf())
            orbitees.add(orbit.orbitee)
            planetOrbitees[orbit.center] = orbitees
        }
        return deepCount(planetOrbitees, "COM", 0)
    }

    private fun deepCount(planetsOrbitees: MutableMap<String, MutableList<String>>, planet: String, deep: Int): Int {
        val orbitees = planetsOrbitees[planet]
        return if (orbitees != null) {
            orbitees.size + orbitees.sumBy { deep + deepCount(planetsOrbitees, it, deep + 1) }
        } else {
            0
        }
    }

    fun second(input: String): Int {
        val orbits = readInput(input)
        val orbitsMap = orbits.map { it.orbitee to it.center }.toMap()
        val yourOrbits = findOrbits(orbitsMap, "YOU")
        val sanOrbits = findOrbits(orbitsMap, "SAN")
        val onlyYourOrbits = yourOrbits.filter { !sanOrbits.contains(it) }
        val onlySanOrbits = sanOrbits.filter { !yourOrbits.contains(it) }
        return onlyYourOrbits.size + onlySanOrbits.size
    }

    private fun findOrbits(orbits: Map<String, String>, planet: String): List<String> =
        if (planet == "COM") {
            listOf()
        } else {
            findOrbits(orbits, orbits.getValue(planet)) + orbits.getValue(planet)
        }

    private fun readInput(input: String): List<Orbit> {
        return input.split("\n")
            .filter { it != "" }
            .map { it.split(")") }
            .map { Orbit(it[0], it[1]) }
    }

    data class Orbit(val center: String, val orbitee: String)
}
