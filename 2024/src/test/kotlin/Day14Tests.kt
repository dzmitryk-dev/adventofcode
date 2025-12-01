
import adventofcode2024.Point
import adventofcode2024.day14.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day14Tests {

    @Test
    fun testParseInput() {
        val robots = parseInput(input)

        assertThat(robots).containsExactly(
            Robot(position = Point(0, 4), velocity = Pair(3, -3)),
            Robot(position = Point(6, 3), velocity = Pair(-1, -3)),
            Robot(position = Point(10, 3), velocity = Pair(-1, 2)),
            Robot(position = Point(2, 0), velocity = Pair(2, -1)),
            Robot(position = Point(0, 0), velocity = Pair(1, 3)),
            Robot(position = Point(3, 0), velocity = Pair(-2, -2)),
            Robot(position = Point(7, 6), velocity = Pair(-1, -3)),
            Robot(position = Point(3, 0), velocity = Pair(-1, -2)),
            Robot(position = Point(9, 3), velocity = Pair(2, 3)),
            Robot(position = Point(7, 3), velocity = Pair(-1, 2)),
            Robot(position = Point(2, 4), velocity = Pair(2, -3)),
            Robot(position = Point(9, 5), velocity = Pair(-3, -3)),
        )
    }

    @Test
    fun testVisualize() {
        val robots = parseInput(input)
        val field = visualize(robots, Pair(11, 7))

        assertThat(field).isEqualTo(
            """
                1.12.......
                ...........
                ...........
                ......11.11
                1.1........
                .........1.
                .......1...
            """.trimIndent()
        )
    }

    @Test
    fun testSimulation() {
        val robots = parseInput(input)

        val newRobots = simulate(robots, 11 to 7, 100)

        val field = visualize(newRobots, Pair(11, 7))

        try {
            assertThat(field)
                .isEqualTo(
                    """
                ......2..1.
                ...........
                1..........
                .11........
                .....1.....
                ...12......
                .1....1....
            """.trimIndent()
                )
        } catch (e: AssertionError) {
            println(newRobots)
            throw e
        }
    }

    @Test
    fun testCalculateSafetyFactor() {
        val robots = parseInput(input)

        val newRobots = simulate(robots, 11 to 7, 100)
        println(visualize(newRobots, Pair(11, 7)))

        val safetyFactor = calculateSafetyFactor(newRobots, 11 to 7)

        assertThat(safetyFactor).isEqualTo(12)
    }

    @ParameterizedTest
    @MethodSource("simulationInput")
    fun testSimulation(testCase: Triple<String, Int, String>) {
        val robots = parseInput(testCase.first.lines())

        val newRobots = simulate(robots, 11 to 7, testCase.second)

        val field = visualize(newRobots, Pair(11, 7))

        assertThat(field).isEqualTo(testCase.third)
    }


    companion object {

        private val input = """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent().lines()

        @JvmStatic
        fun simulationInput() = Stream.of(
            Triple(
                "p=2,4 v=2,-3",
                0,
                """
                    ...........
                    ...........
                    ...........
                    ...........
                    ..1........
                    ...........
                    ...........
                """.trimIndent()
            ),
            Triple(
                    "p=2,4 v=2,-3",
                1,
                """
                    ...........
                    ....1......
                    ...........
                    ...........
                    ...........
                    ...........
                    ...........
                """.trimIndent()
            ),
            Triple(
                "p=2,4 v=2,-3",
                2,
                """
                    ...........
                    ...........
                    ...........
                    ...........
                    ...........
                    ......1....
                    ...........
                """.trimIndent()
            ),
            Triple(
                "p=2,4 v=2,-3",
                3,
                """
                    ...........
                    ...........
                    ........1..
                    ...........
                    ...........
                    ...........
                    ...........
                """.trimIndent()
            ),
            Triple(
                "p=2,4 v=2,-3",
                4,
                """
                    ...........
                    ...........
                    ...........
                    ...........
                    ...........
                    ...........
                    ..........1
                """.trimIndent()
            ),
            Triple(
                "p=2,4 v=2,-3",
                5,
                """
                    ...........
                    ...........
                    ...........
                    .1.........
                    ...........
                    ...........
                    ...........
                """.trimIndent()
            ),
        )
    }
}