package adventofcode2024.day15

import adventofcode2024.*


fun main() {
    val input = readInput("day15.input")

    runPuzzle(1) {
        part1(input)
    }
    runPuzzle(2) {
        part2(input)
    }
}

data class Scene(
    val size: Pair<Int, Int>,
    val items: Map<Point, Item>,
    val robot: Point,
) {
    enum class Item {
        WALL, BOX, BOX_LARGE_LEFT, BOX_LARGE_RIGHT
    }
}

data class Command(
    val direction: Direction,
) {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}

fun parseInput(input: List<String>): Pair<Scene, List<Command>> {
    val separator = input.indexOfFirst { it.isEmpty() }

    val scene = input.subList(0, separator).let { sceneInput ->
        parseScene(sceneInput)
    }

    val commands = parseCommands(input, separator)

    return scene to commands
}

private fun parseCommands(input: List<String>, separator: Int): List<Command> {
    return input.subList(separator + 1, input.size).let { commandsInput ->
        commandsInput.joinToString("").map {
            Command(
                when (it) {
                    '^' -> Command.Direction.UP
                    'v' -> Command.Direction.DOWN
                    '<' -> Command.Direction.LEFT
                    '>' -> Command.Direction.RIGHT
                    else -> error("Invalid command: $it")
                }
            )
        }
    }
}

fun parseScene(sceneInput: List<String>): Scene {
    val height = sceneInput.size
    val width = sceneInput.maxOf { it.length }

    lateinit var robot: Point

    val items: Map<Point, Scene.Item> = buildMap {
        sceneInput.forEachIndexed { line, symbols ->
            symbols.forEachIndexed { col, c ->
                when (c) {
                    '@' -> robot = Point(line, col)
                    '#' -> put(Point(line, col), Scene.Item.WALL)
                    'O' -> put(Point(line, col), Scene.Item.BOX)
                    '['-> put(Point(line, col), Scene.Item.BOX_LARGE_LEFT)
                    ']'-> put(Point(line, col), Scene.Item.BOX_LARGE_RIGHT)
                }
            }
        }
    }

    return Scene(Pair(width, height), items, robot)
}

fun visualize(scene: Scene): String {
    val (width, height) = scene.size
    val field = Array(height) { CharArray(width) { '.' } }

    scene.items.forEach { (position, item) ->
        val (line, col) = position
        field[line][col]  = when (item) {
            Scene.Item.WALL -> '#'
            Scene.Item.BOX ->  'O'
            Scene.Item.BOX_LARGE_LEFT -> '['
            Scene.Item.BOX_LARGE_RIGHT -> ']'
        }
    }
    scene.robot.let { (line, col) ->
        field[line][col] = '@'
    }

    return field.joinToString("\n") { it.joinToString("") }
}

fun executeCommand(scene: Scene, command: Command): Scene {
    fun Point.nextPoint(direction: Command.Direction) = when (direction) {
        Command.Direction.UP -> Point(line - 1, col)
        Command.Direction.DOWN -> Point(line + 1, col)
        Command.Direction.LEFT -> Point(line, col - 1)
        Command.Direction.RIGHT -> Point(line, col + 1)
    }

    fun moveBox(points: Set<Point>, items: Map<Point, Scene.Item>): Map<Point, Scene.Item>? {
        if (points.isEmpty()) return items

        val nextPoints = mutableSetOf<Point>()

        points.forEach { p ->
            val nextPoint = p.nextPoint(command.direction)
            when(items[nextPoint]) {
                Scene.Item.WALL -> return@moveBox null
                Scene.Item.BOX -> nextPoints.add(nextPoint)
                Scene.Item.BOX_LARGE_LEFT -> {
                    nextPoints.add(nextPoint)
                    if (command.direction in arrayOf(Command.Direction.UP, Command.Direction.DOWN)) {
                        nextPoints.add(Point(nextPoint.line, nextPoint.col + 1))
                    }
                }
                Scene.Item.BOX_LARGE_RIGHT -> {
                    if (command.direction in arrayOf(Command.Direction.UP, Command.Direction.DOWN)) {
                        nextPoints.add(Point(nextPoint.line, nextPoint.col - 1))
                    }
                    nextPoints.add(nextPoint)
                }
                null -> { /* DO NOTHING */ }
            }
        }

        val movedItems = moveBox(nextPoints, items)
        return movedItems?.toMutableMap()?.apply {
            points.forEach { p ->
                val nextP = p.nextPoint(command.direction)
                this[nextP] = items[p]!!
                this.remove(p)
            }
        }
    }

    val newRobot = scene.robot.nextPoint(command.direction)

    return when (scene.items[newRobot]) {
        Scene.Item.WALL -> scene
        Scene.Item.BOX_LARGE_LEFT -> {
            val set = if (command.direction in arrayOf(Command.Direction.UP, Command.Direction.DOWN)) {
                setOf(newRobot, newRobot.copy(second = newRobot.col + 1))
            } else {
                setOf(newRobot)
            }
            moveBox(set, scene.items)?.let { newItems ->
                scene.copy(robot = newRobot, items = newItems)
            } ?: scene
        }
        Scene.Item.BOX_LARGE_RIGHT -> {
            val set = if (command.direction in arrayOf(Command.Direction.UP, Command.Direction.DOWN)) {
                setOf(newRobot, newRobot.copy(second = newRobot.col - 1))
            } else {
                setOf(newRobot)
            }
            moveBox(set, scene.items)?.let { newItems ->
                scene.copy(robot = newRobot, items = newItems)
            } ?: scene
        }
        Scene.Item.BOX -> {
            moveBox(setOf(newRobot), scene.items)?.let { newItems ->
                scene.copy(robot = newRobot, items = newItems)
            } ?: scene
        }
        null -> scene.copy(robot = newRobot)
    }
}

fun executeCommands(scene: Scene, commands: List<Command>): Scene {
    return commands.fold(scene) { acc, command ->
        executeCommand(acc, command)
    }
}

fun calculateGPS(scene: Scene): Int {
    return scene.items.filter { it.value in arrayOf(Scene.Item.BOX, Scene.Item.BOX_LARGE_LEFT) }
        .keys.sumOf { (line, col) ->
            line * 100 + col
        }
}

fun part1(input: List<String>): Int {
    val (scene, commands) = parseInput(input)
    val finalScene = executeCommands(scene, commands)
    return calculateGPS(finalScene)
}

fun extendScene(input: List<String>): List<String> {
    return input.fold(mutableListOf()) { acc, line ->
        val newLine = buildString {
            line.forEach { c ->
                when (c) {
                    '@' -> append("@.")
                    'O' -> append("[]")
                    '#' -> append("##")
                    else -> append("..")
                }
            }
        }
        acc.add(newLine)
        acc
    }
}

fun parseInput2(input: List<String>): Pair<Scene, List<Command>> {
    val separator = input.indexOfFirst { it.isEmpty() }

    val scene = parseScene(extendScene(input.subList(0, separator)))

    val commands = parseCommands(input, separator)

    return scene to commands
}

fun part2(input: List<String>): Int {
    val (scene, commands) = parseInput2(input)
    val finalScene = executeCommands(scene, commands)
    return calculateGPS(finalScene)
}