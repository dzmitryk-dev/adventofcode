import day11.countPaths
import day11.parseInput
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class Day11Tests {

    @Test
    fun testParseInput() {
        val expected = mapOf(
            "aaa" to listOf("you", "hhh"),
            "you" to listOf("bbb", "ccc"),
            "bbb" to listOf("ddd", "eee"),
            "ccc" to listOf("ddd", "eee", "fff"),
            "ddd" to listOf("ggg"),
            "eee" to listOf("out"),
            "fff" to listOf("out"),
            "ggg" to listOf("out"),
            "hhh" to listOf("ccc", "fff", "iii"),
            "iii" to listOf("out")
        )

        val actual = parseInput(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testCountPaths() {
        val actual = countPaths(parseInput(testInput.lines()))

        assertThat(actual).isEqualTo(5)
    }

    companion object {
        val testInput = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
        """.trimIndent()
    }
}