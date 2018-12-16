package com.github.aoc2018

class Day15 {
    private val hp = 200

    fun first(input: String): Int {
        val gameMap = loadMap(input)
        val units = gameMap.findUnits(elfPower = 3, goblinPower = 3)
        var round = 0
        while (bothRacesExist(units)) {
            units.sort()
            var unitId = 0
            while (unitId < units.size) {
                val unit = units[unitId]
                unitId = moveOrAttack(unit, units, unitId, gameMap)
                if (onlyOneRaceExists(units)) {
                    return countResult(unitId, units, round)
                }
                unitId++
            }
            round++
        }
        return -1
    }

    fun second(input: String): Int {
        var elfPower = 3
        while (true) {
            elfPower++
            val gameMap = loadMap(input)
            val units = gameMap.findUnits(elfPower, goblinPower = 3)
            val startingElvesNumber = units.count { it is Elf }
            var round = 0
            while (bothRacesExist(units)) {
                units.sort()
                var unitId = 0
                while (unitId < units.size) {
                    val unit = units[unitId]
                    unitId = moveOrAttack(unit, units, unitId, gameMap)
                    if (onlyOneRaceExists(units)) {
                        if (eachElfSurvives(units, startingElvesNumber)) {
                            return countResult(unitId, units, round)
                        } else {
                            break
                        }
                    }
                    unitId++
                }
                round++
            }
        }
    }

    private fun moveOrAttack(unit: Unit, units: MutableList<Unit>, unitId: Int, gameMap: GameMap): Int {
        var unitId1 = unitId
        val attackedUnit = findUnitToAttack(unit, units)
        unitId1 = if (attackedUnit != null) {
            attack(unit, attackedUnit, units, gameMap, unitId1)
        } else {
            moveAndAttack(unit, units, gameMap, unitId1)
        }
        return unitId1
    }

    private fun eachElfSurvives(units: MutableList<Unit>, startingElvesNumber: Int) =
        units.all { it is Elf } && units.size == startingElvesNumber

    private fun onlyOneRaceExists(units: MutableList<Unit>) =
        units.distinctBy { it.letter }.size == 1

    private fun bothRacesExist(units: MutableList<Unit>) =
        units.distinctBy { it.letter }.size == 2

    private fun moveAndAttack(unit: Unit, units: MutableList<Unit>, gameMap: GameMap, unitId: Int): Int {
        val distances = findDistances(unit, gameMap)
        val attackPositions = findAttackPositions(unit, units, gameMap)
        if (attackPositions.isNotEmpty()) {
            val bestAttackPosition = findBestAttackPosition(distances, attackPositions)
            if (bestAttackPosition != null) {
                val move = findMove(unit, gameMap, bestAttackPosition)
                if (move != null) {
                    gameMap[unit.position.y][unit.position.x] = '.'
                    unit.position = move
                    gameMap[move.y][move.x] = unit.letter
                    val attackedUnit = findUnitToAttack(unit, units)
                    if (attackedUnit != null) {
                        return attack(unit, attackedUnit, units, gameMap, unitId)
                    }
                }
            }
        }
        return unitId
    }

    private fun countResult(unitId: Int, units: MutableList<Unit>, round: Int): Int {
        val efficientRounds = if (unitId == units.size - 1) round + 1 else round
        val sumLeft = units.map { it.hp }.sum()
        return efficientRounds * sumLeft
    }

    private fun attack(attackingUnit: Unit, attackedUnit: Unit, units: MutableList<Unit>, gameMap: GameMap, unitId: Int): Int {
        attackedUnit.hp -= attackingUnit.power
        if (attackedUnit.hp <= 0) {
            val indexOfAttackedUnit = units.indexOf(attackedUnit)
            units.remove(attackedUnit)
            gameMap[attackedUnit.position.y][attackedUnit.position.x] = '.'
            if (indexOfAttackedUnit < unitId) {
                return unitId-1
            }
        }
        return unitId
    }

    private fun findUnitToAttack(unit: Day15.Unit, units: MutableList<Day15.Unit>): Unit? =
        units.filter { it.javaClass != unit.javaClass }
            .filter { unit.canAttack(it) }
            .sortedBy { it.hp }
            .firstOrNull()

