package adventofcode2024.day7

import adventofcode2024.readInput
import kotlin.time.measureTimedValue

fun main() {
    val equations = parseInput(readInput("day7.input"))

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        puzzle1(equations)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(equations)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )

    println("Puzzle 2 Parallel started")
    val (output3, duration3) = measureTimedValue {
        puzzle2Parallel(equations)
    }
    println("Puzzle 2 Parallel output: $output3. Done in ${duration3.inWholeMilliseconds} ms" )

}

fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
    return input.map { line ->
        line.split(":").let { (result, numbers) ->
            result.toLong() to numbers.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        }
    }
}

fun tryFindSolution(result: Long, numbers: List<Long>): Boolean {
    tailrec fun calculateRecursive(numbers: List<Long>, intermediateResults: Set<Long> = setOf(0L)): Set<Long> {
        if(numbers.isEmpty()) return intermediateResults
        val number = numbers.first()
        val sumResults = intermediateResults.map { it + number }
        val multResults = intermediateResults.map { it * number }
        return calculateRecursive(numbers.drop(1), (sumResults + multResults).toSet())
    }
    return calculateRecursive(numbers).contains(result)
}

fun puzzle1(equations: List<Pair<Long, List<Long>>>): Long {
    return equations.filter { (result, numbers) -> tryFindSolution(result, numbers) }.sumOf { (result, _) -> result }
}

fun tryFindSolution2(result: Long, numbers: List<Long>): Boolean {
    tailrec fun calculateRecursive(numbers: List<Long>, intermediateResults: Set<Long> = setOf(0L)): Set<Long> {
        if(numbers.isEmpty()) return intermediateResults
        val number = numbers.first()
        val sumResults = intermediateResults.map { it + number }
        val multResults = intermediateResults.map { it * number }
        val concatResult = intermediateResults.map { "$it$number".toLong() }
        return calculateRecursive(numbers.drop(1), (sumResults + multResults + concatResult).toSet())
    }
    return calculateRecursive(numbers).contains(result)
}

fun puzzle2(equations: List<Pair<Long, List<Long>>>): Long {
    return equations.filter { (result, numbers) -> tryFindSolution2(result, numbers) }.sumOf { (result, _) -> result }
}

fun puzzle2Parallel(equations: List<Pair<Long, List<Long>>>): Long {
    return equations.stream().parallel()
        .filter { (result, numbers) -> tryFindSolution2(result, numbers) }
        .map { (result, _) -> result }
        .toList()
        .sum()
}