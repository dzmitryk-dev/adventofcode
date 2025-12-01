package adventofcode2024.day14

import adventofcode2024.Point
import adventofcode2024.readInput
import adventofcode2024.runPuzzle

fun main() {
    val input = readInput("day14.input")

    runPuzzle(1) {
        val robots = parseInput(input)
        val fieldSize = Pair(101, 103)
        val seconds = 100
        val newRobots = simulate(robots, fieldSize, seconds)
        println(visualize(newRobots, fieldSize))
        calculateSafetyFactor(newRobots, fieldSize)
    }

    runPuzzle(2) {
        findMinimalSafetyFactor(input)
    }
}

data class Robot(
    val position: Point,
    val velocity: Pair<Int, Int>
)

fun parseInput(input: List<String>): List<Robot> {
    return input.map { line ->
        // Trim the line and split it into position and velocity parts
        val parts = line.trim().split(" ")

        // Ensure that there are exactly two parts (position and velocity)
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid input line format: $line")
        }

        val positionPart = parts[0]
        val velocityPart = parts[1]

        // Extract position coordinates
        val positionRegex = """p=(-?\d+),(-?\d+)""".toRegex()
        val positionMatch = positionRegex.find(positionPart)
            ?: throw IllegalArgumentException("Invalid position format in line: $line")
        val (posX, posY) = positionMatch.destructured.toList().map { it.toInt() }

        // Extract velocity values
        val velocityRegex = """v=(-?\d+),(-?\d+)""".toRegex()
        val velocityMatch = velocityRegex.find(velocityPart)
            ?: throw IllegalArgumentException("Invalid velocity format in line: $line")
        val (velX, velY) = velocityMatch.destructured.toList().map { it.toInt() }

        // Create and return a Robot instance
        Robot(
            position = Point(posX, posY),
            velocity = Pair(velX, velY)
        )
    }
}

fun visualize(robots: List<Robot>, fieldSize: Pair<Int, Int>): String {
    val (width, height) = fieldSize

    return buildList {
        repeat(height) { line ->
            buildList {
                repeat(width) { col ->
                    val robotsNumber = robots.count { (r, _) -> r.second == line && r.first == col }
                    if (robotsNumber > 0) {
                        robotsNumber.toString()
                    } else {
                        "."
                    }.let { add(it) }
                }
            }.joinToString("").let {
                add(it)
            }
        }
    }.joinToString("\n")
}

fun simulate(robots: List<Robot>, fieldSize: Pair<Int,Int>, seconds: Int): List<Robot> {
    val (width, height) = fieldSize
    var currentRobots = robots
    repeat(seconds) {
        currentRobots.map { robot ->
            val (posX, posY) = robot.position
            val (velX, velY) = robot.velocity
            val newPosition = Point(
                (posX + velX).let { newX ->
                    when {
                        newX < 0 -> width + newX
                        newX >= width -> newX - width
                        else -> newX
                    }
                },
                (posY + velY).let { newY ->
                    when {
                        newY < 0 -> height + newY
                        newY >= height -> newY - height
                        else -> newY
                    }
                }
            )
            robot.copy(position = newPosition)
        }.also { currentRobots = it }
    }
    return currentRobots
}

fun calculateSafetyFactor(robots: List<Robot>, fieldSize: Pair<Int, Int>): Int {
    val (width, height) = fieldSize
    val centerX = width / 2
    val centerY = height / 2

    val leftTop = robots.count { (position, _) ->
        val (x, y) = position
        x < centerX && y < centerY
    }
    val rightTop = robots.count { (position, _) ->
        val (x, y) = position
        x > centerX && y < centerY
    }
    val leftBottom = robots.count { (position, _) ->
        val (x, y) = position
        x < centerX && y > centerY
    }
    val rightBottom = robots.count { (position, _) ->
        val (x, y) = position
        x > centerX && y > centerY
    }
    return leftTop * rightTop * leftBottom * rightBottom
}

fun findMinimalSafetyFactor(input: List<String>): Int {
    val robots = parseInput(input)

    val fieldSize = Pair(101, 103)

//    var currentRobots = simulate(robots, fieldSize, 5000)
    var currentRobots = robots
    repeat(9000) { iteration ->
        println("iteration $iteration")
        currentRobots = simulate(currentRobots, fieldSize, 1)

//        val lll = visualize(currentRobots, fieldSize).contains("1111111111111111111111111111111")
//        if (lll) {
//            println("On iteration $iteration we found potential candidate")
//            println(visualize(currentRobots, fieldSize))
//            println()
//        }

        val robotsSet = currentRobots.map { it.position }.toSet()

        if(currentRobots.size == robotsSet.size) {
            println("On iteration $iteration we found potential candidate")
            println(visualize(currentRobots, fieldSize))
            println()

            val newRobots = simulate(robots, fieldSize, iteration + 1)
            println(visualize(newRobots, fieldSize))
            println()

            return iteration
        }
    }
    return 0
}