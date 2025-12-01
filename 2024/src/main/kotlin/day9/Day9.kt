package adventofcode2024.day9

import adventofcode2024.readInput
import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("day9.input").first()

    println("Puzzle 1 started")
    val (output, duration) = measureTimedValue {
        calculateCheckSum(moveBlocks(unpackDiskMap(input)))
    }
    println("Puzzle 1 output: $output. Done in ${duration.inWholeMilliseconds} ms" )

    println("Puzzle 2 started")
    val (output2, duration2) = measureTimedValue {
        puzzle2(input)
    }
    println("Puzzle 2 output: $output2. Done in ${duration2.inWholeMilliseconds} ms" )
}

fun unpackDiskMap(input: String): List<Int> {
    val diskMap = mutableListOf<Int>()

    input.foldIndexed(diskMap) { index, acc, c ->
        val value = c.toString().toInt()
        val id = if (index % 2 == 0) {
            index / 2
        } else {
           -1
        }
        repeat(value) {
            acc.add(id)
        }
        acc
    }

    return diskMap
}

fun moveBlocks(input: List<Int>): List<Int> {
    val list = input.toMutableList()

    var empty = list.indexOfFirst { it == -1 }
    var data = list.lastIndex

    while (empty < data) {
        list[empty] = list[data]
        list[data] = -1

        empty = list.indexOfFirst { it == -1 }
        do {
            data--
        } while (list[data] == -1 && data > 0)
    }
    return list
}

fun calculateCheckSum(input: List<Int>): Long {
    return input.map { if (it > 0) it else 0 }
        .mapIndexed { index, i -> index * i.toLong() }.sum()
}

data class Block(val id: Int = EMPTY_ID, val size: Int = 0) {
    companion object {
        const val EMPTY_ID = -1
    }
}

fun parseDiskMap(input: String): List<Block> {
    return input.foldIndexed(mutableListOf()) { index, blockMap, c ->
        val value = c.toString().toInt()
        val id = if (index % 2 == 0) {
            index / 2
        } else {
            Block.EMPTY_ID
        }
        blockMap.add(Block(id = id, size = value))
        blockMap
    }
}

fun moveFiles(input: List<Block>): List<Block> {
    val list = input.toMutableList()

    var right = input.lastIndex

    while (right > 0) {
        val block = list[right]

        if (block.id != Block.EMPTY_ID) {
            val left = list.indexOfFirst { it.id == Block.EMPTY_ID && it.size >= block.size}
            if (left in 1..<right) {
                val empty = list[left]

                if (empty.size > block.size) {
                    list[left] = block
                    list[right] = Block(id = Block.EMPTY_ID, size = block.size)
                    list.add(left + 1, Block(id = Block.EMPTY_ID, size = empty.size - block.size))
                    right++
                } else {
                    list[left] = block
                    list[right] = Block(id = Block.EMPTY_ID, size = block.size)
                }
            }
        }
        right--
    }

    return list
}

fun unpackBlockMap(input: List<Block>): List<Int> {
    return buildList {
        input.forEach { block ->
            repeat(block.size) {
                add(block.id)
            }
        }
    }
}

fun puzzle2(input: String): Long {
    val blockMap = parseDiskMap(input)
    val newBlockMap = moveFiles(blockMap)
    val unpackedMap = unpackBlockMap(newBlockMap)
    return calculateCheckSum(unpackedMap)
}

fun diskMapToString(input: List<Int>): String {
    return input.joinToString("") { if (it > -1 ) { it.toString() } else "." }
}