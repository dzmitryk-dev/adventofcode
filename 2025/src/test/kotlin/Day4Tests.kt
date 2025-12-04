import com.adventofcode.utils.col
import com.adventofcode.utils.line
import day4.findAccessibleRolls
import org.assertj.core.api.Assertions.assertThat
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
            points.filter { p -> p.line ==  line }
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
    }
}