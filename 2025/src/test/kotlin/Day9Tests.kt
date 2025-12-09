import com.adventofcode.utils.Point
import day9.calculateArea
import day9.findTheLargestRectangleArea
import day9.findTheLargestRectangleAreaInsideContour
import day9.parseInput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

class Day9Tests {

    @ParameterizedTest
    @MethodSource("calculateAreaTestData")
    fun calculateAreaTest(testCase: TestAreaCase) {
        val (p1, p2, expected) = testCase

        val actual = calculateArea(p1, p2)

        assertThat(actual).isEqualTo(expected.toLong())
    }

    @Test
    fun testTheLargestRectangleArea() {
        val expected = 50

        val actual = findTheLargestRectangleArea(parseInput(testInput.lines()))

        assertThat(actual).isEqualTo(expected.toLong())
    }

    @ParameterizedTest
    @MethodSource("isRectangleInsideContourTestData")
    fun testIsRectangleInsideContour(testCase: TestContourCase) {
        val (p1, p2, expected) = testCase

        val contour = parseInput(testInput.lines())

        val actual = day9.isRectangleInsideContour(p1, p2, contour)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testFindTheLargestRectangleAreaInsideContour() {
        val expected = 24

        val actual = findTheLargestRectangleAreaInsideContour(parseInput(testInput.lines()))

        assertThat(actual).isEqualTo(expected.toLong())
    }

    companion object {

        data class TestAreaCase(
            val p1: Point,
            val p2: Point,
            val expectedArea: Int
        )

        @JvmStatic
        fun calculateAreaTestData() = listOf(
            TestAreaCase(Point(2,5),  Point(9,7), 24),
            TestAreaCase(Point(7,1),  Point(11,7), 35),
            TestAreaCase(Point(7,3),  Point(2,3), 6),
            TestAreaCase(Point(2,5),  Point(11,1), 50),
        )

        data class TestContourCase(
            val p1: Point,
            val p2: Point,
            val expected: Boolean
        )

        @JvmStatic
        fun isRectangleInsideContourTestData() = listOf(
            TestContourCase(Point(7,3),  Point(11,1), true),
            TestContourCase(Point(9,7),  Point(9,5), true),
            TestContourCase(Point(9,5),  Point(2,3), true),
            TestContourCase(Point(2,3),  Point(7,1), false),
        )

        val testInput = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent()
    }
}