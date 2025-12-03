package day10

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day10.input")

    runPuzzle(1) {
        part1(input)
    }
}


fun part1(input: List<String>): Int {
    return input.map { it.toInt() }
        .sorted()
        .let { listOf(0) + it + listOf(it.last() + 3) }
        .windowed(2)
        .groupBy { (a,b) -> b - a }
        .mapValues { v -> v.value.size }
        .let { m ->
            m[1]!! * m[3]!!
        }
}