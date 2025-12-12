package day11

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val graph = parseInput(readInput("day11.input"))

    runPuzzle(1) {
        countPaths(graph)
    }

    runPuzzle(2) {
        part2(graph)
    }
}

fun parseInput(input: List<String>): Map<String, List<String>> {
    return input.associate { line ->
        val (key, values) = line.split(": ")
        key to values.split(" ")
    }
}

fun countPaths(graph: Map<String, List<String>>, start: String = "you", end: String = "out"): Int {
    tailrec fun search(graph: Map<String, List<String>>, nodes: List<String>, counter: Int): Int {
        if (nodes.isEmpty()) return counter
        val newNodes = nodes.flatMap { i -> graph.getOrDefault(i, emptyList()) }
        val newCounter = counter + newNodes.count { it == end }
        return search(graph, newNodes.filter { it != end }, newCounter)
    }

    return search(graph, listOf(start), 0)
}

fun countPaths2(graph: Map<String, List<String>>, start: String, end: String): Int {
    tailrec fun search(graph: Map<String, List<String>>, nodes: Map<String, Int>, end: String, counter: Int = 0): Int {
        if (nodes.isEmpty()) return counter
        val newNodes = nodes.map { n ->
            graph.getOrDefault(n.key, emptyList())
                .associateWith { k -> n.value }
        }.fold(mutableMapOf<String, Int>()) { acc, map ->
            map.forEach { (k, v) ->
                acc[k] = acc.getOrDefault(k, 0) + v
            }
            acc
        }

        val newCounter = counter + newNodes.getOrDefault(end, 0)
        return search(graph, newNodes.filter { it.key != end }, end, newCounter)
    }

    return search(graph, mapOf(start to 1), end)
}

fun part2(graph: Map<String, List<String>>): Long {
    val a = countPaths2(graph, start = "svr", end = "fft")
    val b = countPaths2(graph, start = "fft", end = "dac")
    val c = countPaths2(graph, start = "dac", end = "out")

    val a1 = countPaths2(graph, start = "svr", end = "dac")
    val b2 = countPaths2(graph, start = "dac", end = "fft")
    val c2 = countPaths2(graph, start = "fft", end = "out")

    return a.toLong() * b * c + a1.toLong() * b2 * c2
}