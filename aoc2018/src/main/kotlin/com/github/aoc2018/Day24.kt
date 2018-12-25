package com.github.aoc2018

class Day24 {
    fun first(immuneSystem: MutableList<Group>, infection: MutableList<Group>): Int {
        return performCombat(immuneSystem, infection)
    }

    fun second(immuneSystem: MutableList<Group>, infection: MutableList<Group>): Int {
        immuneSystem.forEach { it.attack += 29 }
        return performCombat(immuneSystem, infection)
    }

    private fun performCombat(immuneSystem: MutableList<Group>, infection: MutableList<Group>): Int {
        while (true) {
            val toSelectionPhase = listOf(immuneSystem, infection)
                .flatten()
                .sortedWith(ToSelectionPhaseComparator)
            val attacks = mutableListOf<Pair<Group, Group>>()
            toSelectionPhase.forEach { group ->
                val candidatesByDamage = toSelectionPhase
                    .filter { group.type != it.type }
                    .filter { !it.immunities.contains(group.attackType) }
                    .filterNot { attacks.map { it.second }.contains(it) }
                    .groupBy { group.damageTo(it) }
                    .toList()
                    .sortedBy { it.first }
                if (candidatesByDamage.isNotEmpty()) {
                    if (candidatesByDamage.last().second.size == 1) {
                        attacks.add(group to candidatesByDamage.last().second.first())
                    } else {
                        val candidatesByEffectivePower = candidatesByDamage.last().second
                            .groupBy { it.effectivePower() }
                            .toList()
                            .sortedBy { it.first }
                        if (candidatesByEffectivePower.isNotEmpty()) {
                            if (candidatesByEffectivePower.last().second.size == 1) {
                                attacks.add(group to candidatesByEffectivePower.last().second.first())
                            } else {
                                val candidatesByInitiative = candidatesByEffectivePower.last().second
                                    .sortedBy { it.initiative }
                                    .reversed()
                                if (candidatesByInitiative.isNotEmpty()) {
                                    attacks.add(group to candidatesByInitiative.first())
                                }
                            }
                        }
                    }
                }
            }
            attacks.sortBy { -it.first.initiative }
            attacks.forEach { (attacker, defender) ->
                if (attacker.isAnyoneAlive()) {
                    attacker.attack(defender)
                }
            }
            immuneSystem.removeIf { !it.isAnyoneAlive() }
            infection.removeIf { !it.isAnyoneAlive() }
            if (immuneSystem.all { !it.isAnyoneAlive() } || infection.all { !it.isAnyoneAlive() }) {
                return infection.sumBy { it.units } + immuneSystem.sumBy { it.units }
            }
        }
    }

    data class Group(
        val id: Int,
        val type: GroupType,
        var units: Int,
        val hpPerUnit: Int,
        val weaknesses: List<String>,
        val immunities: List<String>,
        var attack: Int,
        val attackType: String,
        val initiative: Int
    ) {
        fun effectivePower() = units * attack
        fun isAnyoneAlive() = units > 0
        private fun kill(units: Int) {
            this.units -= units
        }
        fun damageTo(group: Group): Int = when (attackType) {
            in group.immunities -> 0
            in group.weaknesses -> effectivePower() * 2
            else -> effectivePower()
        }
        fun attack(group: Group) {
            val damage = damageTo(group)
            val killedUnits = damage / group.hpPerUnit
            group.kill(killedUnits)
        }
    }

    enum class GroupType {
        INFECTION,
        IMMUNE_SYSTEM;
    }

    class ToSelectionPhaseComparator {
        companion object : Comparator<Group> {
            override fun compare(o1: Group, o2: Group): Int = when {
                o1.effectivePower() != o2.effectivePower() -> o2.effectivePower() - o1.effectivePower()
                else -> o2.initiative - o1.initiative
            }
        }
    }
}
