import adventofcode2024.day1.parseInput
import adventofcode2024.day1.puzzle1
import adventofcode2024.day1.puzzle2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Tests {

    private val testInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()

    @Test
    fun testParseInput() {
        val (a, b) = parseInput(testInput.lines())
        assertThat(a).containsExactly(3, 4, 2, 1, 3, 3)
        assertThat(b).containsExactly(4, 3, 5, 3, 9, 3)
    }

    @Test
    fun testPuzzle1() {
        val (left, right) = parseInput(testInput.lines())

        assertThat(puzzle1(left, right)).isEqualTo(11)
    }


    @Test
    fun testPuzzle2() {
        val (left, right) = parseInput(testInput.lines())

        assertThat(puzzle2(left, right)).isEqualTo(31)
    }
}