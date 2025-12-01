package adventofcode2024

// Re-export from shared utils for backward compatibility
public typealias Point = com.adventofcode.utils.Point
public val Point.line: Int get() = this.first
public val Point.col: Int get() = this.second
public fun Point.pointsAround(): List<Point> =
    listOf(
        Point(line, col - 1),
        Point(line - 1, col),
        Point(line, col + 1),
        Point(line + 1, col)
    )
public fun readInput(resourceName: String): List<String> =
    com.adventofcode.utils.readInput(resourceName)
public fun <T> runPuzzle(number: Int, block: () -> T) =
    com.adventofcode.utils.runPuzzle(number, block)