package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day06Test : Spek({
    describe("DAY 06") {
        val day06 = Day06()
        on("testing first task") {
            it("should match expected output for sample input") {
                day06.first("0 2 7 0") `should be equal to` 5
            }
            it("should print answer for first task") {
                println(day06.first("14\t0\t15\t12\t11\t11\t3\t5\t1\t6\t8\t4\t9\t1\t8\t4"))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day06.second("0 2 7 0") `should be equal to` 4
            }
            it("should print answer for second task") {
                println(day06.second("14\t0\t15\t12\t11\t11\t3\t5\t1\t6\t8\t4\t9\t1\t8\t4"))
            }
        }
    }
})
