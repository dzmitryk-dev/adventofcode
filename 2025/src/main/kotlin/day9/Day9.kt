package day9

import com.adventofcode.utils.Point
import com.adventofcode.utils.col
import com.adventofcode.utils.line
import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import java.util.stream.IntStream
import kotlin.math.abs

fun main() {
    val input = readInput("day9.input")
    val points = parseInput(input)

    runPuzzle(1) {
        findTheLargestRectangleArea(points)
    }
}

fun parseInput(input: List<String>): List<Point> {
    return input.map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Pair(x, y)
    }
}

fun calculateArea(p1: Point, p2: Point): Long {
    val a = abs(p2.col - p1.col) + 1
    val b = abs(p2.line - p1.line) + 1
    return a.toLong() * b.toLong()
}

fun findTheLargestRectangleArea(points: List<Point>): Long {
     return IntStream.range(0, points.size)
        .boxed()
        .parallel()
        .flatMap { i ->
            val a = points[i]
            points.subList(i + 1, points.size)
                .stream()
                .map { b -> calculateArea(a, b) }
        }
        .max(Long::compareTo)
        .orElseThrow()
}