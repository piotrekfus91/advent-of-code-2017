package com.github.aoc2017

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day10Test : Spek({
    describe("DAY 10") {
        val day10 = Day10()
        on("testing first task") {
            it("should match expected output for sample input") {
                day10.first("3, 4, 1, 5", 5) `should be equal to` 12
                day10.first("3, 4, 1, 4", 5) `should be equal to` 8
            }
            it("should print answer for first task") {
                println(day10.first("120,93,0,90,5,80,129,74,1,165,204,255,254,2,50,113", 256))
            }
        }

        on("testing second task") {
            it("should build correct lengths from input") {
                day10.fromAsciiAndDefault("1,2,3").toList() `should equal` listOf(49, 44, 50, 44, 51, 17, 31, 73, 47, 23)
            }
            it("should count xor") {
                day10.countXor(listOf(27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22).toTypedArray(), 65) `should be equal to` 64
            }
            it("should match expected output for sample input") {
                day10.second("") `should be equal to` "a2582a3a0e66e6e86e3812dcb672a272"
                day10.second("AoC 2017") `should be equal to` "33efeb34ea91902bb2f59c9920caa6cd"
                day10.second("1,2,3") `should be equal to` "3efbe78a8d82f29979031a4aa0b16a9d"
                day10.second("1,2,4") `should be equal to` "63960835bcdc130f0b66d7ff4f6a5a8e"
            }
            it("should print answer for second task") {
                println(day10.second("120,93,0,90,5,80,129,74,1,165,204,255,254,2,50,113"))
            }
        }
    }
})
