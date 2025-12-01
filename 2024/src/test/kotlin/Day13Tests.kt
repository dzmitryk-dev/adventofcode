import adventofcode2024.day13.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day13Tests {

    @Test
    fun testParseInput() {
        val result = parseInput(testInput)

        assertThat(result).containsExactly(
            Machine(
                a = 94 to 34,
                b = 22 to 67,
                prize = 8400L to 5400L,
            ),
            Machine(
                a = 26 to 66,
                b = 67 to 21,
                prize = 12748L to 12176L,
            ),
            Machine(
                a = 17 to 86,
                b = 84 to 37,
                prize = 7870L to 6450L,
            ),
            Machine(
                a = 69 to 23,
                b = 27 to 71,
                prize = 18641L to 10279L,
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("testFindButtonsCombination")
    fun testFindButtonPressCombination(testCase: Pair<Machine, Pair<Int, Int>?>) {
        val result = findButtonPressCombination(testCase.first)

        assertThat(result).isEqualTo(testCase.second)
    }

    @Test
    fun testPart1() {
        val result = partOne(testInput)

        assertThat(result).isEqualTo(480)
    }

    companion object {

        private val testInput = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
            
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
            
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
            
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines()

        @JvmStatic
        fun testFindButtonsCombination() = Stream.of(
            Machine(
                a = 94 to 34,
                b = 22 to 67,
                prize = 8400L to 5400L,
            ) to (80L to 40L),
            Machine(
                a = 26 to 66,
                b = 67 to 21,
                prize = 12748L to 12176L,
            ) to null,
            Machine(
                a = 17 to 86,
                b = 84 to 37,
                prize = 7870L to 6450L,
            ) to (38L to 86L),
            Machine(
                a = 69 to 23,
                b = 27 to 71,
                prize = 18641L to 10279L,
            ) to null,
        )

    }
}