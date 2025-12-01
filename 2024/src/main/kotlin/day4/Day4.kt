package adventofcode2024.day4

import adventofcode2024.Point
import adventofcode2024.col
import adventofcode2024.line
import adventofcode2024.readInput
import kotlin.time.measureTimedValue

fun main() {
    val input = parseInput(readInput("day4.input"))

    println("Puzzle 1")
    val (output, duration) =  measureTimedValue {
       findXMAS(input)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms")

    println("Puzzle 2")
    val (output2, duration2) =  measureTimedValue {
        findMASX(input)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms")
}

fun parseInput(input: List<String>): List<CharArray> {
    return input.map { it.toCharArray() }
}

fun findX(input: List<CharArray>): List<Point> {
    return buildList {
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char == 'X') {
                    add(Point(lineIndex, charIndex))
                }
            }
        }
    }
}

fun findNextSymbol(input: List<CharArray>, point: Point, direction: Pair<Int, Int>, symbols: String): Boolean {
    if (symbols.isEmpty()) {
        return true
    }
    return try {
        val actualPoint = Point(point.line + direction.first, point.col + direction.second)
        val actual = input[actualPoint.line][actualPoint.col]
        val expected = symbols.first()
        if (actual == expected) {
            findNextSymbol(input, actualPoint, direction, symbols.substring(1))
        } else {
            false
        }
    } catch (ex: IndexOutOfBoundsException) {
        false
    }
}

fun findXMAS(input: List<CharArray>): Int {
    val directions = listOf(0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1, 1 to 1, 1 to 0, 1 to -1)
    var count = 0
    findX(input).forEach { point ->
        directions.forEach { direction ->
            if(findNextSymbol(input, point, direction, "MAS")) {
                count++
            }
        }
    }
    return count
}

fun findA(input: List<CharArray>): List<Point> {
    return buildList {
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char == 'A')
                    add(Point(lineIndex, charIndex))
            }
        }
    }
}

fun findMASX(input: List<CharArray>): Int {
    val letters = listOf('M', 'S')
    var count = 0
    findA(input).forEach { point ->
        val result = try {
            val d1 = setOf(
                input[point.first - 1][point.second - 1],
                input[point.first + 1][point.second + 1]
            )
            val d2 = setOf(
                input[point.first - 1][point.second + 1],
                input[point.first + 1][point.second - 1]
            )
            d1.containsAll(letters) && d2.containsAll(letters)
        } catch (ex: IndexOutOfBoundsException) {
            false
        }
        if (result) {
            count++
        }
    }
    return count
}