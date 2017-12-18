package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day17Test : Spek({
    describe("DAY 17") {
        val day17 = Day17()
        on("testing internals") {
            it("testing main method") {
                day17.run(3, 3, 3) `should be equal to` 1
                day17.run(3, 0, 3) `should be equal to` 2
                day17.run(6, 6, 3) `should be equal to` 1
                day17.run(6, 0, 3) `should be equal to` 5
            }
        }
        on("testing first task") {
            it("should match expected output for sample input") {
                day17.first(3) `should be equal to` 638
            }
            it("should print answer for first task") {
                println(day17.first(324))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
            }
            it("should print answer for second task") {
                println(day17.second(324))
            }
        }
    }
})
