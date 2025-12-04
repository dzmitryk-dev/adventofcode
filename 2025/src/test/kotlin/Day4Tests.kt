import com.adventofcode.utils.col
import com.adventofcode.utils.line
import day4.findAccessibleRolls
import day4.part2
import day4.removeRolls
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test

class Day4Tests {

    @Test
    fun testFindAccessibleRolls() {
        val expected = """
            ..xx.xx@x.
            x@@.@.@.@@
            @@@@@.x.@@
            @.@@@@..@.
            x@.@@@@.@x
            .@@@@@@@.@
            .@.@.@.@@@
            x.@@@.@@@@
            .@@@@@@@@.
            x.x.@@@.x.
        """.trimIndent()

        val points = findAccessibleRolls(testInput.lines())

        val actual = testInput.lines().mapIndexed { line, lineString ->
            val chars = lineString.toCharArray()
            points.filter { p -> p.line == line }
                .forEach { p ->
                    chars[p.col] = 'x'
                }
            String(chars)
        }.joinToString("\n")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testPart1() {
        val accessibleRolls = findAccessibleRolls(testInput.lines())
        assertThat(accessibleRolls.size).isEqualTo(13)
    }

    @ParameterizedTest
    @MethodSource("removeTestSource")
    fun testRemoveRolls(testCase: Pair<String, String>) {
        val (input, expected) = testCase

        val points = findAccessibleRolls(input.lines())
        val actual = removeRolls(input.lines(), points).joinToString("\n")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testPart2() {
        val removedRolls = part2(testInput.lines())
        assertThat(removedRolls).isEqualTo(43)
    }

    companion object {
        val testInput = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent()

        @JvmStatic
        fun removeTestSource() = Stream.of(
        """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent() to
        """
             .......@..
             .@@.@.@.@@
             @@@@@...@@
             @.@@@@..@.
             .@.@@@@.@.
             .@@@@@@@.@
             .@.@.@.@@@
             ..@@@.@@@@
             .@@@@@@@@.
             ....@@@...
         """.trimIndent(),
         """
             .......@..
             .@@.@.@.@@
             @@@@@...@@
             @.@@@@..@.
             .@.@@@@.@.
             .@@@@@@@.@
             .@.@.@.@@@
             ..@@@.@@@@
             .@@@@@@@@.
             ....@@@...
         """.trimIndent() to
         """
            ..........
            .@@.....@.
            .@@@@...@@
            ..@@@@....
            .@.@@@@...
            ..@@@@@@..
            ...@.@.@@@
            ..@@@.@@@@
            ..@@@@@@@.
            ....@@@...
        """.trimIndent(),
        """
            ..........
            .@@.....@.
            .@@@@...@@
            ..@@@@....
            .@.@@@@...
            ..@@@@@@..
            ...@.@.@@@
            ..@@@.@@@@
            ..@@@@@@@.
            ....@@@...
        """.trimIndent() to
        """
            ..........
            ..@.......
            .@@@@.....
            ..@@@@....
            ...@@@@...
            ..@@@@@@..
            ...@.@.@@.
            ..@@@.@@@@
            ...@@@@@@.
            ....@@@...
        """.trimIndent()

        )
    }
}