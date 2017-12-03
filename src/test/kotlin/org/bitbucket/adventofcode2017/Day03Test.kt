package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day03Test : Spek({
    describe("DAY 03") {
        val day03 = Day03()
        on("testing first task") {
            it("should check if number is squarable") {
                day03.isSquarable(1).shouldBeTrue()
                day03.isSquarable(4).shouldBeTrue()
                day03.isSquarable(9).shouldBeTrue()
                day03.isSquarable(2).shouldBeFalse()
                day03.isSquarable(3).shouldBeFalse()
                day03.isSquarable(5).shouldBeFalse()
                day03.isSquarable(6).shouldBeFalse()
                day03.isSquarable(7).shouldBeFalse()
                day03.isSquarable(8).shouldBeFalse()
            }
            it("should count distance to border for squarable numbers") {
                day03.distanceToBorder(4) `should be equal to`  1
                day03.distanceToBorder(9) `should be equal to`  1
                day03.distanceToBorder(16) `should be equal to`  2
                day03.distanceToBorder(25) `should be equal to`  2
                day03.distanceToBorder(36) `should be equal to`  3
            }
            it("should bcount distance to for non-squarable numbers") {
                day03.distanceToBorder(2) `should be equal to` 1
                day03.distanceToBorder(3) `should be equal to` 1
                day03.distanceToBorder(5) `should be equal to` 1
                day03.distanceToBorder(6) `should be equal to` 1
                day03.distanceToBorder(7) `should be equal to` 1
                day03.distanceToBorder(8) `should be equal to` 1
                day03.distanceToBorder(10) `should be equal to` 2
                day03.distanceToBorder(11) `should be equal to` 2
                day03.distanceToBorder(12) `should be equal to` 2
                day03.distanceToBorder(13) `should be equal to` 2
                day03.distanceToBorder(14) `should be equal to` 2
                day03.distanceToBorder(15) `should be equal to` 2
                day03.distanceToBorder(16) `should be equal to` 2
                day03.distanceToBorder(17) `should be equal to` 2
                day03.distanceToBorder(18) `should be equal to` 2
                day03.distanceToBorder(19) `should be equal to` 2
                day03.distanceToBorder(20) `should be equal to` 2
                day03.distanceToBorder(21) `should be equal to` 2
                day03.distanceToBorder(22) `should be equal to` 2
                day03.distanceToBorder(23) `should be equal to` 2
                day03.distanceToBorder(24) `should be equal to` 2
                day03.distanceToBorder(25) `should be equal to` 2
                day03.distanceToBorder(26) `should be equal to` 3
            }
            it("should count distance to half of line") {
                day03.distanceToHalfOfline(2) `should be equal to` 0
                day03.distanceToHalfOfline(3) `should be equal to` 1
                day03.distanceToHalfOfline(5) `should be equal to` 1
                day03.distanceToHalfOfline(6) `should be equal to` 0
                day03.distanceToHalfOfline(7) `should be equal to` 1
                day03.distanceToHalfOfline(8) `should be equal to` 0
                day03.distanceToHalfOfline(10) `should be equal to` 1
                day03.distanceToHalfOfline(11) `should be equal to` 0
                day03.distanceToHalfOfline(12) `should be equal to` 1
                day03.distanceToHalfOfline(13) `should be equal to` 2
                day03.distanceToHalfOfline(14) `should be equal to` 1
                day03.distanceToHalfOfline(15) `should be equal to` 0
                day03.distanceToHalfOfline(17) `should be equal to` 2
                day03.distanceToHalfOfline(18) `should be equal to` 1
                day03.distanceToHalfOfline(19) `should be equal to` 0
                day03.distanceToHalfOfline(20) `should be equal to` 1
                day03.distanceToHalfOfline(21) `should be equal to` 2
                day03.distanceToHalfOfline(22) `should be equal to` 1
                day03.distanceToHalfOfline(23) `should be equal to` 0
                day03.distanceToHalfOfline(24) `should be equal to` 1
                day03.distanceToHalfOfline(26) `should be equal to` 2
                day03.distanceToHalfOfline(27) `should be equal to` 1
                day03.distanceToHalfOfline(28) `should be equal to` 0
                day03.distanceToHalfOfline(29) `should be equal to` 1
                day03.distanceToHalfOfline(30) `should be equal to` 2
                day03.distanceToHalfOfline(31) `should be equal to` 3
                day03.distanceToHalfOfline(32) `should be equal to` 2
                day03.distanceToHalfOfline(33) `should be equal to` 1
                day03.distanceToHalfOfline(34) `should be equal to` 0
                day03.distanceToHalfOfline(35) `should be equal to` 1
                day03.distanceToHalfOfline(37) `should be equal to` 3
                day03.distanceToHalfOfline(38) `should be equal to` 2
                day03.distanceToHalfOfline(39) `should be equal to` 1
                day03.distanceToHalfOfline(40) `should be equal to` 0
                day03.distanceToHalfOfline(41) `should be equal to` 1
                day03.distanceToHalfOfline(42) `should be equal to` 2
                day03.distanceToHalfOfline(43) `should be equal to` 3
                day03.distanceToHalfOfline(44) `should be equal to` 2
                day03.distanceToHalfOfline(45) `should be equal to` 1
                day03.distanceToHalfOfline(46) `should be equal to` 0
                day03.distanceToHalfOfline(47) `should be equal to` 1
                day03.distanceToHalfOfline(48) `should be equal to` 2
                day03.distanceToHalfOfline(50) `should be equal to` 3
            }
            it("should count for given data") {
                day03.first(1) `should be equal to` 0
                day03.first(19) `should be equal to` 2
                day03.first(23) `should be equal to` 2
                day03.first(25) `should be equal to` 4
                day03.first(16) `should be equal to` 3
                day03.first(12) `should be equal to` 3
            }
            it("should print answer for first task") {
                println(day03.first(265149))
            }
        }
        on("testing second task") {
            it("return correct positions") {
                day03.positionsGenerator.take(14).toList() `should equal` listOf(
                        Position(0, 0, 1),
                        Position(1, 0, 1),
                        Position(1, 1, 2),
                        Position(0, 1, 4),
                        Position(-1, 1, 5),
                        Position(-1, 0, 10),
                        Position(-1, -1, 11),
                        Position(0, -1, 23),
                        Position(1, -1, 25),
                        Position(2, -1, 26),
                        Position(2, 0, 54),
                        Position(2, 1, 57),
                        Position(2, 2, 59),
                        Position(1, 2, 122)
                )
            }
            it("should return correct answer for sample data") {
                day03.second(1) `should be equal to` 2
                day03.second(2) `should be equal to` 4
                day03.second(3) `should be equal to` 4
                day03.second(4) `should be equal to` 5
                day03.second(5) `should be equal to` 10
                day03.second(6) `should be equal to` 10
                day03.second(7) `should be equal to` 10
                day03.second(8) `should be equal to` 10
                day03.second(9) `should be equal to` 10
                day03.second(10) `should be equal to` 11
                day03.second(11) `should be equal to` 23
                day03.second(12) `should be equal to` 23
                day03.second(23) `should be equal to` 25
            }
            it("should print answer for second task") {
                println(day03.second(265149))
            }
        }
    }
})
