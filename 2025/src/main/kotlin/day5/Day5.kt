package day5

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day5.input")

    runPuzzle(1) {
        val parsedInput = parseInput(input)
        part1(parsedInput)
    }

    runPuzzle(2) {
        val parsedInput = parseInput(input)
        part2(parsedInput)
    }

}

data class ParsedInput(
    val ranges: List<LongRange>,
    val ids: List<Long>
)

fun parseInput(input: List<String>): ParsedInput {
    val separatorIndex = input.indexOfFirst { it.isBlank() }
    val ranges = input.subList(0, separatorIndex).map {
        val (start, end) = it.split("-").map(String::toLong)
        start .. end
    }
    val numbers = input.subList(separatorIndex + 1, input.size).map(String::toLong)
    return ParsedInput(ranges, numbers)
}

fun part1(parsedInput: ParsedInput): Int {
    return parsedInput.ids.count { id ->
        parsedInput.ranges.any { range ->
            id in range
        }
    }
}

fun part2(parsedInput: ParsedInput): Long {
    return parsedInput.ranges
        .sortedBy { r -> r.first }
        .also {
            println("Ranges: $it")
        }
        .fold(setOf<LongRange>()) { acc, range ->
            var wasMerged = false
            val newAcc = acc.map { r -> if (r.intersects(range)) {
                wasMerged = true
                min(r.first, range.first)..max(r.last, range.last)
            } else {
                r
            } }
            if (!wasMerged) {
                newAcc + listOf(range)
            } else {
                newAcc
            }.toSet()
        }.also {
            println("Merged ranges: $it")
        }.map { range  -> range.last - range.first + 1 }
        .also {
            println("Merged ranges sizes: $it")
        }
        .sum()
}

fun LongRange.intersects(other: LongRange): Boolean {
    return this.first <= other.last && other.first <= this.last
}