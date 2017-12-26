package org.bitbucket.adventofcode2017

import java.util.*

class Day25 {
    fun first(input: String, steps: Int): Int {
        val states = readStates(input)
        val tape = LinkedList<Byte>()
        var idx = 0
        var currentState = states.getValue('A')
        repeat(steps) {
            val action = currentState.getAction(tape, idx)
            action.write(tape, idx)
            if(idx < 0) idx = 0
            idx = action.nextIdx(idx)
            currentState = action.nextState(states)
        }
        return tape.count { it == 1.toByte() }
    }

    private fun readStates(input: String): Map<Char, State> {
        val statesStr = input.split("\n\n")
        return statesStr.map { toState(it) }
                .map { it.name to it }
                .toMap()
    }

    private fun toState(stateStr: String): State {
        val pattern = """
In state (\w):
  If the current value is 0:
    - Write the value (\d).
    - Move one slot to the (\w+).
    - Continue with state (\w).
  If the current value is 1:
    - Write the value (\d).
    - Move one slot to the (\w+).
    - Continue with state (\w).""".trimIndent().toRegex()
        val (name, value0, dir0, nextState0, value1, dir1, nextState1) = pattern.matchEntire(stateStr.trimIndent())!!.destructured
        val action0 = Action(if (dir0 == "right") 1 else -1, value0.toInt().toByte(), nextState0[0])
        val action1 = Action(if (dir1 == "right") 1 else -1, value1.toInt().toByte(), nextState1[0])
        return State(name[0], action0, action1)
    }

    class State(val name: Char, private val action0: Action, private val action1: Action) {
        fun getAction(tape: MutableList<Byte>, idx: Int): Action {
            val currentValue = if (idx >= 0 && idx < tape.size) tape[idx] else 0
            return if (currentValue == 0.toByte()) action0 else action1
        }
    }

    class Action(private val idxModifier: Int, private val value: Byte, private val nextState: Char) {
        fun write(tape: MutableList<Byte>, idx: Int) {
            when {
                idx >= tape.size -> tape.add(value)
                idx < 0 -> tape.add(0, value)
                else -> tape[idx] = value
            }
        }

        fun nextIdx(idx: Int): Int {
            return if (idxModifier == -1 && idx <= 0) -1 else idx + idxModifier
        }

        fun nextState(states: Map<Char, State>): State {
            return states.getValue(nextState)
        }
    }
}
