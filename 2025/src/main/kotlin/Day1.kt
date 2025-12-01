import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day1.input")
    val parsedInput = parseInput(input)

    runPuzzle(1) {
        part1(parsedInput)
    }

}

data class Rotation(
    val direction: Direction,
    val steps: Int
) {
    enum class Direction {
        LEFT, RIGHT
    }
}

fun parseInput(input: List<String>): List<Rotation> {
    return input.map { line ->
        val direction = when(line.first()) {
            'L' -> Rotation.Direction.LEFT
            else -> Rotation.Direction.RIGHT
        }
        val steps = line.substring(startIndex = 1).toInt()
        Rotation(direction, steps)
    }
}

fun rotateDial(
    currentPosition: Int = 50,
    rotation: Rotation,
): Int {
    val newPosition = when (rotation.direction) {
        Rotation.Direction.LEFT -> (currentPosition - rotation.steps).let {
            val corrected = it % 100
            if (corrected < 0) 100 + corrected else corrected
        }

        Rotation.Direction.RIGHT -> (currentPosition + rotation.steps).let {
            it % 100
        }
    }
    return newPosition
}

tailrec fun applyRotations(
    list: List<Rotation>,
    currentPosition: Int = 50,
    accumulator: IntArray = intArrayOf()
): IntArray {
    if (list.isEmpty()) {
        return accumulator
    }
    val newPosition = rotateDial(currentPosition = currentPosition, rotation = list.first())
    val newAccumulator = accumulator + newPosition
    return applyRotations(list.subList(1, list.size), newPosition, newAccumulator)
}

fun part1(input: List<Rotation>): Int {
    return applyRotations(input).count { it == 0 }
}