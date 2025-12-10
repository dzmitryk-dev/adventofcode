package day10

import com.adventofcode.utils.readInput
import java.util.BitSet

fun main() {
    val input = readInput("day10.input")
    println("Day 10 solution goes here.")
}

data class MachineState(
    val expectedIndicator: BitSet,
    val buttons: Array<BitSet>,
    val joltage: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MachineState

        if (expectedIndicator != other.expectedIndicator) return false
        if (!buttons.contentEquals(other.buttons)) return false
        if (!joltage.contentEquals(other.joltage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = expectedIndicator.hashCode()
        result = 31 * result + buttons.contentHashCode()
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
            BitSet().apply {
                s.split(",").forEach { this.set(it.toInt()) }
            }
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