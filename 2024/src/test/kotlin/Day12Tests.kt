import adventofcode2024.day12.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Ignore
import kotlin.test.Test

class Day12Tests {

    @ParameterizedTest
    @MethodSource("testFindRegionSource")
    fun testFindRegion(testCase: Pair<Char, Int>) {
        val result = findRegions(parseInput(testInput1.lines()), testCase.first)

        assertThat(result.first().size).isEqualTo(testCase.second)
    }

    @Test
    fun testFindRegionX() {
        val result = findRegions(parseInput(testInput2.lines()), 'X')

        assertThat(result.size).isEqualTo(4)

        result.forEach {
            assertThat(it.size).isEqualTo(1)
        }
    }

    @Test
    fun testFindRegionO() {
        val result = findRegions(parseInput(testInput2.lines()), 'O')

        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().size).isEqualTo(21)
    }

    @ParameterizedTest
    @MethodSource("testTotalPriceSource")
    fun testCalculateTotalPrice(testCase: Pair<String, Int>) {
        val result = calculateTotalPrice(parseInput(testCase.first.lines()))

        assertThat(result).isEqualTo(testCase.second)
    }

    @ParameterizedTest
    @MethodSource("testCalculatePriceSource")
    fun testCalculatePrice(testCase: Pair<Char, Int>) {
        val result = calculatePrice(findRegions(parseInput(testInput1.lines()), testCase.first).first())

        assertThat(result).isEqualTo(testCase.second)
    }

    @Test
    fun testCalculatePrice0() {
        val result = calculatePrice(findRegions(parseInput(testInput2.lines()), 'O').first())

        assertThat(result).isEqualTo(756)
    }

    @Ignore
    @ParameterizedTest
    @MethodSource("testCalculateTotalBulkPriceSource")
    fun testCalculateTotalBulkPrice(testCase: Pair<String, Int>) {
        val result = calculateTotalBulkPrice(parseInput(testCase.first.lines()))

        assertThat(result).isEqualTo(testCase.second)
    }

    @Ignore
    @ParameterizedTest
    @MethodSource("testCalculateBulkPriceSource")
    fun testCalculateBulkPrice(testCase: Triple<String, Char, Int>) {
        val region = findRegions(parseInput(testCase.first.lines()), testCase.second).first()
        val result = calculateBulkPrice(region)

        assertThat(result).isEqualTo(testCase.third)
    }

    companion object {

        private val testInput1 = """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent()

        private val testInput2 = """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent()

        private val testInput3 = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()

        private val testInput4 = """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """.trimIndent()

        private val testInput5 = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent()

        @JvmStatic
        fun testTotalPriceSource() = Stream.of(
            testInput1 to 140,
            testInput2 to 772,
            testInput3 to 1930
        )

        @JvmStatic
        fun testFindRegionSource() = Stream.of(
            'A' to 4,
            'B' to 4,
            'C' to 4,
            'E' to 3,
            'D' to 1,
        )

        @JvmStatic
        fun testCalculatePriceSource() = Stream.of(
            'A' to 40,
            'B' to 32,
            'C' to 40,
            'E' to 24,
            'D' to 4,
        )

        @JvmStatic
        fun testCalculateTotalBulkPriceSource() = Stream.of(
            testInput1 to 80,
            testInput2 to 436,
            testInput3 to 1206,
            testInput4 to 236,
            testInput5 to 368
        )

        @JvmStatic
        fun testCalculateBulkPriceSource() = Stream.of(
            Triple(testInput1, 'A', 16),
            Triple(testInput1, 'B', 16),
            Triple(testInput1, 'C', 32),
            Triple(testInput1, 'D', 4),
            Triple(testInput1, 'E', 16),
            Triple(testInput4, 'E', 204),
            Triple(testInput3, 'R', 120),
            Triple(testInput3, 'V', 130),
            Triple(testInput3, 'M', 30),
            Triple(testInput3, 'I', 16),
            Triple(testInput3, 'C', 308),
            Triple(testInput3, 'F', 120),
            Triple(testInput3, 'J', 132),
            Triple(testInput3, 'S', 18),
            Triple(testInput3, 'E', 104),
        )
    }
}