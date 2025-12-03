import day3.findJoltage
import day3.findJoltageV2
import day3.part1
import day3.part2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

class Day3Tests {

    @ParameterizedTest
    @MethodSource("testFindJoltageTestDataSource")
    fun findJoltageTest(testCase: Pair<CharSequence, Int>) {
        val actual = findJoltage(testCase.first)

        assertThat(actual).isEqualTo(testCase.second)
    }

    @Test
    fun testPart1() {
        val actual = part1(testInput.lines())

        assertThat(actual).isEqualTo(357)
    }

    @ParameterizedTest
    @MethodSource("testFindJoltageTestDataSource")
    fun findJoltageV2Test(testCase: Pair<CharSequence, Int>) {
        val actual = findJoltageV2(testCase.first, 2)

        assertThat(actual).isEqualTo(testCase.second.toLong())
    }

    @ParameterizedTest
    @MethodSource("testFindJoltageTestDataSource2")
    fun findJoltageV2Test2(testCase: Pair<CharSequence, Int>) {
        val actual = findJoltageV2(testCase.first, 12)

        assertThat(actual).isEqualTo(testCase.second)
    }

    @Test
    fun testPart2() {
        val actual = part2(testInput.lines())

        assertThat(actual).isEqualTo(3121910778619L)
    }

    companion object {

         @JvmStatic
         fun testFindJoltageTestDataSource() = listOf(
            "987654321111111" to 98,
            "811111111111119" to 89,
            "234234234234278" to 78,
            "818181911112111" to 92,
         )

        @JvmStatic
        fun testFindJoltageTestDataSource2() = listOf(
            "987654321111111" to 987654321111L,
            "811111111111119" to 811111111119L,
            "234234234234278" to 434234234278L,
            "818181911112111" to 888911112111L,
        )



        val testInput = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
         """.trimIndent()
     }
}