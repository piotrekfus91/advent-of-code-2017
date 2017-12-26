package org.bitbucket.adventofcode2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day24Test : Spek({
    val input = """
31/13
34/4
49/49
23/37
47/45
32/4
12/35
37/30
41/48
0/47
32/30
12/5
37/31
7/41
10/28
35/4
28/35
20/29
32/20
31/43
48/14
10/11
27/6
9/24
8/28
45/48
8/1
16/19
45/45
0/4
29/33
2/5
33/9
11/7
32/10
44/1
40/32
2/45
16/16
1/18
38/36
34/24
39/44
32/37
26/46
25/33
9/10
0/29
38/8
33/33
49/19
18/20
49/39
18/39
26/13
19/32
        """
    describe("DAY 24") {
        val day24 = Day24()
        on("testing first task") {
            it("should match expected output for sample input") {
                day24.first("""
0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10
                    """) `should be equal to` 31
            }
            it("should print answer for first task") {
                println(day24.first(input))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day24.second("""
0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10
                    """) `should be equal to` 19
            }
            it("should print answer for second task") {
                println(day24.second(input))
            }
        }
    }
})
