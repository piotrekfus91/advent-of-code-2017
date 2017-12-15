package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day15Test : Spek({
    describe("DAY 15") {
        val day15 = Day15()
        on("testing first task") {
            it("should match expected output for sample input") {
                day15.first(65, 8921) `should be equal to` 588
            }
            it("should print answer for first task") {
                println(day15.first(618, 814))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day15.second(65, 8921) `should be equal to` 309
            }
            it("should print answer for second task") {
                println(day15.second(618, 814))
            }
        }
    }
})
