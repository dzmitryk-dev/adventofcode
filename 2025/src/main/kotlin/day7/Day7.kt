package day7

import com.adventofcode.utils.readInput
import com.adventofcode.utils.Point
import com.adventofcode.utils.col
import com.adventofcode.utils.line
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day7.input").map { it.toCharArray() }

    runPuzzle(1) {
        part1(input)
    }
}

data class Trace(
    val points: Set<Point> = emptySet(),
    val splitCounts: Int = 0
)

fun traceTachyon(field: List<CharArray>): Trace {
    val start = Point(0, field.first().indexOfFirst { it == 'S' })

    data class Accumulator(
        val trace: Set<Point> = emptySet(),
        val candidates: Set<Point> = emptySet(),
        val splitCounts: Int = 0
    )

    val result = field.drop(1)
        .foldIndexed(Accumulator(candidates = setOf(start))) { index, accumulator, line ->
            var splitsCounts = 0
            val newCandidates = accumulator.candidates.flatMap { p ->
                if (line[p.col] == '^') {
                    splitsCounts += 1
                    listOf(
                        p.copy(first = index + 1, second = p.col - 1),
                        p.copy(first = index + 1, second = p.col + 1)
                    )
                } else {
                    listOf(p.copy(first = index + 1))
                }
            }.toSet()
            accumulator.copy(
                trace = accumulator.trace + accumulator.candidates,
                candidates = newCandidates,
                splitCounts = accumulator.splitCounts + splitsCounts
            )
        }

    return Trace(
        points = result.trace + result.candidates,
        splitCounts = result.splitCounts
    )
}

fun visualizeTracing(field: List<CharArray>, trace: Set<Point>): String {
    return trace.fold(field.toTypedArray()) { field, p ->
        field[p.line][p.col] = '|'
        field
    }.joinToString("\n") { l -> l.joinToString("") }
}

fun part1(input: List<CharArray>): Int {
    val result = traceTachyon(input)
    visualizeTracing(input, result.points)
    return result.splitCounts
}