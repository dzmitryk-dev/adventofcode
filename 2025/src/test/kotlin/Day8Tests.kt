import day8.Point
import day8.buildCircuits
import day8.buildConnections
import day8.parseInput
import day8.part1
import day8.part2
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class Day8Tests {

    @Test
    fun testParsing() {
        val expected = listOf(
            Point(162, 817, 812),
            Point(57, 618, 57),
            Point(906, 360, 560),
            Point(592, 479, 940),
            Point(352, 342, 300),
            Point(466, 668, 158),
            Point(542, 29, 236),
            Point(431, 825, 988),
            Point(739, 650, 466),
            Point(52, 470, 668),
            Point(216, 146, 977),
            Point(819, 987, 18),
            Point(117, 168, 530),
            Point(805, 96, 715),
            Point(346, 949, 466),
            Point(970, 615, 88),
            Point(941, 993, 340),
            Point(862, 61, 35),
            Point(984, 92, 344),
            Point(425, 690, 689)
        )

        val actual = parseInput(testInput.lines())

        assertThat(actual).containsAll(expected)
    }

    @Test
    fun testBuildCircuit() {
        val circuits = buildCircuits(
            connections = buildConnections(
                parseInput(testInput.lines())
            ),
            limit = 10
        ).take(3)

        assertThat(circuits.map { it.size }).containsExactly(5, 4, 2)
    }

    @Test
    fun testPart1() {
        val actual = part1(testInput.lines(), 10, 3)

        assertThat(actual).isEqualTo(40)
    }

    @Test
    fun testPart2() {
        val actual = part2(testInput.lines())

        assertThat(actual).isEqualTo(25272)
    }


    companion object {

        val testInput = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent()
    }

}