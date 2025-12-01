import adventofcode2024.Point
import adventofcode2024.day6.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day6Tests {

    private val testInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

    @Test
    fun testParseScene() {
        val expectedScene = Scene(
            scene = listOf(
                "....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."
            ).map { it.toCharArray() },
            guard = Guard(
                cord = Point(6, 4),
                direction = Guard.Direction.UP,
            )
        )

        val scene = parseScene(testInput.lines())
        assertThat(scene.scene).containsExactlyElementsOf(expectedScene.scene)
        assertThat(scene.guard).isEqualTo(expectedScene.guard)
    }

    @Test
    fun testPuzzle() {
        val result = puzzle1(testInput.lines())

        assertThat(result).isEqualTo(41)
    }

    @ParameterizedTest
    @MethodSource("provideLoopFields")
    fun testLoopFields(testCase: Pair<String, Boolean>) {
        val scene = parseScene(testCase.first.lines())

        val result = checkIsSceneLooped(scene)

        assertThat(result).isEqualTo(testCase.second)
    }

    @Test
    fun testPuzzle2() {
        val result = puzzle2(testInput.lines())

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun testFindLoopedScenes() {
        val expectedScenes = provideLoopFields()
            .filter { (_, l) -> l }
            .map {
                it.first.replace('|', '.')
                    .replace('-', '.')
                    .replace('+', '.')
            }.onEach {
                println("------------------------------------")
                println(it)
                println("------------------------------------")
            }.map {
                parseScene(it.lines())
            }

        val loopedScenes = findLoopedScenes(testInput.lines())
        val loopedScenesStr = loopedScenes.map { it.scene }
            .map { it.joinToString("\n") { it.joinToString("")} }
            .toTypedArray()

        val expectedScenesStr = expectedScenes.map { it.scene }
            .map { it.joinToString("\n") { it.joinToString("") } }
            .toTypedArray()

        assertThat(loopedScenesStr).containsAll(expectedScenesStr.toList())
//        assertContentEquals(expectedScenesStr, loopedScenesStr)

        assertThat(loopedScenes).containsAll(expectedScenes)
    }

    companion object {

        @JvmStatic
        fun provideLoopFields(): List<Pair<String, Boolean>> =
            listOf(
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ....|..#|.
                    ....|...|.
                    .#.O^---+.
                    ........#.
                    #.........
                    ......#...
                """.trimIndent() to true,
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ..+-+-+#|.
                    ..|.|.|.|.
                    .#+-^-+-+.
                    ......O.#.
                    #.........
                    ......#...
                """.trimIndent() to true,
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ..+-+-+#|.
                    ..|.|.|.|.
                    .#+-^-+-+.
                    .+----+O#.
                    #+----+...
                    ......#...
                """.trimIndent() to true,
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ..+-+-+#|.
                    ..|.|.|.|.
                    .#+-^-+-+.
                    ..|...|.#.
                    #O+---+...
                    ......#...
                """.trimIndent() to true,
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ..+-+-+#|.
                    ..|.|.|.|.
                    .#+-^-+-+.
                    ....|.|.#.
                    #..O+-+...
                    ......#...
                """.trimIndent() to true,
                """
                    ....#.....
                    ....+---+#
                    ....|...|.
                    ..#.|...|.
                    ..+-+-+#|.
                    ..|.|.|.|.
                    .#+-^-+-+.
                    .+----++#.
                    #+----++..
                    ......#O..
                """.trimIndent() to true,
                """
                    ....#.....
                    .........#
                    ..........
                    ..#.......
                    .......#..
                    ..........
                    .#..^.....
                    ........#.
                    #.........
                    ......#...
                """.trimIndent() to false
            )
    }
}