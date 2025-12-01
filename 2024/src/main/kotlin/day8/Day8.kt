package adventofcode2024.day8

import adventofcode2024.Point
import adventofcode2024.col
import adventofcode2024.line
import adventofcode2024.readInput
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

fun main() {
    val field = parseInput(readInput("day8.input"))

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        findAntinodes(field).size
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        findAntinodesInRegion(field).size
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )

}

fun parseInput(input: List<String>): List<CharArray> {
    return input.map { it.toCharArray() }
}

fun findSymmetricPoints(pointA: Point, pointB: Point): List<Point> {
    // Вычисляем расстояние между точками A и B
    val distanceAB = sqrt(((pointB. line - pointA.line).toDouble().pow(2) + (pointB.col - pointA.col).toDouble().pow(2)))

    // Вычисляем вектор направления от A к B
    val dx = pointB.line - pointA.line
    val dy = pointB.col - pointA.col

    // Нормализуем вектор направления
    val magnitude = sqrt(dx.toDouble().pow(2) + dy.toDouble().pow(2))
    val unitDx = dx / magnitude
    val unitDy = dy / magnitude

    // Вычисляем новые точки, отстоящие на расстоянии distanceAB от A и B в противоположных направлениях
    val point1 = Point(
        (pointA.line - unitDx * distanceAB).roundToInt(),
        (pointA.col - unitDy * distanceAB).roundToInt()
    )
    val point2 = Point(
        (pointB.line + unitDx * distanceAB).roundToInt(),
        (pointB.col + unitDy * distanceAB).roundToInt()
    )

    return listOf(point1, point2)
}

fun findAntinodes(input: List<CharArray>): Set<Point> {
    val symbols = input.flatMap { it.toSet() }.filterNot { it == '.' }.toSet()

    val antinodes = mutableSetOf<Point>()
    for (s in symbols) {
        val points = buildList {
            input.forEachIndexed { line, chars ->
                val col = chars.indexOf(s)
                if (col != -1) {
                    add(Point(line, col))
                }
            }
        }
        // Call findSymmetricPoints for each pair of points
        for ((index, p) in points.withIndex()) {
            for (p2 in points.subList(index + 1, points.size)) {
                val symmetricPoints = findSymmetricPoints(p, p2)
                    .filter { (l, c) -> l in input.indices && c in input[l].indices }
                antinodes.addAll(symmetricPoints)
            }
        }
    }

    return antinodes
}

fun findSymmetricPointsInRegion(
    pointA: Point,
    pointB: Point,
    height: Int,
    width: Int
): List<Point> {
    val points = mutableListOf<Point>()
    val dx = pointB.first - pointA.first
    val dy = pointB.second - pointA.second

    if (dx == 0 && dy == 0) return points

    val gcd = abs(gcd(dx, dy))
    val stepX = dx / gcd
    val stepY = dy / gcd

    var x = pointA.first
    var y = pointA.second

    // Forward direction
    while (x in 0 until width && y in 0 until height) {
        points.add(Point(x, y))
        x += stepX
        y += stepY
    }

    x = pointA.first - stepX
    y = pointA.second - stepY

    // Backward direction
    while (x in 0 until width && y in 0 until height) {
        points.add(Point(x, y))
        x -= stepX
        y -= stepY
    }

    return points
}

private fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun findAntinodesInRegion(input: List<CharArray>): Set<Point> {
    val symbols = input.flatMap { it.toSet() }.filterNot { it == '.' }.toSet()

    val antinodes = mutableSetOf<Point>()
    for (s in symbols) {
        val points = buildList {
            input.forEachIndexed { line, chars ->
                val col = chars.indexOf(s)
                if (col != -1) {
                    add(Point(line, col))
                }
            }
        }
        // Call findSymmetricPoints for each pair of points
        for ((index, p) in points.withIndex()) {
            for (p2 in points.subList(index + 1, points.size)) {
                val symmetricPoints = findSymmetricPointsInRegion(p, p2, input.size, input[0].size)
                    .filter { (l, c) -> l in input.indices && c in input[l].indices }
                antinodes.addAll(symmetricPoints)
            }
        }

        antinodes.addAll(points)
    }

    return antinodes
}