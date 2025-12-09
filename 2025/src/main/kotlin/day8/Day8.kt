package day8

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import java.util.stream.IntStream
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val input = readInput("day8.input")

    runPuzzle(1) {
        part1(input, connectionsLimit = 1000, circuitsLimit = 3)
    }

    runPuzzle(2) {
        part2(input)
    }
}

data class Point(val x: Int, val y: Int, val z: Int)

data class Connection(val from: Point, val to: Point, val distance: Double)

fun parseInput(input: List<String>): List<Point> {
    return input.map { l ->
        l.split(",").let { (x, y, z) ->
            Point(x.toInt(), y.toInt(), z.toInt())
        }
    }
}

fun calculateDistance(p1: Point, p2: Point): Double {
    return sqrt(
        (p2.x - p1.x).toDouble().pow(2.0) +
        (p2.y - p1.y).toDouble().pow(2.0) +
        (p2.z - p1.z).toDouble().pow(2.0)
    )
}

fun buildConnections(points: List<Point>): List<Connection> {
    return IntStream.range(0, points.size)
        .boxed()
        .parallel()
        .flatMap { i ->
            val a = points[i]
            points.subList(i + 1, points.size)
                .stream()
                .map { b ->
                    val distance = calculateDistance(a, b)
                    Connection(a, b, distance)
                }
        }
        .sorted { a, b -> a.distance.compareTo(b.distance) }
        .toList()
}

fun buildCircuits(
    connections: List<Connection>,
    limit: Int,
): List<Set<Point>> {
    val point2circuitId = mutableMapOf<Point, Int>()
    val circuitId2points = mutableMapOf<Int, MutableSet<Point>>()

    var circuitIdCounter = 0

    for (connection in connections.subList(0, limit)) {
        val fromId = point2circuitId[connection.from]
        val toId = point2circuitId[connection.to]

        when {
            fromId == null && toId == null -> {
                point2circuitId[connection.from] = circuitIdCounter
                point2circuitId[connection.to] = circuitIdCounter
                circuitId2points[circuitIdCounter] = mutableSetOf(connection.from, connection.to)
                circuitIdCounter++
            }
            fromId != null && toId == null -> {
                point2circuitId[connection.to] = fromId
                circuitId2points[fromId]!!.add(connection.to)
            }
            fromId == null && toId != null -> {
                point2circuitId[connection.from] = toId
                circuitId2points[toId]!!.add(connection.from)
            }
            fromId != null && toId != null && fromId != toId -> {
                val fromPoints = circuitId2points[fromId]!!
                val toPoints = circuitId2points[toId]!!

                // Merge circuits
                fromPoints.forEach { p -> point2circuitId[p] = toId }
                toPoints.addAll(fromPoints)
                circuitId2points.remove(fromId)
            }
        }
    }

    return circuitId2points.values.toList().sortedByDescending { it.size }
}

fun part1(input: List<String>, connectionsLimit: Int, circuitsLimit: Int): Int {
    val points = parseInput(input)
    val connections = buildConnections(points)
    val circuits = buildCircuits(connections, limit = connectionsLimit)

    return circuits.take(circuitsLimit).map { it.size }.reduce { a, b -> a * b }
}

fun buildFullCircuit(
    connections: List<Connection>,
    points: List<Point>
): Int {
    val point2circuitId = mutableMapOf<Point, Int>()
    val circuitId2points = mutableMapOf<Int, MutableSet<Point>>()

    var circuitIdCounter = 0

    var value = 0

    for (connection in connections) {
        val fromId = point2circuitId[connection.from]
        val toId = point2circuitId[connection.to]

        when {
            fromId == null && toId == null -> {
                point2circuitId[connection.from] = circuitIdCounter
                point2circuitId[connection.to] = circuitIdCounter
                circuitId2points[circuitIdCounter] = mutableSetOf(connection.from, connection.to)
                circuitIdCounter++
            }
            fromId != null && toId == null -> {
                point2circuitId[connection.to] = fromId
                circuitId2points[fromId]!!.add(connection.to)
            }
            fromId == null && toId != null -> {
                point2circuitId[connection.from] = toId
                circuitId2points[toId]!!.add(connection.from)
            }
            fromId != null && toId != null && fromId != toId -> {
                val fromPoints = circuitId2points[fromId]!!
                val toPoints = circuitId2points[toId]!!

                // Merge circuits
                fromPoints.forEach { p -> point2circuitId[p] = toId }
                toPoints.addAll(fromPoints)
                circuitId2points.remove(fromId)
            }
        }

        if (circuitId2points.size == 1 && circuitId2points.entries.first().value.containsAll(points)) {
            value = connection.from.x * connection.to.x
            break
        }
    }

    return value
}

fun part2(input: List<String>): Int {
    val points = parseInput(input)
    val connections = buildConnections(points)
    return buildFullCircuit(connections, points)
}