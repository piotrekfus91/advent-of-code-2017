package com.github.aoc2017

import org.amshove.kluent.`should be equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Day22Test : Spek({
    val input = """
..#..##...##.######.##...
..#...#####..#.#####..#..
...##.#..##.#.##....#...#
#.#.#.#..###...#....##..#
..#..#####.....##..#.#..#
.##.#####.#.....###.#..#.
##..####...#.##.#...##...
###.#.#####...##.#.##..#.
#.##..##.#....#.#..#.##..
###.######......####..#.#
###.....#.##.##.######..#
...####.###.#....#..##.##
#..####.#.....#....###.#.
#..##..#.####.#.##..#.#..
#..#.#.##...#...#####.##.
#.###..#.##.#..##.#######
...###..#..####.####.#.#.
.#..###..###.#....#######
.####..##.#####.#.#..#.#.
#.#....##.....##.##.....#
....####.....#..#.##..##.
######..##..#.###...###..
..##...##.....#..###.###.
##.#.#..##.#.#.##....##.#
.#.###..##..#....#...##.#
        """
    describe("DAY 22") {
        val day22 = Day22()
        on("testing first task") {
            it("should match expected output for sample input") {
                day22.first("""
                    ..#
                    #..
                    ...
                    """, 70) `should be equal to` 41
                day22.first("""
                    ..#
                    #..
                    ...
                    """) `should be equal to` 5587
            }
            it("should print answer for first task") {
                println(day22.first(input))
            }
        }

        on("testing second task") {
            it("should match expected output for sample input") {
                day22.second("""
                    ..#
                    #..
                    ...
                    """, 100) `should be equal to` 26
                day22.second("""
                    ..#
                    #..
                    ...
                    """) `should be equal to` 2511944
            }
            it("should print answer for second task") {
                println(day22.second(input))
            }
        }
    }
})
