package adventofcode2024.day13

import adventofcode2024.readInput
import adventofcode2024.runPuzzle

fun main() {
    val input = readInput("day13.input")

    runPuzzle(1) {
        partOne(input)
    }

    runPuzzle(2) {
        partTwo(input)
    }
}

data class Machine(
    val a: Pair<Int, Int>,
    val b: Pair<Int, Int>,
    val prize: Pair<Long, Long>,
)

fun parseInput(input: List<String>): List<Machine> {
    val machines = mutableListOf<Machine>()

    var a: Pair<Int, Int>? = null
    var b: Pair<Int, Int>? = null
    var prize: Pair<Long, Long>? = null

    for (line in input) {
        when {
            line.startsWith("Button A:") -> {
                val coordinates = line.substringAfter("Button A:").trim()
                    .split(", ")
                    .map { it.substring(2).toInt() }
                a = coordinates[0] to coordinates[1]
            }

            line.startsWith("Button B:") -> {
                val coordinates = line.substringAfter("Button B:").trim()
                    .split(", ")
                    .map { it.substring(2).toInt() }
                b = coordinates[0] to coordinates[1]
            }

            line.startsWith("Prize:") -> {
                val coordinates = line.substringAfter("Prize:").trim()
                    .split(", ")
                    .map { it.substringAfter("=").toLong() }
                prize = coordinates[0] to coordinates[1]
            }

            line.isBlank() -> {
                if (a != null && b != null && prize != null) {
                    machines.add(Machine(a, b, prize))
                    a = null
                    b = null
                    prize = null
                }
            }
        }
    }

    // Add the last machine if it wasn't followed by a blank line
    if (a != null && b != null && prize != null) {
        machines.add(Machine(a, b, prize))
    }

    return machines
}

fun findButtonPressCombination(machine: Machine): Pair<Long, Long>? {
    val (ax, ay) = machine.a
    val (bx, by) = machine.b
    val (px, py) = machine.prize

    // ax * a + bx * b = px
    // ay * a + by * b = py

    val determinant = ax * by - ay * bx

    if (determinant == 0) {
        return null
    }

    val x = (px * by - bx * py) / determinant
    val y = (ax * py - px * ay) / determinant

//    if (x !in (0..100) || y !in (0..100)) {
//        return null
//    }
    // Check that result is valid
    if (ax * x + bx * y != px || ay * x + by * y != py) {
        return null
    }
    println("for machine $machine, result: x = $x, y = $y")
    return Pair(x, y)
}

fun findTokensNumber(pair: Pair<Long, Long>): Long = pair.first * 3 + pair.second * 1

fun partOne(input: List<String>): Long {
    val machines = parseInput(input)

    val result = machines
        .mapNotNull { findButtonPressCombination(it) }
        .sumOf { findTokensNumber(it) }

    return result
}

fun partTwo(input: List<String>): Long {
    val machines = parseInput(input)
        .map { m -> m.copy(prize = m.prize.copy(m.prize.first + 10000000000000, m.prize.second + 10000000000000)) }

    val result = machines
        .mapNotNull { findButtonPressCombination(it) }
        .sumOf { findTokensNumber(it) }

    return result
}