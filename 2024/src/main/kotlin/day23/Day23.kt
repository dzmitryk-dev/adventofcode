package adventofcode2024.day23

import adventofcode2024.readInput
import adventofcode2024.runPuzzle

fun main() {
    val input = readInput("day23.input")

    runPuzzle(1) {
        part1(input)
    }
}

fun parseInput(input: List<String>): List<Set<String>> =
    input.filterNot { it.isEmpty() }.map { it.split("-").toSet() }

fun findGroups(pairs: List<Set<String>>): List<Set<String>> {
    return buildSet {
        pairs.forEachIndexed { index, firstPair ->
            val secondPairCandidates = pairs.subList(index + 1, pairs.size).filter { p -> p.any { it in firstPair } }

            for (candidate in secondPairCandidates) {
                val newGroup = firstPair + candidate

                val thirdPairCandidates = secondPairCandidates.toMutableSet().apply {
                    remove(candidate)
                }

                val thirdCandidate = thirdPairCandidates.find { c -> c.all { it in newGroup } }
                if (thirdCandidate != null) {
                    add(newGroup)
                }
            }
        }
    }.toList()
}

fun groupFilter(group: Set<String>): Boolean = group.any { it.startsWith('t') }

fun part1(input: List<String>): Int =
    findGroups(parseInput(input)).count(::groupFilter)