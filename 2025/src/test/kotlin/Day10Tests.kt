import day10.MachineState
import day10.parseInput
import org.assertj.core.api.Assertions.assertThat
import java.util.BitSet
import kotlin.test.Test

class Day10Tests {

    @Test
    fun testParseInput() {
        val expected = listOf(
            // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b0110)),
                buttons = arrayOf(
                    BitSet.valueOf(byteArrayOf(0b1000)),
                    BitSet.valueOf(byteArrayOf(0b1010)),
                    BitSet.valueOf(byteArrayOf(0b0100)),
                    BitSet.valueOf(byteArrayOf(0b1100)),
                    BitSet.valueOf(byteArrayOf(0b0101)),
                    BitSet.valueOf(byteArrayOf(0b0011))
                ),
                joltage = intArrayOf(3, 5, 4, 7)
            ),
            // [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b01000)),
                buttons = arrayOf(
                    BitSet.valueOf(byteArrayOf(0b11101)),
                    BitSet.valueOf(byteArrayOf(0b01100)),
                    BitSet.valueOf(byteArrayOf(0b10001)),
                    BitSet.valueOf(byteArrayOf(0b00111)),
                    BitSet.valueOf(byteArrayOf(0b11110)),
                ),
                joltage = intArrayOf(7, 5, 12, 7, 2)
            ),
            // [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            MachineState(
                expectedIndicator = BitSet.valueOf(byteArrayOf(0b101110)),
                buttons = arrayOf(
                    BitSet.valueOf(byteArrayOf(0b011111)),
                    BitSet.valueOf(byteArrayOf(0b011001)),
                    BitSet.valueOf(byteArrayOf(0b110111)),
                    BitSet.valueOf(byteArrayOf(0b000110)),
                ),
                joltage = intArrayOf(10, 11, 11, 5, 10, 5)
            )
        )

        val actual = parseInput(testInput.lines())

        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        val testInput = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()
    }
}