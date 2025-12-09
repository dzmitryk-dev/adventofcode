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

    runPuzzle(2) {
        findTheLargestRectangleAreaInsideContour(points)
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

/**
 * Point-in-polygon (inclusive) using ray casting.
 * Returns true if p is inside or on the boundary of the polygon.
 */
private fun isPointInsidePolygon(p: Point, polygon: List<Point>): Boolean {
    val x = p.col
    val y = p.line

    // 1) quick check: on any edge -> inside
    for (i in polygon.indices) {
        val a = polygon[i]
        val b = polygon[(i + 1) % polygon.size]

        val minX = minOf(a.col, b.col)
        val maxX = maxOf(a.col, b.col)
        val minY = minOf(a.line, b.line)
        val maxY = maxOf(a.line, b.line)

        // check collinearity and bounding box
        val dx1 = b.col - a.col
        val dy1 = b.line - a.line
        val dx2 = x - a.col
        val dy2 = y - a.line

        if (dx1 * dy2 == dy1 * dx2 && x in minX..maxX && y in minY..maxY) {
            return true
        }
    }

    // 2) ray casting for strict interior
    var inside = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        val xi = polygon[i].col
        val yi = polygon[i].line
        val xj = polygon[j].col
        val yj = polygon[j].line

        val intersect = ((yi > y) != (yj > y)) &&
                (x < (xj - xi).toDouble() * (y - yi).toDouble() / (yj - yi).toDouble() + xi.toDouble())

        if (intersect) {
            inside = !inside
        }
        j = i
    }
    return inside
}

fun isRectangleInsideContour(p1: Point, p2: Point, contour: List<Point>): Boolean {
    val colRange = minOf(p1.col, p2.col)..maxOf(p1.col, p2.col)
    val lineRange = minOf(p1.line, p2.line)..maxOf(p1.line, p2.line)

    return !lineRange.flatMap { x -> colRange.map { y -> Point(x, y) } }
        .any { point -> !isPointInsidePolygon(point, contour) }
}

fun findTheLargestRectangleAreaInsideContour(points: List<Point>): Long {
     return IntStream.range(0, points.size)
        .boxed()
        .parallel()
        .flatMap { i ->
            val a = points[i]
            points.subList(i + 1, points.size)
                .stream()
                .map { b -> a to b }
        }
        .filter { (p1, p2) -> isRectangleInsideContour(p1, p2, points) }
        .map { (p1, p2) -> calculateArea(p1, p2) }
        .max(Long::compareTo)
        .orElseThrow()
}