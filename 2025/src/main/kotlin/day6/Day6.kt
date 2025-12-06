package day6

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import day6.Problem.Operation

fun main() {
    val input = readInput("day6.input")

    runPuzzle(1) {
        part1(input)
    }
}

data class Problem(
    val numbers: LongArray,
    val operation: Operation,
) {
    enum class Operation {
        MULT, ADD
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Problem

        if (!numbers.contentEquals(other.numbers)) return false
        if (operation != other.operation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numbers.contentHashCode()
        result = 31 * result + operation.hashCode()
        return result
    }
}

fun String.toOperation(): Operation {
    return if (this == "*") {
        Operation.MULT
    } else {
        Operation.ADD
    }
}

fun parseInput(input: List<String>): List<Problem> {
    val preparedInput = input.map { it.split(" ").filterNot { it.isEmpty() } }
    val operations = preparedInput.last()
    val numbersList = preparedInput.subList(0, preparedInput.lastIndex).map { l -> l.map { it.toLong() } }

    return operations.mapIndexed { index, string ->
        val operation = string.toOperation()

        val numbers = numbersList.map { l -> l[index] }.toLongArray()

        Problem(
            numbers = numbers,
            operation = operation,
        )
    }
}

fun calculation(problem: Problem): Long {
    val func =  when (problem.operation) {
        Operation.MULT ->  { a: Long, b: Long -> a * b }
        Operation.ADD ->  { a: Long, b: Long -> a + b }
    }
    return problem.numbers.reduce(func)
}

fun part1(input: List<String>): Long {
    return parseInput(input).sumOf { p -> calculation(p) }
}