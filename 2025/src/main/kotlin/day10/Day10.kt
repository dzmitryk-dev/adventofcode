package day10

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import java.util.BitSet

fun main() {
    val input = readInput("day10.input")

    runPuzzle(1) {
        part1(input)
    }

    runPuzzle(2) {
        part2(input)
    }
}

data class MachineState(
    val expectedIndicator: BitSet,
    val buttons: Array<ByteArray>,
    val joltage: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MachineState

        if (expectedIndicator != other.expectedIndicator) return false
        if (!buttons.contentDeepEquals(other.buttons)) return false
        if (!joltage.contentEquals(other.joltage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = expectedIndicator.hashCode()
        result = 31 * result + buttons.contentDeepHashCode()
        result = 31 * result + joltage.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "MachineState(expectedIndicator=$expectedIndicator, buttons=${buttons.contentToString()}, joltage=${joltage.contentToString()})"
    }
}

fun parseInput(input: List<String>): List<MachineState> {
    return input.map { line ->
        // Extract content within []
        val bracketContent = """\[([^\]]+)]""".toRegex().find(line)?.groupValues?.get(1)
        // Result: ".##."
        val expectedIndicator = BitSet().apply {
            bracketContent!!.forEachIndexed { index, ch ->
                if (ch == '#') this.set(index)
            }
        }

        // Extract all content within ()
        val parenthesesContents = """\(([^)]+)\)""".toRegex().findAll(line).map { it.groupValues[1] }.toList()
        // Result: ["3", "1,3", "2", "2,3", "0,2", "0,1"]
        val buttons = parenthesesContents.map { s ->
            s.split(",").map { it.toByte() }.toByteArray()
        }.toTypedArray()

        // Extract content within {}
        val braceContent = """\{([^}]+)}""".toRegex().find(line)?.groupValues?.get(1)
        // Result: "3,5,4,7"
        val joltage = braceContent!!.split(",").map { it.toInt() }.toIntArray()

        MachineState(
            expectedIndicator = expectedIndicator, // Convert bracketContent to BitSet
            buttons = buttons, // Convert parenthesesContents to Array<BitSet>
            joltage = joltage // Convert braceContent to IntArray
        )
    }
}

tailrec fun findButtonCombinations(
    expected: BitSet,
    buttons: Array<ByteArray>,
    generation: Set<BitSet> = setOf(BitSet()),
    generationCounter: Int = 0,
): Int {
    val nextGeneration = generation.flatMap { state ->
        buttons.map { button ->
            val newState = state.clone() as BitSet
            button.forEach { index ->
                newState.flip(index.toInt())
            }
            newState
        }
    }.toSet()
    val nextGenerationCounter = generationCounter + 1
    return if (nextGeneration.contains(expected)) {
        nextGenerationCounter
    } else {
       findButtonCombinations(expected, buttons, nextGeneration, nextGenerationCounter)
    }
}

fun part1(input: List<String>): Long {
    return parseInput(input).map { machineState ->
        findButtonCombinations(machineState.expectedIndicator, machineState.buttons)
    }.sumOf { it.toLong() }
}


// Part 2 Here

fun part2(input: List<String>): Long {
    return parseInput(input).sumOf { machineState ->
        solveJoltageProblem(machineState.buttons, machineState.joltage).toLong()
    }
}

fun solveJoltageProblem(buttons: Array<ByteArray>, targetJoltages: IntArray): Int {
    val numButtons = buttons.size
    val numCounters = targetJoltages.size

    data class State(val counters: List<Int>, val presses: Int) : Comparable<State> {
        override fun compareTo(other: State): Int = this.presses.compareTo(other.presses)
    }

    val queue = java.util.PriorityQueue<State>()
    val visited = mutableSetOf<List<Int>>()

    queue.offer(State(List(numCounters) { 0 }, 0))

    while (queue.isNotEmpty()) {
        val (counters, presses) = queue.poll()

        // Found solution!
        if (counters == targetJoltages.toList()) {
            return presses
        }

        // Skip if visited
        if (visited.contains(counters)) continue
        visited.add(counters)

        // Skip if any counter exceeds target
        if (counters.indices.any { counters[it] > targetJoltages[it] }) continue

        // Try each button
        for (buttonIdx in 0 until numButtons) {
            val newCounters = counters.toMutableList()
            buttons[buttonIdx].forEach { counterIdx ->
                newCounters[counterIdx.toInt()]++
            }

            // Only add if valid (no counter exceeds target)
            if (newCounters.indices.all { newCounters[it] <= targetJoltages[it] }) {
                queue.offer(State(newCounters, presses + 1))
            }
        }
    }

    return -1
}
