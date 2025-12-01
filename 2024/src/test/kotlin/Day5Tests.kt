import adventofcode2024.day5.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day5Tests {

    private val testInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()

    @Test
    fun testParseInput() {
        val (rules, updates) = parseInput(testInput.lines())

        assertThat(rules).containsAll(
            listOf(
                47 to 53,
                97 to 13,
                97 to 61,
                97 to 47,
                75 to 29,
                61 to 13,
                75 to 53,
                29 to 13,
                97 to 29,
                53 to 29,
                61 to 53,
                97 to 53,
                61 to 29,
                47 to 13,
                75 to 47,
                97 to 75,
                47 to 61,
                75 to 61,
                47 to 29,
                75 to 13,
                53 to 13
            )
        )

        assertThat(updates).containsAll(
            listOf(
                listOf(75, 47, 61, 53, 29),
                listOf(97, 61, 53, 29, 13),
                listOf(75, 29, 13),
                listOf(75, 97, 47, 61, 53),
                listOf(61, 13, 29),
                listOf(97, 13, 75, 29, 47)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("testUpdateSource")
    fun testTestUpdate(testCase: Pair<List<Int>, Boolean>) {
        val rules = listOf(
            47 to 53,
            97 to 13,
            97 to 61,
            97 to 47,
            75 to 29,
            61 to 13,
            75 to 53,
            29 to 13,
            97 to 29,
            53 to 29,
            61 to 53,
            97 to 53,
            61 to 29,
            47 to 13,
            75 to 47,
            97 to 75,
            47 to 61,
            75 to 61,
            47 to 29,
            75 to 13,
            53 to 13
        )

        val (input, expected) = testCase
        assertThat(testUpdate(rules, input)).isEqualTo(expected)
    }

    @Test
    fun testPuzzle1() {
        val (rules, updates) = parseInput(testInput.lines())
        assertThat(puzzle1(rules, updates)).isEqualTo(143)
    }

    @ParameterizedTest
    @MethodSource("testFixUpdateSource")
    fun testFixUpdate(testCase: Pair<List<Int>, List<Int>>) {
        val rules = listOf(
            47 to 53,
            97 to 13,
            97 to 61,
            97 to 47,
            75 to 29,
            61 to 13,
            75 to 53,
            29 to 13,
            97 to 29,
            53 to 29,
            61 to 53,
            97 to 53,
            61 to 29,
            47 to 13,
            75 to 47,
            97 to 75,
            47 to 61,
            75 to 61,
            47 to 29,
            75 to 13,
            53 to 13
        )

        val (input, expected) = testCase

        val actual = fixUpdates(rules, input)

        assertThat(actual).containsExactlyElementsOf(expected)
            .withFailMessage { "Expected $expected but got $actual" }
    }


    @Test
    fun testPuzzle2() {
        val (rules, updates) = parseInput(testInput.lines())
        assertThat(puzzle2(rules, updates)).isEqualTo(123)
    }

    companion object {

        @JvmStatic
        fun testUpdateSource() = listOf(
            listOf(75,47,61,53,29) to true,
            listOf(97,61,53,29,13) to true,
            listOf(75,29,13) to true,
            listOf(75,97,47,61,53) to false,
            listOf(61,13,29) to false,
            listOf(97,13,75,29,47) to false
        )

        @JvmStatic
        fun testFixUpdateSource() = listOf(
            listOf(75,97,47,61,53) to listOf(97,75,47,61,53),
            listOf(61,13,29) to listOf(61,29,13),
            listOf(97,13,75,29,47) to listOf(97,75,47,29,13)
        )
    }
}