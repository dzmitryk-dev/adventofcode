package day3

import com.adventofcode.utils.readInput
import com.adventofcode.utils.runPuzzle

fun main() {
    val input = readInput("day3.input")

    runPuzzle(1) {
        part1(input)
    }
}

fun findJoltage(bank: CharSequence): Int {
    var index = 0

    var firstDigit: Char = 0.toChar()
    var secondDigit: Char = 0.toChar()

    do {
        val firstDigitCandidate = bank[index]
        val secondDigitCandidate = bank[index + 1]

        if (firstDigitCandidate > firstDigit) {
            firstDigit = firstDigitCandidate
            secondDigit = secondDigitCandidate
        }

        if (secondDigitCandidate > secondDigit) {
            secondDigit = secondDigitCandidate
        }

        index++
    } while (index < bank.lastIndex)

    return "${firstDigit}${secondDigit}".toInt()
}

fun part1(input: List<String>): Int = input.sumOf { findJoltage(it) }