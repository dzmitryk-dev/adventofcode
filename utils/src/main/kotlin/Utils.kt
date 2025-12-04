package com.adventofcode.utils

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.time.measureTimedValue

fun openResourseAsStream(resourceName: String): InputStream =
    requireNotNull(object {}.javaClass.classLoader.getResourceAsStream(resourceName))

fun InputStream.asReader() =
    BufferedReader(InputStreamReader(BufferedInputStream(this)))

fun readInput(resourceName: String): List<String> =
    openResourseAsStream(resourceName).asReader().use { reader ->
        reader.lines().toList()
    }

typealias Point = Pair<Int, Int>

val Point.line: Int
    get() = this.first

val Point.col: Int
    get() = this.second

fun Point.pointsAround(): List<Point> =
    listOf(
        Point(line, col - 1),
        Point(line - 1, col),
        Point(line, col + 1),
        Point(line + 1, col)
    )

fun Point.allPointsAround(): List<Point> =
    listOf(
        Point(line, col - 1),
        Point(line - 1, col - 1),
        Point(line - 1, col),
        Point(line - 1, col + 1),
        Point(line, col + 1),
        Point(line + 1, col + 1),
        Point(line + 1, col),
        Point(line + 1, col - 1)
    )


fun <T> runPuzzle(number: Int, block: () -> T) {
    println("Puzzle $number Started")
    val (output, duration) = measureTimedValue {
        block()
    }
    println("Puzzle $number output: $output. Done in ${duration.inWholeMilliseconds} ms" )
}
