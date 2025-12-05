import day5.parseInput
import day5.part1
import day5.part2
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class Day5Tests {

    @Test
    fun testPart1() {
        val parsedInput = parseInput(testInput.lines())
        val actual = part1(parsedInput)

        assertThat(actual).isEqualTo(3)
    }

    @Test
    fun testPart2() {
        val parsedInput = parseInput(testInput.lines())
        val actual = part2(parsedInput)

        assertThat(actual).isEqualTo(14L)
    }


    companion object {
        val testInput = """
            3-5
            10-14
            16-20
            12-18
    
            1
            5
            8
            11
            17
            32
        """.trimIndent()
    }
}