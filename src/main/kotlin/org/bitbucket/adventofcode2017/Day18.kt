package org.bitbucket.adventofcode2017

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class Day18 {
    fun first(input: String): Long {
        val lastSound = LastSound(0)
        val commands = input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { toCommandFirst(it, lastSound) }
        val registers = mutableMapOf<Char, Long>().withDefault { 0 }
        try {
            var idx = 0L
            while (true) {
                val command = commands.get(idx.toInt())
                idx += command.perform(registers)
            }
        } catch (e: SoundRecordedException) {
            return e.freq
        }
    }

    fun second(input: String): Long {
        val progA = Program18(0)
        val progB = Program18(1)
        progA.other = progB
        progB.other = progA
        progA.commands = input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { toCommandSecond(it, progA) }
        progB.commands = input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { toCommandSecond(it, progB) }
        val threadA = thread(start = true) {
            progA.dispatch()
        }
        val threadB = thread(start = true) {
            progB.dispatch()
        }
        listOf(threadA, threadB).forEach { it.join() }
        return progB.numberOfSends
    }

    private fun toCommandFirst(str: String, lastSound: LastSound): Command {
        val parts = str.split("\\s+".toRegex())
        return when {
            parts[0] == "snd" -> Snd(parts[1][0], lastSound)
            parts[0] == "set" -> set(parts)
            parts[0] == "add" -> add(parts)
            parts[0] == "mul" -> mul(parts)
            parts[0] == "mod" -> mod(parts)
            parts[0] == "rcv" -> Rcv(parts[1][0], lastSound)
            parts[0] == "jgz" -> jgz(parts)
            else -> { throw RuntimeException(str) }
        }
    }

    private fun toCommandSecond(str: String, program: Program18): Command {
        val parts = str.split("\\s+".toRegex())
        return when {
            parts[0] == "snd" -> snd2(program, parts)
            parts[0] == "set" -> set(parts)
            parts[0] == "add" -> add(parts)
            parts[0] == "mul" -> mul(parts)
            parts[0] == "mod" -> mod(parts)
            parts[0] == "rcv" -> Receive(program, parts[1][0])
            parts[0] == "jgz" -> jgz(parts)
            else -> { throw RuntimeException(str) }
        }
    }

    private fun snd2(program: Program18, parts: List<String>): Command {
        val longOrNull = parts[1].toLongOrNull()
        return if (longOrNull == null) {
            SendReg(program, parts[1][0])
        } else {
            SendVal(program, longOrNull)
        }
    }

    private fun mul(parts: List<String>): Command {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) {
            MulReg(parts[1][0], parts[2][0])
        } else {
            MulVal(parts[1][0], longOrNull)
        }
    }

    private fun add(parts: List<String>): Command {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) {
            AddReg(parts[1][0], parts[2][0])
        } else {
            AddVal(parts[1][0], longOrNull)
        }
    }

    private fun jgz(parts: List<String>): Command {
        val xLongOrNull = parts[1].toLongOrNull()
        val yLongOrNull = parts[2].toLongOrNull()
        return if (xLongOrNull != null) {
            JgzValVal(xLongOrNull, yLongOrNull!!)
        } else if (yLongOrNull == null) {
            JgzRegReg(parts[1][0], parts[2][0])
        } else {
            JgzRegVal(parts[1][0], yLongOrNull)
        }
    }

    private fun mod(parts: List<String>): Command {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) {
            ModReg(parts[1][0], parts[2][0])
        } else {
            ModVal(parts[1][0], longOrNull)
        }
    }

    private fun set(parts: List<String>): Command {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) {
            SetReg(parts[1][0], parts[2][0])
        } else {
            SetVal(parts[1][0], longOrNull)
        }
    }
}

interface Command {
    fun perform(registers: MutableMap<Char, Long>): Long
}

///////////////////// second ////////////////////////////

class Program18(val progP: Long) {
    var other: Program18? = null
    var commands: List<Command> = listOf()
    val messages = LinkedBlockingQueue<Long>()
    val registers = mutableMapOf<Char, Long>().withDefault { if(it == 'p') progP else 0 }
    var numberOfSends = 0L

    fun dispatch() {
        var idx = 0L
        while(true) {
            val command = commands.get(idx.toInt())
            idx += command.perform(registers)
            if (command is SendVal || command is SendReg) {
                numberOfSends++
            }
            Thread.yield()
        }
    }
}

class SendVal(private val program: Program18, private val value: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        program.other!!.messages.put(value)
        return 1
    }
}

class SendReg(private val program: Program18, private val reg: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        program.other!!.messages.put(registers.getValue(reg))
        return 1
    }
}

class Receive(private val program: Program18, private val reg: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        val polled = program.messages.poll(100, TimeUnit.MILLISECONDS)
        if (polled == null) {
            throw ExecutionFinishedException(program.numberOfSends)
        }
        registers[reg] = polled
        return 1
    }
}

class ExecutionFinishedException(val numberOfSends: Long) : RuntimeException()

////////////////////// first ////////////////////////////

class LastSound(var x: Long)
class SoundRecordedException(val freq: Long) : RuntimeException()

class Snd(private val x: Char, private val lastSound: LastSound) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        lastSound.x = registers.getValue(x)
        return 1
    }
}

class SetReg(private val x: Char, private val y: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(y)
        return 1
    }
}

class SetVal(private val x: Char, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = y
        return 1
    }
}

class AddVal(private val x: Char, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) + y
        return 1
    }
}

class AddReg(private val x: Char, private val y: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) + registers.getValue(y)
        return 1
    }
}

class MulReg(private val x: Char, private val y: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) * registers.getValue(y)
        return 1
    }
}

class MulVal(private val x: Char, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) * y
        return 1
    }
}

class ModVal(private val x: Char, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) % y
        return 1
    }
}

class ModReg(private val x: Char, private val y: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        registers[x] = registers.getValue(x) % registers.getValue(y)
        return 1
    }
}

class Rcv(private val x: Char, private val lastSound: LastSound) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        if (registers.getValue(x) > 0) {
            throw SoundRecordedException(lastSound.x)
        }
        return 1
    }
}

class JgzRegVal(private val x: Char, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        return if(registers.getValue(x) > 0) {
            y
        } else {
            1
        }
    }
}

class JgzRegReg(private val x: Char, private val y: Char) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        return if(registers.getValue(x) > 0) {
            registers.getValue(y)
        } else {
            1
        }
    }
}

class JgzValVal(private val x: Long, private val y: Long) : Command {
    override fun perform(registers: MutableMap<Char, Long>): Long {
        return if (x > 0) {
            y
        } else {
            1
        }
    }
}
