package com.github.aoc2017

class Day23 {
    fun first(input: String): Int {
        val counter = Counter(0)
        val programs = buildPrograms(input, counter)
        val registers = mutableMapOf<Char, Long>().withDefault { 0 }
        runLoop(programs, registers)
        return counter.value
    }

    fun second(): Int {
        var b = 109300
        var counter = 0
        repeat(1001) {
            if (!isPrime(b)) {
                counter++
            }
            b += 17
        }
        return counter
    }

    internal fun isPrime(it: Int): Boolean {
        return (2..Math.sqrt(it.toDouble()).toInt()).none { i -> it % i == 0 }
    }

    private fun runLoop(programs: List<Program>, registers: MutableMap<Char, Long>) {
        var currentProgram = 0
        while (currentProgram < programs.size) {
            val program = programs[currentProgram]
            val result = program.run(registers)
            currentProgram += result.toInt()
        }
    }

    private fun buildPrograms(input: String, counter: Counter = Counter(0)): List<Program> {
        val programs = input.split("\n")
                .map { it.trim() }
                .filterNot { it == "" }
                .map { toProgram(it, counter) }
        return programs
    }

    private fun toProgram(line: String, counter: Counter): Program {
        val parts = line.split(" ")
        return when(parts[0]) {
            "set" -> set(parts)
            "sub" -> sub(parts)
            "mul" -> mul(parts, counter)
            "jnz" -> jnz(parts)
            else -> throw RuntimeException(line)
        }
    }

    private fun set(parts: List<String>) : Program {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) SetReg(parts[1][0], parts[2][0]) else SetVal(parts[1][0], longOrNull)
    }

    private fun sub(parts: List<String>) : Program {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) SubReg(parts[1][0], parts[2][0]) else SubVal(parts[1][0], longOrNull)
    }

    private fun mul(parts: List<String>, counter: Counter) : Program {
        val longOrNull = parts[2].toLongOrNull()
        return if (longOrNull == null) MulReg(parts[1][0], parts[2][0], counter) else MulVal(parts[1][0], longOrNull, counter)
    }

    private fun jnz(parts: List<String>) : Program {
        val longOrNull = parts[1].toLongOrNull()
        return if (longOrNull == null) JnzRegVal(parts[1][0], parts[2].toLong()) else JnzValVal(longOrNull, parts[2].toLong())
    }

    data class Counter(var value: Int)

    interface Program {
        fun run(registers: MutableMap<Char, Long>): Long
    }

    data class SetReg(private val x: Char, private val y: Char) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            registers[x] = registers.getValue(y)
            return 1
        }
    }

    data class SetVal(private val x: Char, private val y: Long) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            registers[x] = y
            return 1
        }
    }

    data class SubReg(private val x: Char, private val y: Char) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            registers[x] = registers.getValue(x) - registers.getValue(y)
            return 1
        }
    }

    data class SubVal(private val x: Char, private val y: Long) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            registers[x] = registers.getValue(x) - y
            return 1
        }
    }

    data class MulReg(private val x: Char, private val y: Char, private val counter: Counter) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            counter.value++
            registers[x] = registers.getValue(x) * registers.getValue(y)
            return 1
        }
    }

    data class MulVal(private val x: Char, private val y: Long, private val counter: Counter) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            counter.value++
            registers[x] = registers.getValue(x) * y
            return 1
        }
    }

    data class JnzRegVal(private val x: Char, private val y: Long) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            return if(registers.getValue(x) != 0L) y else 1
        }
    }

    data class JnzValVal(private val x: Long, private val y: Long) : Program {
        override fun run(registers: MutableMap<Char, Long>): Long {
            return if (x == 0L) 1 else y
        }
    }
}

/**
set b 93           b = 93
set c b            c = b
jnz a 2            if (a != 0) jump alpha
jnz 1 5            jump beta
mul b 100          alpha: b *= 100
sub b -100000      b += 100000
set c b            c = b
sub c -17000       c += 17000
set f 1            beta: f = 1
set d 2            d = 2
set e 2            kappa: e = 2
set g d            gamma: g = d
mul g e            g *= e
sub g b            g -= b
jnz g 2            if (g != 0) jump theta
set f 0            f = 0
sub e -1           theta: e += 1
set g e            g = e
sub g b            g -= b
jnz g -8 #12       if (g != 0) jump gamma
sub d -1           d += 1
set g d            g = d
sub g b            g -= b
jnz g -13 #11      if (g != 0) jump kappa
jnz f 2            if (f != 0) jump omega
sub h -1           h += 1
set g b            omega: g = b
sub g c            g -= c
jnz g 2            if (g != 0) jump epsilon
jnz 1 3            jump zeta
sub b -17          epsilon: b += 17
jnz 1 -23 #9       jump beta

---------------------------------------------------------
----------------- rewrite to program way ----------------
b = 93 #1
c = 93 #2

if (a != 0) {
b *= 100 // b = 9300 #5
b += 100000 // b = 109300 #6
c = b // c = 109300 #7
c += 17000 // c = 126300 #8
}

do { # 32
f = 1
d = 2
do {
e = 2
do {
g = d // g = 2
g *= e
g -= b
if (g == 0) {
f = 0
}
e += 1
g = e
g -= b
} while (g != 0)
d += 1
g = d
g -= b
} while(g != 0)

if (f == 0) {
h += 1
}

g = b
g -= c

if (g != 0) {
b += 17
}
} while (g != 0)

----------------------------------------------------------
------------------ simplify init -------------------------

b = 109300
c = 126300

do {
f = 1
d = 2
do {
e = 2
do {
g = d
g *= e
g -= b
if (g == 0) {
f = 0
}
e += 1
g = e
g -= b
} while (g != 0)
d += 1
g = d
g -= b
} while(g != 0)

if (f == 0) {
h += 1
}

g = b
g -= c

if (g != 0) {
b += 17
}
} while (g != 0)

-------------------------------------------------------------
----------- simplify arithmetic operations ------------------

b = 109300
c = 126300

do {
f = 1
d = 2
do {
e = 2

do {
g = d * e - b
if (g == 0) {
f = 0
}
e += 1
g = e - b
} while (g != 0)

d += 1
g = d - b

} while(g != 0)

if (f == 0) {
h += 1
}

g = b - c

if (g != 0) {
b += 17
}
} while (g != 0)

---------------------------------------------------------------
---------- simplify conditions --------------------------------

b = 109300
c = 126300

do {
f = 1
d = 2
do {
e = 2

do {
if (d * e == b) { // d=2, e=54650; d=4, e=27325;
f = 0
}
e += 1
} while (e != b)

d += 1

} while(d != b)

if (f == 0) {
h += 1
}

g = b - c

if (g != 0) {
b += 17
}
} while (g != 0)

result: for(b = 109300; b < 126300; b+=17) { sum += 1 if b is not prime }
        */
