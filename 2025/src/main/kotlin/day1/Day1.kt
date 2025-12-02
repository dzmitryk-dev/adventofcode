package day1

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day1.input")
    val parsedInput = parseInput(input)

    runPuzzle(1) {
        part1(parsedInput)
    }

    runPuzzle(2) {
        part2(parsedInput)
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
    val steps = rotation.steps % 100
    val newPosition = when (rotation.direction) {
        Rotation.Direction.LEFT -> (currentPosition - steps).let {
            if (it < 0) 100 + it else it
        }

        Rotation.Direction.RIGHT -> (currentPosition + steps) % 100
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

fun part2(input: List<Rotation>): Int {

    var currentPosition = 50
    var zeroCount = 0

    input.forEach { rotation ->
        val newPosition = rotateDial(currentPosition = currentPosition, rotation = rotation)

        if (newPosition == 0) {
            zeroCount += 1
        }

        zeroCount += rotation.steps / 100

        val actualSteps = rotation.steps % 100
        if (actualSteps > 0 && newPosition != 0 && currentPosition != 0) {
            when (rotation.direction) {
                Rotation.Direction.LEFT -> {
                    if (newPosition > currentPosition) {
                        zeroCount += 1
                    }
                }
                Rotation.Direction.RIGHT -> {
                    if (newPosition < currentPosition) {
                        zeroCount += 1
                    }
                }
            }
        }

        currentPosition = newPosition
    }

    return zeroCount
}