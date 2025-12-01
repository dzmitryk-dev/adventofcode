import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class Day1Tests {

    @Test
    fun testParsing() {
        val expected = listOf(
            Rotation(Rotation.Direction.LEFT, 68),
            Rotation(Rotation.Direction.LEFT, 30),
            Rotation(Rotation.Direction.RIGHT, 48),
            Rotation(Rotation.Direction.LEFT, 5),
            Rotation(Rotation.Direction.RIGHT, 60),
            Rotation(Rotation.Direction.LEFT, 55),
            Rotation(Rotation.Direction.LEFT, 1),
            Rotation(Rotation.Direction.LEFT, 99),
            Rotation(Rotation.Direction.RIGHT, 14),
            Rotation(Rotation.Direction.LEFT, 82),
        )

        val actual = parseInput(example.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testHugeRotation() {
        val rotation = Rotation(Rotation.Direction.LEFT, 250)
        val expected = 0

        val actual = rotateDial(currentPosition = 50, rotation = rotation)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testHugeRotation2() {
        val rotation = Rotation(Rotation.Direction.RIGHT, 260)
        val expected = 10

        val actual = rotateDial(currentPosition = 50, rotation = rotation)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testRotateDial() {
        val expected = arrayOf(
            82,
            52,
            0,
            95,
            55,
            0,
            99,
            0,
            14,
            32,
        )

        val rotations = parseInput(example.lines())
        val actual = applyRotations(rotations)

        assertThat(actual).containsExactly(expected)
    }

    @Test
    fun testPart1() {
        val expected = 3
        val actual = part1(parseInput(example.lines()))

        assertThat(actual).isEqualTo(expected)
    }

    companion object {

        val example = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent()
    }
}