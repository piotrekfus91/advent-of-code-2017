package com.github.aoc2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day13Test : Spek({
    describe("DAY 13") {
        val input = """
            0: 3
1: 2
2: 5
4: 4
6: 4
8: 6
10: 6
12: 6
14: 8
16: 6
18: 8
20: 8
22: 8
24: 12
26: 8
28: 12
30: 8
32: 12
34: 12
36: 14
38: 10
40: 12
42: 14
44: 10
46: 14
48: 12
50: 14
52: 12
54: 9
56: 14
58: 12
60: 12
64: 14
66: 12
70: 14
76: 20
78: 17
80: 14
84: 14
86: 14
88: 18
90: 20
92: 14
98: 18"""
        val day13 = Day13()
        on("testing first task") {
            it("should match expected output for sample input") {
                day13.first("""
                    0: 3
                    1: 2
                    4: 4
                    6: 4
                    """) `should be equal to` 24
            }
            it("should print answer for first task") {
                println(day13.first(input))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day13.second("""
                    0: 3
                    1: 2
                    4: 4
                    6: 4
                    """) `should be equal to` 10
            }
            it("should print answer for second task") {
                println(day13.second(input))
            }
        }
    }
})
