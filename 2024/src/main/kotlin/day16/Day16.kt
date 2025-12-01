package adventofcode2024.day16

import adventofcode2024.*
import java.util.*

fun main() {
    val input = readInput("day16.input")

    runPuzzle(1) {
        val maze = parseInput(input)
        val (path, score) = findBestPathWithDijkstra(maze)
        println(visualizeMaze(maze, path))
        score
    }
}

data class Maze(
    val start: Point,
    val finish: Point,
    val corridors: Set<Point>,
)

fun parseInput(input: List<String>): Maze {
    lateinit var start: Point
    lateinit var finish: Point
    val corridors = buildSet {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                val point = Point(x, y)
                when (char) {
                    'S' -> start = point
                    'E' -> finish = point
                    '.' -> add(point)
                }
            }
        }
    }

    return Maze(
        start = start,
        finish = finish,
        corridors = corridors,
    )
}

fun visualizeMaze(maze: Maze, path: Set<Point> = emptySet()): String {
    val maxX = maze.corridors.maxOf { it.line } + 1
    val maxY = maze.corridors.maxOf { it.col } + 1

    return buildString {
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val point = Point(x, y)
                when {
                    point == maze.start -> append('S')
                    point == maze.finish -> append('E')
                    maze.corridors.contains(point) -> if(path.contains(point)) {
                        append('x')
                    } else {
                        append('.')
                    }
                    else -> append('#')
                }
            }
            append('\n')
        }
    }
}

private fun calculatePathScore(path: List<Point>): Int {
    var score = 0

    // Определяем начальное направление
    var prevDirection: Pair<Int, Int> = Pair(0, 1)

    for (i in 1 until path.size) {
        val prevPoint = path[i - 1]
        val currPoint = path[i]

        // Вычисляем направление между текущей и предыдущей точкой
        val currDirection = Pair(currPoint.line - prevPoint.line, currPoint.col - prevPoint.col)

        // Если направление изменилось, увеличиваем счётчик
        if (currDirection != prevDirection) {
            score += 1000
        }
        score++

        // Обновляем предыдущее направление
        prevDirection = currDirection
    }
    return score
}

fun findBestPath(maze: Maze): Pair<Set<Point>, Int> {
    fun step(current: Point, path: Set<Point>): List<Set<Point>> {
        if (current == maze.finish) {
//            println("Riches the end")
            return listOf(path)
        }

        val nextSteps = current.pointsAround()
            .filter { it in maze.corridors || it == maze.finish }
            .filter { it !in path }
            .toSet()
        val nextPaths = nextSteps
            .flatMap { step(it, path + it) }
            .filter { it.isNotEmpty() }
            .toList()

        return nextPaths
    }

    return step(maze.start, setOf(maze.start))
        .map { it to calculatePathScore(it.toList()) }
        .minBy { (_, score) -> score }
}

fun findBestPathWithDijkstra(maze: Maze): Pair<Set<Point>, Int> {
    data class Node(val point: Point, val score: Int, val path: Set<Point>, val direction: Pair<Int, Int>)

    val startNode = Node(maze.start, 0, setOf(maze.start), Pair(0, 1))
    val priorityQueue = PriorityQueue<Node>(compareBy { it.score })
    priorityQueue.add(startNode)

    val visited = mutableSetOf<Point>()

    val paths = mutableSetOf<Node>()

    while (priorityQueue.isNotEmpty()) {
        val currentNode = priorityQueue.poll()

        // Если мы достигли финиша, возвращаем путь и счёт
        if (currentNode.point == maze.finish) {
            paths.add(currentNode)
            continue
        }

        // Если точка уже была посещена, пропускаем
        if (currentNode.point in visited) continue
        visited.add(currentNode.point)

        // Рассматриваем соседние точки
        val nextSteps = currentNode.point.pointsAround()
            .filter { it in maze.corridors || it == maze.finish } // Только коридоры и финиш
            .filter { it !in currentNode.path } // Избегаем точек, которые уже в пути

        for (nextPoint in nextSteps) {
            val currDirection = Pair(
                nextPoint.line - currentNode.point.line,
                nextPoint.col - currentNode.point.col
            )

            val turnCost = if (currDirection != currentNode.direction) 1000 else 0
            val newScore = currentNode.score + turnCost + 1

            val newNode = Node(nextPoint, newScore, currentNode.path + nextPoint, currDirection)
            priorityQueue.add(newNode)
        }
    }

    return paths.minBy { it.score }.let { node -> node.path to node.score }
}
