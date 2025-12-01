import adventofcode2024.day2.*
import adventofcode2024.day2.isSafe
import adventofcode2024.day2.parseInput
import adventofcode2024.day2.puzzle1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day2Tests {

    private val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    @Test
    fun testParseInput() {
        val result = parseInput(testInput.lines())

        assertThat(result).containsExactly(
            listOf(7, 6, 4, 2, 1),
            listOf(1, 2, 7, 8, 9),
            listOf(9, 7, 6, 2, 1),
            listOf(1, 3, 2, 4, 5),
            listOf(8, 6, 4, 4, 1),
            listOf(1, 3, 6, 7, 9)
        )
    }

    @ParameterizedTest
    @MethodSource("isSafeTestCases")
    fun testIsSafe(testCase: SafeTestCase) {
        assertThat(isSafe(testCase.input)).isEqualTo(testCase.expected)
    }

    @Test
    fun testPuzzle1() {
        val input = parseInput(testInput.lines())

        val result = puzzle1(input)

        assertThat(result).isEqualTo(2)
    }

    @ParameterizedTest
    @MethodSource("isSafe2TestCases")
    fun testIsSafe2(testCase: SafeTestCase) {
        assertThat(isSafe2Dump(testCase.input)).isEqualTo(testCase.expected)
    }

    @Test
    fun testPuzzle2() {
        val input = parseInput(testInput.lines())

        val result = puzzle2(input)

        assertThat(result).isEqualTo(4)
    }

    companion object {

        data class SafeTestCase(
            val input: List<Int>,
            val expected: Boolean,
        )

        @JvmStatic
        fun isSafeTestCases(): Stream<SafeTestCase> = Stream.of(
            SafeTestCase(
                input = listOf(7, 6, 4, 2, 1),
                expected = true
            ),
            SafeTestCase(
                input = listOf(1, 2, 7, 8, 9),
                expected = false
            ),
            SafeTestCase(
                input = listOf(9, 7, 6, 2, 1),
                expected = false
            ),
            SafeTestCase(
                input = listOf(1, 3, 2, 4, 5),
                expected = false
            ),
            SafeTestCase(
                input = listOf(8, 6, 4, 4, 1),
                expected = false
            ),
            SafeTestCase(
                input = listOf(1, 3, 6, 7, 9),
                expected = true
            ),
            SafeTestCase(
                input = listOf(9, 7, 6, 3, 1),
                expected = true
            ),
            SafeTestCase(
                input = listOf(2, 5, 6, 8, 6),
                expected = false
            ),
        )

        @JvmStatic
        fun isSafe2TestCases(): Stream<SafeTestCase> = Stream.of(
            SafeTestCase(
                input = listOf(7, 6, 4, 2, 1),
                expected = true
            ),
            SafeTestCase(
                input = listOf(1, 2, 7, 8, 9),
                expected = false
            ),
            SafeTestCase(
                input = listOf(9, 7, 6, 2, 1),
                expected = false
            ),
            SafeTestCase(
                input = listOf(1, 3, 2, 4, 5),
                expected = true
            ),
            SafeTestCase(
                input = listOf(8, 6, 4, 4, 1),
                expected = true
            ),
            SafeTestCase(
                input = listOf(1, 3, 6, 7, 9),
                expected = true
            ),
            SafeTestCase(
                input = listOf(9, 7, 6, 3, 1),
                expected = true
            ),
            SafeTestCase(
                input = listOf(2, 5, 6, 8, 6),
                expected = true
            ),
        )
    }
}