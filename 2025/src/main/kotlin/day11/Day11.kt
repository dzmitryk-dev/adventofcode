package day11

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val graph = parseInput(readInput("day11.input"))

    runPuzzle(1) {
        countPaths(graph)
    }
}

fun parseInput(input: List<String>): Map<String, List<String>> {
    return input.associate { line ->
        val (key, values) = line.split(": ")
        key to values.split(" ")
    }
}

fun countPaths(graph: Map<String, List<String>>): Int {
    tailrec fun search(graph: Map<String, List<String>>, nodes: List<String>, counter: Int): Int {
        if (nodes.isEmpty()) return counter
        val newNodes = nodes.flatMap { i -> graph.getOrDefault(i, emptyList()) }
        val newCounter = counter + newNodes.count { it == "out" }
        return search(graph, newNodes.filter { it != "out" }, newCounter)
    }

    return search(graph, listOf("you"), 0)
}