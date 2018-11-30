package com.github.aoc2017

import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day23Test : Spek({
    val input = """
set b 93
set c b
jnz a 2
jnz 1 5
mul b 100
sub b -100000
set c b
sub c -17000
set f 1
set d 2
set e 2
set g d
mul g e
sub g b
jnz g 2
set f 0
sub e -1
set g e
sub g b
jnz g -8
sub d -1
set g d
sub g b
jnz g -13
jnz f 2
sub h -1
set g b
sub g c
jnz g 2
jnz 1 3
sub b -17
jnz 1 -23
        """
    describe("DAY 23") {
        val day23 = Day23()
        on("testing first task") {
            it("should match expected output for sample input") {
            }
            it("should print answer for first task") {
//                println(day23.first(input))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day23.isPrime(4).`should be false`()
                day23.isPrime(5).`should be true`()
                day23.isPrime(8).`should be false`()
                day23.isPrime(7).`should be true`()
                day23.isPrime(25).`should be false`()
                day23.isPrime(15).`should be false`()
            }
            it("should print answer for second task") {
                println(day23.second())
            }
        }
    }
})
