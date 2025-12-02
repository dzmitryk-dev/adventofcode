package day2

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = parseInput(readInput("day2.input").first())
    runPuzzle(1) {
        part1(input)
    }
}

fun parseInput(input: String): List<Pair<String, String>> {
    return input.split(",")
        .map { rangeString ->
            val (start, end) = rangeString.split("-")
            start to end
        }
}

fun verifyRange(start: String, end: String): List<Long> {
    if (start.length % 2 > 0 && end.length % 2 > 0 && end.length - start.length < 2) {
        // If both numbers have odd length and the same scale there is no possible invalid ids
        return emptyList()
    }

    val validStart = if (start.length % 2 > 0) {
        "1" + "0".repeat(start.length)
    } else {
        start
    }

    val validEnd = if(end.length % 2 > 0) {
        "9".repeat(end.length - 1)
    } else {
        end
    }

    val validStartLong = validStart.toLong()
    val validEndLong = validEnd.toLong()

    var startFirstPart = validStart.take(validStart.length / 2).toLong()

    val candidates = mutableListOf<Long>()
    do {
        val candidate = "$startFirstPart".repeat(2).toLong()
        if (candidate < validStartLong) {
            startFirstPart++
            continue
        }
        if (candidate > validEndLong) {
            break
        }
        candidates.add(candidate)
        startFirstPart++
    } while (true)

    return candidates
}

fun part1(input: List<Pair<String, String>>): Long {
    return input.stream()
        .parallel()
        .map { (start, end) -> verifyRange(start, end).sum() }
        .mapToLong { it }
        .sum()
}