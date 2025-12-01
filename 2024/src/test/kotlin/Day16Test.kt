import adventofcode2024.day16.findBestPath
import adventofcode2024.day16.findBestPathWithDijkstra
import adventofcode2024.day16.parseInput
import adventofcode2024.day16.visualizeMaze
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day16Test {

    @ParameterizedTest
    @MethodSource("provideTestInput")
    fun testParseMaze(input: String) {
        val maze = parseInput(input.lines())

        val mazeVisualization = visualizeMaze(maze)

        println("Input:\n $input \n")
        println("Visualization: \n $mazeVisualization \n")

        assertThat(mazeVisualization.normalize()).isEqualTo(input.normalize())
    }

    @ParameterizedTest
    @MethodSource("findPathTestInput")
    fun testFindPath(testData: Pair<String, Pair<String, Int>>) {
        val (input, expected) = testData
        val (expectedPath, expectedScore) = expected

        val maze = parseInput(input.lines())
        val result = findBestPath(maze)

        val mazeVisualization = visualizeMaze(maze, result.first)

        println("Input:\n $input \n")
        println("Path length: ${result.first.size}")
        println("Visualization: \n $mazeVisualization \n")
        println("Expected: \n $expectedPath \n")
        println("Expected score: $expectedScore. Actual score: ${result.second}")

        assertThat(mazeVisualization.normalize()).isEqualTo(expectedPath.normalize())
        assertThat(result.second).isEqualTo(expectedScore)
    }

    @ParameterizedTest
    @MethodSource("findPathTestInput")
    fun testFindPathDijkstra(testData: Pair<String, Pair<String, Int>>) {
        val (input, expected) = testData
        val (expectedPath, expectedScore) = expected

        val maze = parseInput(input.lines())
        val result = findBestPathWithDijkstra(maze)

        val mazeVisualization = visualizeMaze(maze, result.first)

        println("Input:\n $input \n")
        println("Path length: ${result.first.size}")
        println("Visualization: \n $mazeVisualization \n")
        println("Expected: \n $expectedPath \n")
        println("Expected score: $expectedScore. Actual score: ${result.second}")

//        assertThat(mazeVisualization.normalize()).isEqualTo(expectedPath.normalize())
        assertThat(result.second).isEqualTo(expectedScore)
    }

    private fun String.normalize(): String {
        return lines().joinToString("") { it.trim() }
    }

    companion object {
        private val testInput1 = """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent()

        private val testInput2 = """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent()

        private val testInput1Path = """
            ###############
            #.......#....E#
            #.#.###.#.###x#
            #.....#.#...#x#
            #.###.#####.#x#
            #.#.#.......#x#
            #.#.#####.###x#
            #..xxxxxxxxx#x#
            ###x#.#####x#x#
            #xxx#.....#x#x#
            #x#.#.###.#x#x#
            #x....#...#x#x#
            #x###.#.#.#x#x#
            #S..#.....#xxx#
            ###############
        """.trimIndent()

        private val testInput2Path = """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#x#
            #.#.#.#...#...#x#
            #.#.#.#.###.#.#x#
            #xxx#.#.#.....#x#
            #x#x#.#.#.#####x#
            #x#x..#.#.#xxxxx#
            #x#x#####.#x###.#
            #x#x#..xxxxx#...#
            #x#x###x#####.###
            #x#x#xxx#.....#.#
            #x#x#x#####.###.#
            #x#x#x........#.#
            #x#x#x#########.#
            #S#xxx..........#
            #################
        """.trimIndent()


        @JvmStatic
        fun provideTestInput() = Stream.of(
            testInput1,
            testInput2,
        )

        @JvmStatic
        fun findPathTestInput() = Stream.of(
            Pair(testInput1, Pair(testInput1Path, 7036)),
            Pair(testInput2, Pair(testInput2Path, 11048)),
        )
    }
}