import adventofcode2024.day9.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test

class Day9Tests {

    @ParameterizedTest
    @MethodSource("unpackSourceProvider")
    fun testUnpackDiskMap(testCase: Pair<String, List<Int>>) {
        val result = unpackDiskMap(testCase.first)

        assertThat(result).containsExactlyElementsOf(testCase.second)
    }

    @ParameterizedTest
    @MethodSource("moveBlocksSourceProvider")
    fun testMoveBlocks(testCase: Pair<List<Int>, List<Int>>) {
        val result = moveBlocks(testCase.first)

        assertThat(result).containsExactlyElementsOf(testCase.second)
    }

    @Test
    fun testCalculateCheckSum() {
        val result = calculateCheckSum(listOf(0,0,9,9,8,1,1,1,8,8,8,2,7,7,7,3,3,3,6,4,4,6,5,5,5,5,6,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1))

        assertThat(result).isEqualTo(1928L)
    }

    @ParameterizedTest
    @MethodSource("parseDiskSourceProvider")
    fun testParseDisk(testCase: Pair<String, List<Block>>) {
        val result = parseDiskMap(testCase.first)

        assertThat(result).containsExactlyElementsOf(testCase.second)
    }

    @ParameterizedTest
    @MethodSource("unpackSourceProvider")
    fun testUnpackBlockMap(testCase: Pair<String, List<Int>>) {
        val result = unpackBlockMap(parseDiskMap(testCase.first))

        assertThat(result).containsExactlyElementsOf(testCase.second)
    }

    @Test
    fun testMoveFiles() {
        // 2333133121414131402
//        val input = listOf(Block(0, 2), Block(-1, 3), Block(1, 3), Block(-1, 3),
//            Block(2,1), Block(-1, 3), Block(3, 3), Block(-1, 1), Block(4,2), Block(-1,1),
//            Block(5, 4), Block(-1, 1), Block(6, 4), Block(-1,1), Block(7,3), Block(-1,1),
//            Block(8,4), Block(-1, 0), Block(9,2))

        // 00992111777.44.333....5555.6666.....8888..
//        val expected = listOf(Block(0, 2),Block(9, 2), Block(2,1), Block(1, 3), Block(7, 3), Block(-1, 1),
//            Block(4, 2), Block(-1, 1), Block(3, 3), Block(-1,4), Block(5,4), Block(-1,1),
//            Block(5,4), Block(-1,5), Block(8,4), Block(-1,2))

        val input = "2333133121414131402"
        val blockMap = parseDiskMap(input)
        val unpackedMap = unpackBlockMap(blockMap)

        assertThat(diskMapToString(unpackedMap)).isEqualTo("00...111...2...333.44.5555.6666.777.888899")

        val actual = moveFiles(blockMap)

        val unpackedMapActual = unpackBlockMap(actual)

        assertThat(diskMapToString(unpackedMapActual)).isEqualTo("00992111777.44.333....5555.6666.....8888..")

//        assertThat(actual).containsExactlyElementsOf(expected)
    }

    @Test
    fun testPuzzle2() {
        val input = "2333133121414131402"

        val result = puzzle2(input)

        assertThat(result).isEqualTo(2858)
    }

    companion object {

        @JvmStatic
        fun unpackSourceProvider():Stream<Pair<String, List<Int>>> = Stream.of(
            "12345" to listOf(0,-1,-1,1,1,1,-1,-1,-1,-1,2,2,2,2,2),
            "2333133121414131402" to listOf(0,0,-1,-1,-1,1,1,1,-1,-1,-1,2,-1,-1,-1,3,3,3,-1,4,4,-1,5,5,5,5,-1,6,6,6,6,-1,7,7,7,-1,8,8,8,8,9,9)
        )

        @JvmStatic
        fun moveBlocksSourceProvider():Stream<Pair<List<Int>, List<Int>>> = Stream.of(
            listOf(0,-1,-1,1,1,1,-1,-1,-1,-1,2,2,2,2,2) to listOf(0,2,2,1,1,1,2,2,2,-1,-1,-1,-1,-1,-1),
            listOf(0,0,-1,-1,-1,1,1,1,-1,-1,-1,2,-1,-1,-1,3,3,3,-1,4,4,-1,5,5,5,5,-1,6,6,6,6,-1,7,7,7,-1,8,8,8,8,9,9) to
            listOf(0,0,9,9,8,1,1,1,8,8,8,2,7,7,7,3,3,3,6,4,4,6,5,5,5,5,6,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
        )

        @JvmStatic
        fun parseDiskSourceProvider():Stream<Pair<String, List<Block>>> = Stream.of(
            "12345" to listOf(Block(0,1), Block(-1,2), Block(1,3), Block(-1,4), Block(2,5)),
            "2333133121414131402" to listOf(Block(0, 2), Block(-1, 3), Block(1, 3), Block(-1, 3),
                Block(2,1), Block(-1, 3), Block(3, 3), Block(-1, 1), Block(4,2), Block(-1,1),
                Block(5, 4), Block(-1, 1), Block(6, 4), Block(-1,1), Block(7,3), Block(-1,1),
                Block(8,4), Block(-1, 0), Block(9,2))
        )
    }
}