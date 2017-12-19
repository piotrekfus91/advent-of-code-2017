package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day18Test : Spek({
    describe("DAY 18") {
        val input = """
set i 31
set a 1
mul p 17
jgz p p
mul a 2
add i -1
jgz i -2
add a -1
set i 127
set p 622
mul p 8505
mod p a
mul p 129749
add p 12345
mod p a
set b p
mod b 10000
snd b
add i -1
jgz i -9
jgz a 3
rcv b
jgz b -1
set f 0
set i 126
rcv a
rcv b
set p a
mul p -1
add p b
jgz p 4
snd a
set a b
jgz 1 3
snd b
set f 1
add i -1
jgz i -11
snd a
jgz f -16
jgz a -19
"""
        val day18 = Day18()
        on("testing first task") {
            it("should match expected output for sample input") {
                day18.first("""
                    set a 1
                    add a 2
                    mul a a
                    mod a 5
                    snd a
                    set a 0
                    rcv a
                    jgz a -1
                    set a 1
                    jgz a -2
                """.trimIndent()) `should be equal to` 4
            }
            it("should print answer for first task") {
                println(day18.first(input))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input1") {
                day18.second("""
                    snd 1
                    snd 2
                    snd p
                    rcv a
                    rcv b
                    rcv c
                    rcv d
                    """) `should be equal to` 3
            }
//            it("should match expected output for sample input2") {
//                day18.second("""
//                    snd 1
//                    snd 2
//                    snd p
//                    rcv a
//                    rcv b
//                    rcv c
//                    """) `should be equal to` 3
//            }
            it("should print answer for second task") {
                println(day18.second(input))
            }
        }
    }
})
