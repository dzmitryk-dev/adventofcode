import day2.parseInput
import day2.part1
import day2.verifyRange
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.test.Test

class Day2Tests {

    @Test
    fun testParsing() {
        val expected = arrayOf(
            "11" to "22",
            "95" to "115",
            "998" to "1012",
            "1188511880" to "1188511890",
            "222220" to "222224",
            "1698522" to "1698528",
            "446443" to "446449",
            "38593856" to "38593862",
            "565653" to "565659",
            "824824821" to "824824827",
            "2121212118" to "2121212124"
        )

        val actual = parseInput(testInput)

        assertThat(actual).containsExactly(*expected)
    }

    @TestFactory
    fun testVerifyRange(): Stream<DynamicTest> =
        Stream.of(
            TestCase("11" to "22", listOf(11, 22)),
            TestCase("95" to "115", listOf(99)),
            TestCase("998" to "1012", listOf(1010)),
            TestCase("1188511880" to "1188511890", listOf(1188511885)),
            TestCase("222220" to "222224", listOf(222222)),
            TestCase("1698522" to "1698528", emptyList()),
            TestCase("446443" to "446449", listOf(446446)),
            TestCase("38593856" to "38593862", listOf(38593859)),
            TestCase("565653" to "565659", emptyList()),
            TestCase("824824821" to "824824827", emptyList()),
            TestCase("2121212118" to "2121212124", emptyList())
        ).map { testCase ->
            val (start, end) = testCase.range
            val expectedInvalidIds = testCase.expectedInvalidIds

            DynamicTest.dynamicTest("Test Verify Range $testCase") {
                val actualInvalidIds = verifyRange(start, end)

                assertThat(actualInvalidIds).containsExactlyElementsOf(expectedInvalidIds)
            }
        }

    @Test
    fun testPart1() {
        val expected = 1227775554

        val actual = part1(parseInput(testInput))

        assertThat(expected).isEqualTo(actual)
    }

    companion object Companion {

        data class TestCase(
            val range: Pair<String, String>,
            val expectedInvalidIds: List<Long>
        )

        const val testInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124"

    }
}