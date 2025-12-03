import day3.findJoltage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day3Tests {

    @ParameterizedTest
    @MethodSource("testFindJoltageTestDataSource")
    fun findJoltageTest(testCase: Pair<CharSequence, Int>) {
        val actual = findJoltage(testCase.first)

        assertThat(actual).isEqualTo(testCase.second)
    }

    companion object {

         @JvmStatic
         fun testFindJoltageTestDataSource() = listOf(
            "987654321111111" to 98,
            "811111111111119" to 89,
            "234234234234278" to 78,
            "818181911112111" to 92,
         )


         val testInput = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
         """.trimIndent()
     }
}