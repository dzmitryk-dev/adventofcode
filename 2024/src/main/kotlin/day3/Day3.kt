package adventofcode2024.day3

import adventofcode2024.readInput
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day3.input")

    println("Puzzle 1")
    val (output, duration) =  measureTimedValue {
        puzzle1(input.joinToString { it })
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms")

    println("Puzzle 2")
    val (output2, duration2) =  measureTimedValue {
        puzzle2(input.joinToString { it })
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms")
}

fun cleanUpInput(input: String): List<String> {
    // Updated regex to match "mul(x,y)" with flexible delimiters
    val regex = Regex("""mul\(\d+,\d+\)""")
    return regex.findAll(input).map { it.value }.toList()
}

fun mul(input: String): Int {
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    val (a, b) = regex.find(input)!!.destructured
    return a.toInt() * b.toInt()
}


fun puzzle1(input: String): Int {
    return cleanUpInput(input).sumOf { mul(it) }
}

fun cleanUpInput2(input: String): List<String> {
    val regex = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""")
    return regex.findAll(input).map { it.value }.toList()
}

fun puzzle2(input: String): Int {
    data class Acc(val flag: Boolean = true, val sum: Int = 0)
    return cleanUpInput2(input).fold(Acc()) { acc, it ->
        if (it == "do()") {
            acc.copy(flag = true)
        } else if (it == "don't()") {
            acc.copy(flag = false)
        } else {
            if (acc.flag) {
                acc.copy(sum = acc.sum + mul(it))
            } else {
                acc
            }
        }
    }.sum
}






