package day1

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day1.input").map { it.toInt() }

    runPuzzle(1) {
        part1(input)
    }
    runPuzzle(1) {
        part2(input)
    }
}

fun part1(input: List<Int>): Int {
    val wanted = mutableSetOf<Int>()

    for (n in input) {
        val complement = 2020 - n
        if (complement in wanted) {
            return n * complement
        }
        wanted.add(n)
    }

    throw IllegalStateException("No solution found")
}

fun part2(input: List<Int>): Int {
    val wanted = mutableSetOf<Int>()

    for (i in input.indices) {
        for (j in i + 1 until input.size) {
            val complement = 2020 - input[i] - input[j]
            if (complement in wanted) {
                return input[i] * input[j] * complement
            }
        }
        wanted.add(input[i])
    }

    throw IllegalStateException("No solution found")
}