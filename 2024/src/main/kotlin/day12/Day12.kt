package adventofcode2024.day12

import adventofcode2024.*

fun main() {

    val input = readInput("day12.input")

    runPuzzle(1) {
        calculateTotalPrice(parseInput(input))
    }
}

fun parseInput(input: List<String>): List<CharArray> =
    input.map { it.toCharArray() }

fun findRegions(field: List<CharArray>, symbol: Char): List<Set<Point>> {
    val points: Set<Point> = field.foldIndexed(mutableSetOf()) { line, acc, chars ->
        chars.forEachIndexed { col, c ->
            if (symbol == c) {
                acc.add(Point(line, col))
            }
        }
        acc
    }

    return buildList {
        val pointsToHandle = points.toMutableSet()

        do {
            val p = pointsToHandle.first()
            val region = findRegion(points, setOf(p))

            add(region)
            pointsToHandle.removeAll(region)

        } while (pointsToHandle.isNotEmpty())
    }
}

private tailrec fun findRegion(points: Set<Point>, candidates: Set<Point>, region: MutableSet<Point> = mutableSetOf()): Set<Point> {
    if (candidates.isEmpty()) {
        return region
    }
    region.addAll(candidates)
    val newCandidate = candidates.asSequence()
        .flatMap { it.pointsAround() }
        .toSet()
        .filter { it in points }
        .filter { it !in region }
        .toSet()
    return findRegion(points, newCandidate, region)
}

fun calculatePrice(region: Set<Point>): Int {
    val perimeter = region.sumOf { p -> p.pointsAround().filterNot { sp -> sp in region }.count() }
    val square = region.size
    return perimeter * square
}

fun calculateTotalPrice(field: List<CharArray>): Int {
    val symbols: Set<Char> = field.fold(mutableSetOf()) { acc, chars ->
        acc.apply {
            addAll(chars.toSet())
        }
    }

    return symbols.flatMap { symbol ->
        findRegions(field, symbol)
    }.sumOf { region ->
        calculatePrice(region)
    }
}

private fun findSides(points: Set<Point>): Int {
   TODO()
}

fun calculateBulkPrice(region: Set<Point>): Int {
    val sides = findSides(region)
    val square = region.size
    return sides * square
}

fun calculateTotalBulkPrice(field: List<CharArray>): Int {
    val symbols: Set<Char> = field.fold(mutableSetOf()) { acc, chars ->
        acc.apply {
            addAll(chars.toSet())
        }
    }

    return symbols.flatMap { symbol ->
        findRegions(field, symbol)
    }.sumOf { region ->
        calculateBulkPrice(region)
    }
}