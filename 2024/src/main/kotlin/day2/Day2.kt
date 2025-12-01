package adventofcode2024.day2

import adventofcode2024.readInput
import kotlin.math.abs
import kotlin.math.sign
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day2.input")

    val parsedInput = parseInput(input)

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        puzzle1(parsedInput)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(parsedInput)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )
}

internal fun parseInput(input: List<String>): Collection<List<Int>> =
    input.map { l -> l.split(" ").filter { it.isNotEmpty() }.map { v -> v.toInt() } }

internal fun isSafe(rules: List<Int>): Boolean {
    val sign = (rules[1] - rules[0]).sign
    for (i in 1 .. rules.lastIndex) {
        val a = rules[i]
        val b = rules[i - 1]
        if(abs(a - b) !in 1..3) {
            return false
        }
        if ((a - b).sign != sign) {
            return false
        }
    }
    return true
}

internal fun puzzle1(input: Collection<List<Int>>): Int {
    return input.map { r -> isSafe(r) }.count { it }
}

internal fun isSafe2Dump(rules: List<Int>): Boolean {
    val result = isSafe(rules)
    if (result) {
        return true
    }
    repeat(rules.size) { attempt ->
        val newRules = rules.toMutableList().apply {
            removeAt(attempt)
        }
        val dampeneredResult = isSafe(newRules)
        if (dampeneredResult) {
            return true
        }
    }
    return false
}

internal fun puzzle2(input: Collection<List<Int>>): Int {
    return input.map { r -> isSafe2Dump(r) }.count { it }
}