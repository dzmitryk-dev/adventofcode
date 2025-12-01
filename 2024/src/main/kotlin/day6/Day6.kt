package adventofcode2024.day6

import adventofcode2024.Point
import adventofcode2024.col
import adventofcode2024.line
import adventofcode2024.readInput
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day6.input")

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        puzzle1(input)
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(input)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )
}

data class Guard(
    val cord: Point,
    val direction: Direction,
) {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        fun next(): Direction {
            return when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
        }
    }
}

data class Scene(
    val scene: List<CharArray>,
    val guard: Guard,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Scene) return false

        val str1 = scene.joinToString { it.joinToString() }
        val str2 = other.scene.joinToString { it.joinToString() }

        if (str1 != str2) return false
        if (guard != other.guard) return false

        return true
    }

    override fun hashCode(): Int {
        return scene.map { it.contentHashCode() }.hashCode() + guard.hashCode()
    }

    override fun toString(): String {
        return "Field:\n${scene.joinToString("\n") { it.joinToString("") }}\nGuard: $guard"
    }
}

fun parseScene(input: List<String>): Scene {
//    println("Input: \n${input.joinToString("\n")}")
//    println("----------------------------------------------------")
    return Scene(
        scene = input.map { it.toCharArray() },
        guard = Guard(
            cord = run {
                for ( (lineIndex, line) in input.withIndex()) {
                    val charIndex = line.indexOf('^')
                    if (charIndex > -1) {
                        return@run Point(lineIndex, charIndex)
                    }
                }
                throw IllegalStateException("No guard found")
            },
            direction = Guard.Direction.UP,
        )
    )
}

fun Guard.walk(
    field: List<CharArray>,
): Guard? {
    val newPoint = when(direction) {
        Guard.Direction.UP -> cord.copy(first = cord.line - 1)
        Guard.Direction.DOWN -> cord.copy(first = cord.line + 1)
        Guard.Direction.LEFT -> cord.copy(second = cord.col - 1)
        Guard.Direction.RIGHT -> cord.copy(second = cord.col + 1)
    }
    return try {
        val content = field[newPoint.line][newPoint.col]

        if (content in setOf('#', 'O')) {
            copy(direction = direction.next())
        } else {
            copy(cord = newPoint)
        }
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}

fun calculateRoute(scene: Scene): Set<Point> {
    var guard = scene.guard

    val route = mutableSetOf(guard.cord)

    do {
        val newGuard = guard.walk(scene.scene)
        if (newGuard != null) {
            route.add(newGuard.cord)
            guard = newGuard
        }
    } while (newGuard != null)

    return route
}

fun puzzle1(input: List<String>): Int {
    val scene = parseScene(input)
    val route = calculateRoute(scene)
    return route.size
}

fun checkIsSceneLooped(scene: Scene): Boolean {
    var guard = scene.guard

    val route = mutableSetOf(guard)

    do {
        val newGuard = guard.walk(scene.scene)
        if (newGuard!= null) {
            if (!route.add(newGuard)) {
                return true
            }
            guard = newGuard
        }
    } while (newGuard != null)

    return false
}

fun findLoopedScenes(input: List<String>): Set<Scene> {
    val scene = parseScene(input)
    println("--------------------Scene-----------------------")
    println(scene)
    println("----------------------------------------------------")
    val route = calculateRoute(scene)
    println("-------------------Route----------------------------")
    scene.scene.forEachIndexed { line, chars ->
        chars.forEachIndexed { col, c ->
            if (Pair(line, col) in route) {
                print('X')
            } else {
                print(c)
            }
        }
        println()
    }
    println("----------------------------------------------------")
    val potentialObstacles = route.toList().let {
        it.subList(fromIndex = 2, toIndex = it.size).toSet()
    }
    val loopedScenes = mutableSetOf<Scene>()
    for (position in potentialObstacles) {
        val newField = scene.scene.toMutableList().apply {
            this[position.line] = this[position.line].toMutableList().apply {
                this[position.col] = 'O'
            }.toCharArray()
        }
        val newScene = scene.copy(scene = newField)
        println("--------------------Candidate-----------------------")
        println(newScene)
        println("----------------------------------------------------")
        if (checkIsSceneLooped(newScene)) {
            loopedScenes.add(newScene)
        }
    }
    return loopedScenes
}

fun puzzle2(input: List<String>): Int {
    return findLoopedScenes(input).size
}