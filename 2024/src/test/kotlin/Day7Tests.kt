import adventofcode2024.day7.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day7Tests {

    private val testInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()

    @Test
    fun testParseInput() {
        val expected = listOf(
            190L to listOf(10L, 19L),
            3267L to listOf(81L, 40L, 27L),
            83L to listOf(17L, 5L),
            156L to listOf(15L, 6L),
            7290L to listOf(6L, 8L, 6L, 15L),
            161011L to listOf(16L, 10L, 13L),
            192L to listOf(17L, 8L, 14L),
            21037L to listOf(9L, 7L, 18L, 13L),
            292L to listOf(11L, 6L, 16L, 20L),
        )

        val result = parseInput(testInput.lines())

        assertThat(result).containsExactlyElementsOf(expected)
    }

    @ParameterizedTest
    @MethodSource("findSolutionSource")
    fun testTryFindSolution(testCase: TestCase) {
        val result = tryFindSolution(testCase.result, testCase.numbers)

        assertThat(result).isEqualTo(testCase.expected)
    }

    @Test
    fun testPuzzle1() {
        val result = puzzle1(parseInput(testInput.lines()))

        assertThat(result).isEqualTo(3749L)
    }

    @ParameterizedTest
    @MethodSource("findSolutionSource2")
    fun testTryFindSolution2(testCase: TestCase) {
        val result = tryFindSolution2(testCase.result, testCase.numbers)

        assertThat(result).isEqualTo(testCase.expected)
    }

    @Test
    fun testPuzzle2() {
        val result = puzzle2(parseInput(testInput.lines()))

        assertThat(result).isEqualTo(11387)
    }

    companion object {
        data class TestCase(
            val result: Long,
            val numbers: List<Long>,
            val expected: Boolean,
        )

        @JvmStatic
        fun findSolutionSource() = listOf(
            TestCase(190L, listOf(10L, 19L), true),
            TestCase(3267L, listOf(81L, 40L, 27L), true),
            TestCase(83L, listOf(17L, 5L), false),
            TestCase(156L, listOf(15L, 6L), false),
            TestCase(7290L, listOf(6L, 8L, 6L, 15L), false),
            TestCase(161011L, listOf(16L, 10L, 13L), false),
            TestCase(192L, listOf(17L, 8L, 14L), false),
            TestCase(21037L, listOf(9L, 7L, 18L, 13L), false),
            TestCase(292L, listOf(11L, 6L, 16L, 20L), true),
        )

        @JvmStatic
        fun findSolutionSource2() = listOf(
            TestCase(190L, listOf(10L, 19L), true),
            TestCase(3267L, listOf(81L, 40L, 27L), true),
            TestCase(83L, listOf(17L, 5L), false),
            TestCase(156L, listOf(15L, 6L), true),
            TestCase(7290L, listOf(6L, 8L, 6L, 15L), true),
            TestCase(161011L, listOf(16L, 10L, 13L), false),
            TestCase(192L, listOf(17L, 8L, 14L), true),
            TestCase(21037L, listOf(9L, 7L, 18L, 13L), false),
            TestCase(292L, listOf(11L, 6L, 16L, 20L), true),
        )
    }
}