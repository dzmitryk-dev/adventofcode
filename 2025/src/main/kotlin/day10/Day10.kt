package day10

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle
import com.microsoft.z3.ArithExpr
import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import java.util.BitSet

fun main() {
    val input = readInput("day10.input")

    runPuzzle(1) {
        part1(input)
    }

    runPuzzle(2) {
        part2Optimized(input)
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

// Part 2 Optimized - Uses Z3 SMT Solver

fun part2Optimized(input: List<String>): Long {
    return parseInput(input).sumOf { machineState ->
        solveJoltageProblemV2(machineState.buttons, machineState.joltage).toLong()
    }
}

/**
 * Solves the joltage problem using Z3 SMT solver.
 * 
 * The problem is modeled as a system of linear equations:
 * - Variables: number of times each button is pressed
 * - Constraints: sum of button presses affecting each counter must equal target joltage
 * - Objective: minimize total button presses
 * 
 * Example: For buttons [(3), (1,3), (2)] and target joltages [3, 5, 4, 7]:
 * - Let x0, x1, x2, ... be the number of times each button is pressed
 * - Counter 0: x5 + x6 = 3 (buttons (0,2) and (0,1) affect counter 0)
 * - Counter 1: x1 + x6 = 5 (buttons (1,3) and (0,1) affect counter 1)
 * - Counter 2: x2 + x3 + x4 + x5 = 4 (multiple buttons affect counter 2)
 * - Counter 3: x0 + x1 + x3 = 7 (buttons (3), (1,3), and (2,3) affect counter 3)
 * - Minimize: x0 + x1 + x2 + x3 + x4 + x5 + x6
 * 
 * Z3 efficiently solves this integer linear programming problem.
 */
fun solveJoltageProblemV2(buttons: Array<ByteArray>, targetJoltages: IntArray): Int {
    val ctx = Context()
    val solver = ctx.mkOptimize()
    
    // Create integer variables for each button (number of times pressed)
    val buttonPresses = buttons.indices.map { i ->
        ctx.mkIntConst("button_$i")
    }
    
    // Each button press count must be non-negative
    buttonPresses.forEach { buttonVar ->
        solver.Add(ctx.mkGe(buttonVar, ctx.mkInt(0)))
    }
    
    // For each counter/joltage requirement, add constraint
    targetJoltages.indices.forEach { counterIdx ->
        val counterContributions = mutableListOf<ArithExpr<*>>()
        
        // Find which buttons affect this counter
        buttons.forEachIndexed { buttonIdx, button ->
            if (button.contains(counterIdx.toByte())) {
                counterContributions.add(buttonPresses[buttonIdx])
            }
        }
        
        if (counterContributions.isNotEmpty()) {
            // Sum of button presses for this counter must equal target
            val sum = if (counterContributions.size == 1) {
                counterContributions[0]
            } else {
                ctx.mkAdd(*counterContributions.toTypedArray())
            }
            solver.Add(ctx.mkEq(sum, ctx.mkInt(targetJoltages[counterIdx])))
        } else {
            // If no button affects this counter, it must be 0
            if (targetJoltages[counterIdx] != 0) {
                ctx.close()
                return -1 // No solution possible
            }
        }
    }
    
    // Minimize total button presses
    val totalPresses = if (buttonPresses.size == 1) {
        buttonPresses[0]
    } else {
        ctx.mkAdd(*buttonPresses.toTypedArray())
    }
    solver.MkMinimize(totalPresses)
    
    val result = solver.Check()
    
    val answer = if (result == Status.SATISFIABLE) {
        val model = solver.model
        val total = model.eval(totalPresses, false) as IntNum
        total.int
    } else {
        -1
    }
    
    ctx.close()
    return answer
}
