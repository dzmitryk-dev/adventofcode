package adventofcode2024.day10

import adventofcode2024.*
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day10.input")

    println("Puzzle 1 Started")
    val (output, duration) = measureTimedValue {
        puzzle1(input)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 Started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(input)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )
}

fun parseInput(input: List<String>): List<IntArray> {
    return input.map { line -> line.map { c -> "$c".toIntOrNull() ?: -1 }.toIntArray() }
}

fun countTrailheads(field: List<IntArray>): Int {
    val startPoints = buildList {
        field.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { colIndex, v ->
                if (v == 0) {
                    add(Point(lineIndex, colIndex))
                }
            }
        }
    }

    return startPoints.map { s ->
        tailrec fun iterate(candidates: Set<Point>, value: Int): Set<Point> {
            if (value == 9) {
                return candidates
            }
            if (candidates.isEmpty()) {
                return emptySet()
            }
            val newCandidate = candidates.map {
                it.pointsAround().filter { p ->
                    try {
                        val v = field[p.line][p.col]
                        v == value + 1
                    } catch (e: IndexOutOfBoundsException) {
                        false
                    }
                }
            }.flatten().toSet()
            return iterate(newCandidate, value + 1)
        }

        iterate(setOf(s), 0)
    }.sumOf { it.size }
}

fun puzzle1(input: List<String>): Int {
    val field = parseInput(input)
    return countTrailheads(field)
}

fun countTrails(field: List<IntArray>): Int {
    val endPoints = buildList {
        field.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { colIndex, v ->
                if (v == 0) {
                    add(Point(lineIndex, colIndex))
                }
            }
        }
    }

    return endPoints.map { s ->
        tailrec fun iterate(candidates: List<Point>, value: Int): List<Point> {
            if (value == 9) {
                return candidates
            }
            if (candidates.isEmpty()) {
                return emptyList()
            }
            val newCandidates = candidates.map {
                it.pointsAround().filter { p ->
                    try {
                        val v = field[p.line][p.col]
                        v == value + 1
                    } catch (e: IndexOutOfBoundsException) {
                        false
                    }
                }
            }.flatten()
            return iterate(newCandidates, value + 1)
        }

        iterate(listOf(s), 0)
    }.sumOf { it.size }
}

fun puzzle2(input: List<String>): Int {
    val field = parseInput(input)
    return countTrails(field)
}

