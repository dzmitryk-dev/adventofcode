package adventofcode2024.day1

import adventofcode2024.readInput
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day1.input")

    val (left, right) = parseInput(input)

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        puzzle1(left, right)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(left, right)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )
}

internal fun parseInput(input: List<String>): Pair<IntArray, IntArray> =
    input.map { it.split("   ") }
        .map { (a, b) -> a.toInt() to b.toInt() }
        .toList()
        .let { list ->
            val left = IntArray(list.size)
            val right = IntArray(list.size)

            list.forEachIndexed { index, (l, r) ->
                left[index] = l
                right[index] = r
            }
            left to right
        }

internal fun puzzle1(left: IntArray, right: IntArray): Int {
    left.sort()
    right.sort()

    var totalDistance = 0
    for (i in left.indices) {
        val distance = abs(left[i] - right[i])
        totalDistance += distance
    }
    return totalDistance
}

internal fun puzzle2(left: IntArray, right: IntArray): Int {
    return left.sumOf { v ->
        v * right.count { it == v }
    }
}