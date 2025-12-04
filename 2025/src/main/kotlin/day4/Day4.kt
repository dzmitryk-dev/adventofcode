package day4

import com.adventofcode.utils.*

fun main() {
    val input = readInput("day4.input")

    runPuzzle(1) {
        part1(input)
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