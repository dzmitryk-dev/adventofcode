import adventofcode2024.day15.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day15Test {

    @ParameterizedTest
    @MethodSource("testParseSource")
    fun testParseInput(testData: Triple<List<String>, String, String>) {
        val (scene, commands) = parseInput(testData.first)

        val renderedScene = visualize(scene)

        assertThat(renderedScene).isEqualTo(testData.second)

        val renderedCommands = commands.map {
            when (it.direction) {
                Command.Direction.UP -> '^'
                Command.Direction.DOWN -> 'v'
                Command.Direction.LEFT -> '<'
                Command.Direction.RIGHT -> '>'
            }
        }.joinToString("")

        assertThat(renderedCommands).isEqualTo(testData.third.replace("\n", ""))
    }

    @ParameterizedTest
    @MethodSource("testCommandExecutionSource")
    fun testExecuteCommand(testData: Pair<List<String>, String>) {
        val (input, expectedOutput) = testData
        val (scene, commands) = parseInput(input)

        val updatedScene = executeCommand(scene, commands.first())
        val renderedScene = visualize(updatedScene)

        assertThat(renderedScene)
            .overridingErrorMessage {
                "Input:\n ${input.joinToString { "$it\n" }}\n." +
                        "Expected:\n $expectedOutput\n." +
                        "Actual:\n $renderedScene"
            }
            .isEqualTo(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("testCommandsExecutionsSource")
    fun testCommandsExecutions(testData: Pair<String, String>) {
        val (input, expectedOutput) = testData
        val (scene, commands) = parseInput(input.lines())

        val updatedScene = executeCommands(scene, commands)
        val renderedScene = visualize(updatedScene)

        assertThat(renderedScene)
            .overridingErrorMessage {
                "Input:\n $input\n." +
                        "Expected:\n $expectedOutput\n." +
                        "Actual:\n $renderedScene"
            }
            .isEqualTo(expectedOutput)

    }

    @ParameterizedTest
    @MethodSource("calculateGPSTestSource")
    fun testCalculateGPSTest(testData: Pair<String, Int>) {
        val (input, expectedOutput) = testData
        val scene = parseScene(input.lines())

        val renderedScene = visualize(scene)
        println(renderedScene)

        val result = calculateGPS(scene)

        assertThat(result).isEqualTo(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("extendTestSource")
    fun testExtend(testData: Pair<String, String>) {
        val (input, expectedOutput) = testData

        val extendedSceneInput = extendScene(input.lines())

        val scene = parseScene(extendedSceneInput)
        val renderedScene = visualize(scene)

        assertThat(renderedScene)
            .overridingErrorMessage {
                "Input:\n $input\n." +
                        "Expected:\n $expectedOutput\n." +
                        "Actual:\n $renderedScene"
            }
            .isEqualTo(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("part2MoveTestSource")
    fun testPart2Move(testData: Pair<String, String>) {
        val (input, expectedOutput) = testData
        val (scene, commands) = parseInput2(input.lines())

        val updatedScene = executeCommands(scene, commands)
        val renderedScene = visualize(updatedScene)

        assertThat(renderedScene)
            .overridingErrorMessage {
                "Input:\n $input\n." +
                        "Expected:\n $expectedOutput\n." +
                        "Actual:\n $renderedScene"
            }
            .isEqualTo(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("testCommandExecutionSource2")
    fun testExecuteCommand2(testData: Triple<List<String>, Command.Direction, String>) {
        val (input, direction, expectedOutput) = testData
        val scene = parseScene(input)

        val updatedScene = executeCommand(scene, Command(direction))
        val renderedScene = visualize(updatedScene)

        assertThat(renderedScene)
            .overridingErrorMessage {
                "Input:\n ${input.joinToString { "$it\n" }}\n." +
                        "Expected:\n $expectedOutput\n." +
                        "Actual:\n $renderedScene"
            }
            .isEqualTo(expectedOutput)

    }

    companion object {

        private val testInputSmall = """
            ########
            #..O.O.#
            ##@.O..#
            #...O..#
            #.#.O..#
            #...O..#
            #......#
            ########

            <^^>>>vv<v>>v<<
        """.trimIndent()

        private val testInputLarge = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########

            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()

        @JvmStatic
        fun testParseSource() = Stream.of(
            Triple(
                testInputSmall.lines(),
                """
                    ########
                    #..O.O.#
                    ##@.O..#
                    #...O..#
                    #.#.O..#
                    #...O..#
                    #......#
                    ########""".trimIndent(),
                "<^^>>>vv<v>>v<<"
            ),
            Triple(
                testInputLarge.lines(),
                """
                    ##########
                    #..O..O.O#
                    #......O.#
                    #.OO..O.O#
                    #..O@..O.#
                    #O#..O...#
                    #O..O..O.#
                    #.OO.O.OO#
                    #....O...#
                    ##########""".trimIndent(),
                """
                    <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
                    vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
                    ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
                    <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
                    ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
                    ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
                    >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
                    <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
                    ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
                    v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
                """.trimIndent()
            )
        )

        @JvmStatic
        fun testCommandExecutionSource() = Stream.of(
            Pair(
                listOf(
                    "########",
                    "#..O.O.#",
                    "##@.O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    "<"
                ),
                "########\n#..O.O.#\n##@.O..#\n#...O..#\n#.#.O..#\n#...O..#\n#......#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#..O.O.#",
                    "##@.O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    "^"
                ),
                "########\n#.@O.O.#\n##..O..#\n#...O..#\n#.#.O..#\n#...O..#\n#......#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#.@O.O.#",
                    "##..O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    "^"
                ),
                "########\n#.@O.O.#\n##..O..#\n#...O..#\n#.#.O..#\n#...O..#\n#......#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#.@O.O.#",
                    "##..O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    ">"
                ),
                "########\n#..@OO.#\n##..O..#\n#...O..#\n#.#.O..#\n#...O..#\n#......#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#..@OO.#",
                    "##..O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    ">"
                ),
                "########\n#...@OO#\n##..O..#\n#...O..#\n#.#.O..#\n#...O..#\n#......#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#...@OO#",
                    "##..O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    "v"
                ),
                "########\n#....OO#\n##..@..#\n#...O..#\n#.#.O..#\n#...O..#\n#...O..#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##..@..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    "<"
                ),
                "########\n#....OO#\n##.@...#\n#...O..#\n#.#.O..#\n#...O..#\n#...O..#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##.@...#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    "v"
                ),
                "########\n#....OO#\n##.....#\n#..@O..#\n#.#.O..#\n#...O..#\n#...O..#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##.....#",
                    "#..@O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    ">"
                ),
                "########\n#....OO#\n##.....#\n#...@O.#\n#.#.O..#\n#...O..#\n#...O..#\n########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##.....#",
                    "#...@O.#",
                    "#.#.O..#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    ">"
                ),
                "########\n" +
                        "#....OO#\n" +
                        "##.....#\n" +
                        "#....@O#\n" +
                        "#.#.O..#\n" +
                        "#...O..#\n" +
                        "#...O..#\n" +
                        "########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##.....#",
                    "#....@O#",
                    "#.#.O..#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    "v"
                ),
                "########\n" +
                        "#....OO#\n" +
                        "##.....#\n" +
                        "#.....O#\n" +
                        "#.#.O@.#\n" +
                        "#...O..#\n" +
                        "#...O..#\n" +
                        "########"
            ),
            Pair(
                listOf(
                    "########",
                    "#....OO#",
                    "##.....#",
                    "#.....O#",
                    "#.#.O@.#",
                    "#...O..#",
                    "#...O..#",
                    "########",
                    "",
                    "<"
                ),
                "########\n" +
                        "#....OO#\n" +
                        "##.....#\n" +
                        "#.....O#\n" +
                        "#.#O@..#\n" +
                        "#...O..#\n" +
                        "#...O..#\n" +
                        "########"

            )
        )

        @JvmStatic
        fun testCommandsExecutionsSource() = Stream.of(
            testInputSmall to """
                ########
                #....OO#
                ##.....#
                #.....O#
                #.#O@..#
                #...O..#
                #...O..#
                ########
            """.trimIndent(),
            testInputLarge to """
                ##########
                #.O.O.OOO#
                #........#
                #OO......#
                #OO@.....#
                #O#.....O#
                #O.....OO#
                #O.....OO#
                #OO....OO#
                ##########
            """.trimIndent()
        )

        @JvmStatic
        fun calculateGPSTestSource() = Stream.of(
            """
                #######
                #...O..
                #.....@
            """.trimIndent() to 104,
            """
                ########
                #....OO#
                ##.....#
                #.....O#
                #.#O@..#
                #...O..#
                #...O..#
                ########
            """.trimIndent() to 2028,
            """
                ##########
                #.O.O.OOO#
                #........#
                #OO......#
                #OO@.....#
                #O#.....O#
                #O.....OO#
                #O.....OO#
                #OO....OO#
                ##########
            """.trimIndent() to 10092
        )

        @JvmStatic
        fun extendTestSource() = Stream.of(
            """
                #######
                #...#.#
                #.....#
                #..OO@#
                #..O..#
                #.....#
                #######
            """.trimIndent() to
                    """
                ##############
                ##......##..##
                ##..........##
                ##....[][]@.##
                ##....[]....##
                ##..........##
                ##############
            """.trimIndent(),
            """
                ##########
                #..O..O.O#
                #......O.#
                #.OO..O.O#
                #..O@..O.#
                #O#..O...#
                #O..O..O.#
                #.OO.O.OO#
                #....O...#
                ##########
            """.trimIndent() to
                    """
                ####################
                ##....[]....[]..[]##
                ##............[]..##
                ##..[][]....[]..[]##
                ##....[]@.....[]..##
                ##[]##....[]......##
                ##[]....[]....[]..##
                ##..[][]..[]..[][]##
                ##........[]......##
                ####################
            """.trimIndent()
        )

        @JvmStatic
        fun part2MoveTestSource() = Stream.of(
            """
               #######
               #...#.#
               #.....#
               #..OO@#
               #..O..#
               #.....#
               #######

               <vv<<^^<<^^
            """.trimIndent() to
                    """
                ##############
                ##...[].##..##
                ##...@.[]...##
                ##....[]....##
                ##..........##
                ##..........##
                ##############
            """.trimIndent(),
            testInputLarge to
                    """
                ####################
                ##[].......[].[][]##
                ##[]...........[].##
                ##[]........[][][]##
                ##[]......[]....[]##
                ##..##......[]....##
                ##..[]............##
                ##..@......[].[][]##
                ##......[][]..[]..##
                ####################
            """.trimIndent()
        )

        @JvmStatic
        fun testCommandExecutionSource2() = Stream.of(
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##....[][]@.##",
                    "##....[]....##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.LEFT,
                "##############\n" +
                        "##......##..##\n" +
                        "##..........##\n" +
                        "##...[][]@..##\n" +
                        "##....[]....##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##...[][]@..##",
                    "##....[]....##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.DOWN,
                "##############\n" +
                        "##......##..##\n" +
                        "##..........##\n" +
                        "##...[][]...##\n" +
                        "##....[].@..##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##...[][]...##",
                    "##....[].@..##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.DOWN,
                "##############\n" +
                        "##......##..##\n" +
                        "##..........##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##.......@..##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##.......@..##",
                    "##############",
                ),
                Command.Direction.LEFT,
                "##############\n" +
                        "##......##..##\n" +
                        "##..........##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##......@...##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##......@...##",
                    "##############",
                ),
                Command.Direction.LEFT,
                "##############\n" +
                        "##......##..##\n" +
                        "##..........##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##.....@....##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##..........##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##.....@....##",
                    "##############",
                ),
                Command.Direction.UP,
                "##############\n" +
                        "##......##..##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##.....@....##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##.....@....##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.LEFT,
                "##############\n" +
                        "##......##..##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##....@.....##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##....@.....##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.UP,
                "##############\n" +
                        "##......##..##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##....@.....##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##...[][]...##",
                    "##....[]....##",
                    "##....@.....##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.LEFT,
                "##############\n" +
                        "##......##..##\n" +
                        "##...[][]...##\n" +
                        "##....[]....##\n" +
                        "##...@......##\n" +
                        "##..........##\n" +
                        "##############"
            ),
            Triple(
                listOf(
                    "##############",
                    "##......##..##",
                    "##...[][]...##",
                    "##...@[]....##",
                    "##..........##",
                    "##..........##",
                    "##############",
                ),
                Command.Direction.UP,
                "##############\n" +
                        "##...[].##..##\n" +
                        "##...@.[]...##\n" +
                        "##....[]....##\n" +
                        "##..........##\n" +
                        "##..........##\n" +
                        "##############"
            )
        )

    }
}