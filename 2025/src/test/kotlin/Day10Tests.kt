import day10.MachineState
import day10.findButtonCombinations
import day10.parseInput
import day10.part1
import day10.part2
import day10.part2Optimized
import day10.solveJoltageProblem
import day10.solveJoltageProblemV2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.BitSet
import kotlin.test.Test

class Day10Tests {

    @Test
    fun testParseInput() {
        val expected = listOf(
            // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b0110)),
                buttons = arrayOf(
                    byteArrayOf(3),
                    byteArrayOf(1, 3),
                    byteArrayOf(2),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 2),
                    byteArrayOf(0, 1),
                ),
                joltage = intArrayOf(3, 5, 4, 7)
            ),
            // [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b01000)),
                buttons = arrayOf(
                    byteArrayOf(0, 2, 3, 4),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 4),
                    byteArrayOf(0, 1, 2),
                    byteArrayOf(1, 2, 3, 4),
                ),
                joltage = intArrayOf(7, 5, 12, 7, 2)
            ),
            // [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b101110)),
                buttons = arrayOf(
                    byteArrayOf(0, 1, 2, 3, 4),
                    byteArrayOf(0, 3, 4),
                    byteArrayOf(0, 1, 2, 4, 5),
                    byteArrayOf(1, 2),
                ),
                joltage = intArrayOf(10, 11, 11, 5, 10, 5)
            )
        )

        val actual = parseInput(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("testingButtonCombinationsInput")
    fun testFindButtonCombinations(input: Pair<MachineState, Int>) {
        val (machineState, expected) = input

        val actual = findButtonCombinations(machineState.expectedIndicator, machineState.buttons)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testPart1() {
        val actual = part1(testInput.lines())

        assertThat(actual).isEqualTo(7)
    }

    @ParameterizedTest
    @MethodSource("testingsolveJoltageProblemInput")
    fun testSolveJoltageProblem(input: Pair<MachineState, Int>) {
        val (machineState, expected) = input

        val actual = solveJoltageProblem(machineState.buttons, machineState.joltage)

        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun testPart2() {
        val actual = part2(testInput.lines())

        assertThat(actual).isEqualTo(33)
    }

    @ParameterizedTest
    @MethodSource("testingsolveJoltageProblemInput")
    fun testSolveJoltageProblemV2(input: Pair<MachineState, Int>) {
        val (machineState, expected) = input

        val actual = solveJoltageProblemV2(machineState.buttons, machineState.joltage)

        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun testPart2Optimized() {
        val actual = part2Optimized(testInput.lines())

        assertThat(actual).isEqualTo(33)
    }

    companion object {
        val testInput = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()

        @JvmStatic
        fun testingButtonCombinationsInput() = listOf(
            // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b0110)),
                buttons = arrayOf(
                    byteArrayOf(3),
                    byteArrayOf(1, 3),
                    byteArrayOf(2),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 2),
                    byteArrayOf(0, 1),
                ),
                joltage = intArrayOf(3, 5, 4, 7)
            ) to 2,
            // [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b01000)),
                buttons = arrayOf(
                    byteArrayOf(0, 2, 3, 4),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 4),
                    byteArrayOf(0, 1, 2),
                    byteArrayOf(1, 2, 3, 4),
                ),
                joltage = intArrayOf(7, 5, 12, 7, 2)
            ) to 3,
            // [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b101110)),
                buttons = arrayOf(
                    byteArrayOf(0, 1, 2, 3, 4),
                    byteArrayOf(0, 3, 4),
                    byteArrayOf(0, 1, 2, 4, 5),
                    byteArrayOf(1, 2),
                ),
                joltage = intArrayOf(10, 11, 11, 5, 10, 5)
            ) to 2
        )

        @JvmStatic
        fun testingsolveJoltageProblemInput() = listOf(
            // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b0110)),
                buttons = arrayOf(
                    byteArrayOf(3),
                    byteArrayOf(1, 3),
                    byteArrayOf(2),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 2),
                    byteArrayOf(0, 1),
                ),
                joltage = intArrayOf(3, 5, 4, 7)
            ) to 10,
            // [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b01000)),
                buttons = arrayOf(
                    byteArrayOf(0, 2, 3, 4),
                    byteArrayOf(2, 3),
                    byteArrayOf(0, 4),
                    byteArrayOf(0, 1, 2),
                    byteArrayOf(1, 2, 3, 4),
                ),
                joltage = intArrayOf(7, 5, 12, 7, 2)
            ) to 12,
            // [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b101110)),
                buttons = arrayOf(
                    byteArrayOf(0, 1, 2, 3, 4),
                    byteArrayOf(0, 3, 4),
                    byteArrayOf(0, 1, 2, 4, 5),
                    byteArrayOf(1, 2),
                ),
                joltage = intArrayOf(10, 11, 11, 5, 10, 5)
            ) to 11
        )
    }
}