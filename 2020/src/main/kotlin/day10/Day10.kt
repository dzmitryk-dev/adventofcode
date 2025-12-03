package day10

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day10.input")

    runPuzzle(1) {
        part1(input)
    }
}

private fun adapters(input: List<String>): List<Int> {
    return input.map { it.toInt() }
        .sorted()
        .let { listOf(0) + it + listOf(it.last() + 3) }
}

fun part1(input: List<String>): Int {
    return adapters(input)
        .windowed(2)
        .groupBy { (a,b) -> b - a }
        .mapValues { v -> v.value.size }
        .let { m ->
            m[1]!! * m[3]!!
        }
}

fun part2(input: List<String>): Long {
    val adapters = adapters(input)

    val waysToReach = mutableMapOf(0 to 1L)

    for (adapter in adapters.drop(1)) {
        waysToReach[adapter] = (1..3).sumOf { diff -> waysToReach[adapter - diff] ?: 0L }
    }

    return waysToReach.getValue(adapters.last())
}