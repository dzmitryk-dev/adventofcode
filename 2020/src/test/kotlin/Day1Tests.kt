
import day1.part1
import day1.part2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Tests {

    @Test
    fun testPart1() {
        val actual = part1(testInput.lines().map { it.toInt() })

        assertThat(actual).isEqualTo(514579)
    }

    @Test
    fun testPart2() {
        val actual = part2(testInput.lines().map { it.toInt() })

        assertThat(actual).isEqualTo(241861950)
    }

    companion object {

        val testInput = """
            1721
            979
            366
            299
            675
            1456
        """.trimIndent()
    }
}