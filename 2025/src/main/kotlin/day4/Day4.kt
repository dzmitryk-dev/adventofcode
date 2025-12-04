package day4

import com.adventofcode.utils.*

fun main() {
    val input = readInput("day4.input")

    runPuzzle(1) {
        part1(input)
    }

    runPuzzle(2) {
        part2(input)
    }
}

fun findAccessibleRolls(list: List<CharSequence>): Collection<Point> {
    val accessibleRolls = mutableListOf<Point>()
    list.forEachIndexed { line, lineString ->
        lineString.forEachIndexed { col, char ->
            if (char == '@') {
                val point = Point(line, col)
                val rollsAround = point.allPointsAround()
//                    .onEach { println(it) }
                    .mapNotNull { p -> list.getOrNull(p.line)?.getOrNull(p.col) }
//                    .onEach { println(it) }
                    .count { c -> c == '@' }
                if (rollsAround < 4) {
                    accessibleRolls.add(point)
                }
            }
        }
    }
    return accessibleRolls
}

fun part1(list: List<CharSequence>): Int {
    val accessibleRolls = findAccessibleRolls(list)
    return accessibleRolls.size
}

fun removeRolls(list: List<CharSequence>, points: Collection<Point>): List<CharSequence> {
    return list.mapIndexed { line, lineString ->
        val chars = lineString.toString().toCharArray()
        points.filter { p -> p.line ==  line }
            .forEach { p ->
                chars[p.col] = '.'
            }
        String(chars)
    }
}

fun part2(list: List<CharSequence>): Int {
    var removedRolls = 0
    var field = list

    do {
        val accessibleRolls = findAccessibleRolls(field)
        val afterRemoval = removeRolls(field, accessibleRolls)

        removedRolls += accessibleRolls.size
        field = afterRemoval
    } while (accessibleRolls.isNotEmpty())


    return removedRolls
}