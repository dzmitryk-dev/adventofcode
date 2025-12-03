import day10.part1
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class Day10Tests {

    @Test
    fun testPart1_1() {
        val actual = part1(textInput1.lines())

        assertThat(actual).isEqualTo(35)
    }

    @Test
    fun testPart1_2() {
        val actual = part1(textInput2.lines())

        assertThat(actual).isEqualTo(220)
    }

    companion object {

        val textInput1 = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent()

        val textInput2 = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent()
    }
}