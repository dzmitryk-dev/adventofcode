import adventofcode2024.day11.countStones
import adventofcode2024.day11.puzzle1
import adventofcode2024.day11.transform
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day11Tests {

    @ParameterizedTest
    @MethodSource("testTransformSource")
    fun testTransform(testcase: Pair<String, String>) {
        val (input, expected) = testcase

        val actual = transform(input.split(" "))

        assertThat(expected.split(" ")).containsExactlyElementsOf(actual)
    }

    @ParameterizedTest
    @MethodSource("testPuzzle1Source")
    fun testPuzzle1(testcase: Pair<String, List<Int>>) {
        val actual = puzzle1(testcase.first, testcase.second.first())

        assertThat(testcase.second.component2()).isEqualTo(actual)
    }

    @ParameterizedTest
    @MethodSource("testPuzzle1Source")
    fun testCountStones(testcase: Pair<String, List<Int>>) {
        val actual = countStones(testcase.first.split(" "), testcase.second.first())

        assertThat(testcase.second.component2()).isEqualTo(actual)
    }

    companion object {

        @JvmStatic
        fun testTransformSource() = Stream.of(
            "0 1 10 99 999" to "1 2024 1 0 9 9 2021976",
            "125 17" to "253000 1 7",
            "253000 1 7" to "253 0 2024 14168",
            "253 0 2024 14168" to "512072 1 20 24 28676032",
            "512072 1 20 24 28676032" to "512 72 2024 2 0 2 4 2867 6032",
            "512 72 2024 2 0 2 4 2867 6032" to "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32",
            "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32" to "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2"
        )

        @JvmStatic
        fun testPuzzle1Source() = Stream.of(
            "125 17" to listOf(25, 55312),
            "125 17" to listOf(6, 22),
            "0 1 10 99 999" to listOf(1, 7),
        )
    }
}