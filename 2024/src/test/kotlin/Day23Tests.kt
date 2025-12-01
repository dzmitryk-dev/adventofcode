import adventofcode2024.day23.findGroups
import adventofcode2024.day23.groupFilter
import adventofcode2024.day23.parseInput
import adventofcode2024.day23.part1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23Tests {

    @Test
    fun testFindGroups() {
        val expected = expectedGroups.lines().map { it.split(",").toSet() }

        val input = parseInput(testInput.lines())

        val result = findGroups(input)

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun testFindFilteredGroups() {
        val expected = expectedFilteredGroups.lines().map { it.split(",").toSet() }

        val input = parseInput(testInput.lines())
        val result = findGroups(input).filter(::groupFilter)

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun testPart1() {
        val expected = part1(testInput.lines())

        assertThat(expected).isEqualTo(7)
    }

    companion object {

        private val testInput = """
            kh-tc
            qp-kh
            de-cg
            ka-co
            yn-aq
            qp-ub
            cg-tb
            vc-aq
            tb-ka
            wh-tc
            yn-cg
            kh-ub
            ta-co
            de-co
            tc-td
            tb-wq
            wh-td
            ta-ka
            td-qp
            aq-cg
            wq-ub
            ub-vc
            de-ta
            wq-aq
            wq-vc
            wh-yn
            ka-de
            kh-ta
            co-tc
            wh-qp
            tb-vc
            td-yn
        """.trimIndent()

        private val expectedGroups = """
            aq,cg,yn
            aq,vc,wq
            co,de,ka
            co,de,ta
            co,ka,ta
            de,ka,ta
            kh,qp,ub
            qp,td,wh
            tb,vc,wq
            tc,td,wh
            td,wh,yn
            ub,vc,wq
        """.trimIndent()

        private val expectedFilteredGroups = """
            co,de,ta
            co,ka,ta
            de,ka,ta
            qp,td,wh
            tb,vc,wq
            tc,td,wh
            td,wh,yn
        """.trimIndent()
    }

}