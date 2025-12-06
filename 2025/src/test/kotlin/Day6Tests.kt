import day6.Problem
import day6.Problem.Operation
import day6.calculation
import day6.parseInput
import day6.parseInput2
import day6.part1
import day6.part2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

class Day6Tests {

    @Test
    fun testParsing() {
        val expected = listOf(
            Problem(
                numbers = longArrayOf(123, 45, 6),
                operation = Operation.MULT,
            ),
            Problem(
                numbers = longArrayOf(328, 64, 98),
                operation = Operation.ADD,
            ),
            Problem(
                numbers = longArrayOf(51, 387, 215),
                operation = Operation.MULT,
            ),
            Problem(
                numbers = longArrayOf(64, 23, 314),
                operation = Operation.ADD,
            ),
        )

        val actual = parseInput(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("computationTestSource")
    fun testComputation(testCase: Pair<Problem, Long>) {
        val (problem, expected) = testCase

        val actual = calculation(problem)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testPart1() {
        val expected = 4277556L

        val actual = part1(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testParsing2() {
        val expected = listOf(
            Problem(
                numbers = longArrayOf(4, 431, 623),
                operation = Operation.ADD,
            ),
            Problem(
                numbers = longArrayOf(175, 581, 32),
                operation = Operation.MULT,
            ),
            Problem(
                numbers = longArrayOf(8, 248, 369),
                operation = Operation.ADD,
            ),
            Problem(
                numbers = longArrayOf(356, 24, 1),
                operation = Operation.MULT,
            ),
        )

        val actual = parseInput2(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testPart2() {
        val expected = 3263827L

        val actual = part2(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        val testInput = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent()

        @JvmStatic
        fun computationTestSource() = listOf(
            Problem(
                numbers = longArrayOf(123, 45, 6),
                operation = Operation.MULT,
            ) to 33210,
            Problem(
                numbers = longArrayOf(328, 64, 98),
                operation = Operation.ADD,
            ) to 490,
            Problem(
                numbers = longArrayOf(51, 387, 215),
                operation = Operation.MULT,
            ) to 4243455,
            Problem(
                numbers = longArrayOf(64, 23, 314),
                operation = Operation.ADD,
            ) to 401,
        )
    }

}