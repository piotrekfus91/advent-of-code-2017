package org.bitbucket.adventofcode2097

import org.amshove.kluent.`should equal`
import org.bitbucket.adventofcode2017.reverseRangeCyclic
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object UtilTest : Spek({
    describe("Util") {
        on("reversing array") {
            val array: Array<Int> = Array(5) { it }
            it("should reverse non cyclic") {
                val copyOf = array.copyOf()
                copyOf.reverseRangeCyclic(1, 4)
                copyOf.toList() `should equal` listOf(0, 3, 2, 1, 4)
            }
            it("should reverse cyclic in odd range") {
                val copyOf = array.copyOf()
                copyOf.reverseRangeCyclic(3, 2)
                copyOf.toList() `should equal` listOf(4, 3, 2, 1, 0)
            }
            it("should reverse cyclic in even range") {
                val copyOf = array.copyOf()
                copyOf.reverseRangeCyclic(3, 1)
                copyOf.toList() `should equal` listOf(3, 1, 2, 0, 4)
            }
        }
    }
})