    private fun findMove(unit: Unit, gameMap: GameMap, attackPosition: Point): Point? {
        val allVertices = mutableListOf<Vertex>()
        val verticesToCheck = mutableListOf(Vertex(null, unit.position, 0))
        while (verticesToCheck.isNotEmpty()) {
            verticesToCheck.sort()
            val vertex = verticesToCheck.removeAt(0)
            val children = findMovesFromPoint(vertex.point, gameMap).map { Vertex(vertex, it, vertex.distance + 1) }
            children.forEach { child ->
                val existingVertex = allVertices.find { it.point == child.point }
                if (existingVertex == null) {
                    allVertices.add(child)
                    verticesToCheck.add(child)
                } else {
                    if (existingVertex.distance > child.distance) {
                        existingVertex.distance = child.distance
                        verticesToCheck.add(child)
                    }
                }
                val attackCandidate = allVertices.find { it.point == child.point }!!
                if (attackCandidate.point == attackPosition) {
                    return findFirstStep(attackCandidate)
                }
            }
        }
        return null
    }

    private fun findFirstStep(attackCandidate: Vertex): Point {
        var lookingForParent = attackCandidate
        while (lookingForParent.parent?.parent != null) {
            lookingForParent = lookingForParent.parent!!
        }
        return lookingForParent.point
    }

    private fun findDistances(unit: Unit, gameMap: GameMap): Array<Array<Int>> {
        val distances = Array(gameMap.size) { Array(gameMap[0].size) { Int.MAX_VALUE } }
        distances[unit.position.y][unit.position.x] = 0
        val toCheck = mutableListOf(unit.position)
        while (toCheck.isNotEmpty()) {
            val moveFrom = toCheck.sortedBy { distances[it.y][it.x] }.first()
            val movesToCheck = findMovesFromPoint(moveFrom, gameMap)
            movesToCheck.forEach { moveTo ->
                if (distances[moveFrom.y][moveFrom.x] + 1 < distances[moveTo.y][moveTo.x]) {
                    toCheck.add(moveTo)
                    distances[moveTo.y][moveTo.x] = distances[moveFrom.y][moveFrom.x] + 1
                }
            }
            toCheck.removeAt(0)
        }
        return distances
    }

    private fun findAttackPositions(unit: Unit, units: List<Unit>, gameMap: GameMap): List<Point> =
        units.filter { it.javaClass != unit.javaClass }.flatMap { findMovesFromPoint(it.position, gameMap) }

    private fun findBestAttackPosition(distances: Array<Array<Int>>, attackPositions: List<Day15.Point>): Point? {
        val attacksToDistances = attackPositions.map { it to distances[it.y][it.x] }
        val minDistance = attacksToDistances.minBy { it.second }!!.second
        return if (minDistance != Int.MAX_VALUE) {
            attacksToDistances.filter { it.second == minDistance }.sortedBy { it.first }.first().first
        } else {
            null
        }
    }

    private fun findMovesFromPoint(point: Point, gameMap: GameMap): List<Point> =
        listOf(
            point.y to point.x - 1,
            point.y to point.x + 1,
            point.y - 1 to point.x,
            point.y + 1 to point.x
        ).filter { gameMap[it.first][it.second] == '.' }.map { Point(it.second, it.first) }

    private fun loadMap(input: String): GameMap =
        input.split("\n")
            .filter { it.trim() != "" }
            .map { it.toCharArray() }


    data class Point(var x: Int, var y: Int) : Comparable<Point> {
        override fun compareTo(other: Point): Int =
            if (y != other.y) {
                y - other.y
            } else {
                x - other.x
            }
    }
    abstract class Unit : Comparable<Unit> {
        abstract var position: Point
        abstract var hp: Int
        abstract val letter: Char
        abstract val power: Int

        override fun compareTo(other: Unit): Int = position.compareTo(other.position)

        fun canAttack(other: Day15.Unit): Boolean =
            (position.x == other.position.x && Math.abs(position.y - other.position.y) == 1
                || Math.abs(position.x - other.position.x) == 1 && position.y == other.position.y)
    }
    data class Elf(override var position: Point, override var hp: Int, override val power: Int) : Unit() {
        override val letter: Char = 'E'
    }
    data class Goblin(override var position: Point, override var hp: Int, override val power: Int) : Unit() {
        override val letter: Char = 'G'
    }

    data class Vertex(val parent: Vertex?, val point: Point, var distance: Int) : Comparable<Vertex> {
        override fun compareTo(other: Vertex): Int =
            if (distance != other.distance) {
                distance - other.distance
            } else {
                point.compareTo(other.point)
            }
    }

    private fun GameMap.findUnits(elfPower: Int, goblinPower: Int): MutableList<Unit> =
        (0 until size).flatMap { y ->
            (0 until this[y].size).map { x ->
                when {
                    this[y][x] == 'E' -> Elf(Point(x, y), hp, elfPower)
                    this[y][x] == 'G' -> Goblin(Point(x, y), hp, goblinPower)
                    else -> null
                }
            }
        }.filterNotNull().toMutableList()

}

typealias GameMap = List<CharArray>
