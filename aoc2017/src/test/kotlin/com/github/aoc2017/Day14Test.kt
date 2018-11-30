package com.github.aoc2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day14Test : Spek({
    describe("DAY 14") {
        val day14 = Day14()
        on("testing util functions") {
            day14.toBinary("190af") `should be equal to` "00011001000010101111"
        }
        on("testing first task") {
            it("should match expected output for sample input") {
            }
            it("should print answer for first task") {
                println(day14.first("stpzcrnm"))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day14.second("flqrgnkx") `should be equal to`  1242
            }
            it("should print answer for second task") {
                println(day14.second("stpzcrnm"))
            }
        }
    }
})
