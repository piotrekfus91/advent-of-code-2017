package org.bitbucket.adventofcode2017

import java.util.*

class Day09 {
    fun first(input: String): Int {
        val stack = ArrayDeque<Char>()
        var index = 0
        var sum = 0
        while (index < input.length) {
            val c = input[index]
            if (c == '{' || c == '<') {
                if (stack.peek() != '<') {
                    stack.push(c)
                }
            } else if (c == '!') {
                index++
            } else if (c == '}') {
                if (stack.peek() == '{') {
                    sum += stack.count { it == '{' }
                    stack.pop()
                }
            } else if (c == '>') {
                if (stack.peek() == '<') {
                    stack.pop()
                }
            }
            index++
        }
        return sum
    }

    fun second(input: String): Int {
        val stack = ArrayDeque<Char>()
        var index = 0
        var sum = 0
        while (index < input.length) {
            val c = input[index]
            if (c == '{' || c == '<') {
                if (stack.peek() != '<') {
                    stack.push(c)
                } else {
                    sum++
                }
            } else if (c == '!') {
                index++
            } else if (c == '}') {
                if (stack.peek() == '{') {
                    stack.pop()
                } else {
                    sum++
                }
            } else if (c == '>') {
                stack.pop()
            } else if (c != ',' || stack.peek() == '<') {
                sum++
            }
            index++
        }
        return sum
    }
}
