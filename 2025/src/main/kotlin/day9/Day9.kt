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

// ============================================================================
// Part 2: Coordinate Compression Algorithm
// ============================================================================

/**
 * Build a compressed grid representation of the polygon.
 * Uses coordinate compression to reduce problem size.
 */
fun buildCompressedGrid(contour: List<Point>): Triple<Array<CharArray>, Map<Int, Int>, Map<Int, Int>> {
    // Step 1: Extract and sort unique coordinates
    val uniqueCols = contour.map { it.col }.toSet().sorted()
    val uniqueLines = contour.map { it.line }.toSet().sorted()

    // Step 2: Create coordinate mappings (original -> compressed index)
    val colToIndex = uniqueCols.withIndex().associate { it.value to it.index }
    val lineToIndex = uniqueLines.withIndex().associate { it.value to it.index }

    // Step 3: Initialize grid (compressed coordinates)
    val height = uniqueLines.size
    val width = uniqueCols.size
    val grid = Array(height) { CharArray(width) { '.' } }

    // Step 4: Mark contour edges on compressed grid
    for (i in contour.indices) {
        val p1 = contour[i]
        val p2 = contour[(i + 1) % contour.size]

        val ix1 = colToIndex[p1.col]!!
        val iy1 = lineToIndex[p1.line]!!
        val ix2 = colToIndex[p2.col]!!
        val iy2 = lineToIndex[p2.line]!!

        // Mark vertices
        grid[iy1][ix1] = 'X'
        grid[iy2][ix2] = 'X'

        // Mark edge between vertices
        if (ix1 == ix2) {
            // Vertical edge
            val minY = minOf(iy1, iy2)
            val maxY = maxOf(iy1, iy2)
            for (y in (minY + 1) until maxY) {
                grid[y][ix1] = 'O'
            }
        } else {
            // Horizontal edge
            val minX = minOf(ix1, ix2)
            val maxX = maxOf(ix1, ix2)
            for (x in (minX + 1) until maxX) {
                grid[iy1][x] = 'O'
            }
        }
    }

    // Step 5: For remaining cells, use point-in-polygon check
    // Map compressed indices back to actual coordinates for checking
    val indexToCol = uniqueCols
    val indexToLine = uniqueLines

    for (y in 0 until height) {
        for (x in 0 until width) {
            if (grid[y][x] == '.') {
                // Convert compressed coordinates to actual coordinates
                val actualPoint = Point(indexToLine[y], indexToCol[x])
                // Use ray-casting point-in-polygon
                if (isPointInsidePolygon(actualPoint, contour)) {
                    grid[y][x] = 'Z'
                }
            }
        }
    }

    return Triple(grid, colToIndex, lineToIndex)
}

/**
 * Ray-casting point-in-polygon test.
 */
private fun isPointInsidePolygon(p: Point, polygon: List<Point>): Boolean {
    val x = p.col
    val y = p.line

    var inside = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        val xi = polygon[i].col
        val yi = polygon[i].line
        val xj = polygon[j].col
        val yj = polygon[j].line

        if (((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
            inside = !inside
        }
        j = i
    }
    return inside
}

/**
 * Check if rectangle is inside contour using compressed grid.
 * This is fast because it operates on the small compressed grid.
 */
fun isRectangleInsideContourCompressed(
    p1: Point,
    p2: Point,
    grid: Array<CharArray>,
    colToIndex: Map<Int, Int>,
    lineToIndex: Map<Int, Int>
): Boolean {
    // Get compressed indices
    val ix1 = colToIndex[p1.col] ?: return false
    val iy1 = lineToIndex[p1.line] ?: return false
    val ix2 = colToIndex[p2.col] ?: return false
    val iy2 = lineToIndex[p2.line] ?: return false

    val minX = minOf(ix1, ix2)
    val maxX = maxOf(ix1, ix2)
    val minY = minOf(iy1, iy2)
    val maxY = maxOf(iy1, iy2)

    // Check all cells in compressed rectangle
    // If any cell is '.', it's outside the polygon
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (grid[y][x] == '.') {
                return false
            }
        }
    }

    return true
}

/**
 * Find largest rectangle inside contour using coordinate compression.
 * Area is calculated using ORIGINAL coordinates, not compressed ones.
 */
fun findTheLargestRectangleAreaInsideContour(points: List<Point>): Long {
    val (grid, colToIndex, lineToIndex) = buildCompressedGrid(points)

    var maxArea = 0L
    val n = points.size

    for (i in 0 until n) {
        val a = points[i]
        for (j in i + 1 until n) {
            val b = points[j]

            if (isRectangleInsideContourCompressed(a, b, grid, colToIndex, lineToIndex)) {
                val width = abs(a.col - b.col) + 1
                val height = abs(a.line - b.line) + 1
                val area = width.toLong() * height.toLong()
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }
    }

    return maxArea
}

/**
 * Check if rectangle is inside contour (test wrapper).
 * Builds compressed grid for single rectangle check.
 */
fun isRectangleInsideContour(p1: Point, p2: Point, contour: List<Point>): Boolean {
    val (grid, colToIndex, lineToIndex) = buildCompressedGrid(contour)
    return isRectangleInsideContourCompressed(p1, p2, grid, colToIndex, lineToIndex)
}

