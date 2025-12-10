package day10

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import java.util.BitSet

fun main() {
    val input = readInput("day10.input")

    runPuzzle(1) {
        part1(input)
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
}

fun parseInput(input: List<String>): List<MachineState> {
    // Implement parsing logic here
    return input.map { line ->
        val input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"

        // Extract content within []
        val bracketContent = """\[([^\]]+)\]""".toRegex().find(line)?.groupValues?.get(1)
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
        val braceContent = """\{([^}]+)\}""".toRegex().find(line)?.groupValues?.get(1)
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