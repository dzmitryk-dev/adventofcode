package adventofcode2024.day17

import adventofcode2024.runPuzzle

fun main() {
    runPuzzle(1) {
        val vm = VM(A = 27575648).apply {
            loadProgram("2,4,1,2,7,5,4,1,1,3,5,5,0,3,3,0")
        }
        vm.runProgram()

        val output = vm.getOutput()
        println("Output: $output")
        val registers = vm.dumpRegisters()
        println("Registers: $registers")
    }
}

data class VM(
    private var A: Int = 0,
    private var B: Int = 0,
    private var C: Int = 0,
) {
    enum class OPCODE(val code: Int) {
        ADV(0),
        BXL(1),
        BST(2),
        JNZ(3),
        BXC(4),
        OUT(5),
        BDV(6),
        CDV(7),
    }

    private val output = mutableListOf<Int>()
    private var instructionPointer: Int = 0
    private lateinit var program: IntArray

    fun loadProgram(programCode: String) {
        program = programCode.split(",").map { it.toInt() }.toIntArray()
    }

    fun runProgram() {
        try {
            while (instructionPointer < program.size) {
                val opcode = program[instructionPointer]
                val operand = program[instructionPointer + 1]
                executeCommand(opcode, operand)
                println(toString())
            }
        } catch (ex: ArrayIndexOutOfBoundsException) {
            println("VM halted")
        }
    }

    private fun executeCommand(opcode: Int, operand: Int) {
        when (opcode) {
            OPCODE.ADV.code -> {
                val decodedOperand = decodeComboOperand(operand)
                println("ADV A=$A operand=$decodedOperand")
                var denominator = 1
                repeat(decodedOperand) {
                    denominator *= 2
                }
                A /= denominator
                instructionPointer += 2
            }
            OPCODE.BXL.code -> {
                println("BXL B=$B operand=$operand")
                B = B xor operand
                instructionPointer += 2
            }
            OPCODE.BST.code -> {
                val decodedOperand = decodeComboOperand(operand)
                println("BST B=$B operand=$decodedOperand")
                B = decodedOperand.mod(8)
                instructionPointer += 2
            }
            OPCODE.JNZ.code -> {
                println("JNZ A=$A")
                if (A != 0) {
                    instructionPointer = operand
                } else {
                    instructionPointer += 2
                }
            }
            OPCODE.BXC.code -> {
                println("BXC B=$B C=$C")
                B = B xor C
                instructionPointer += 2
            }
            OPCODE.OUT.code -> {
                val decodedOperand = decodeComboOperand(operand)
                println("OUT decodedOperand = $decodedOperand")
                output.add(decodedOperand.mod(8))
                instructionPointer += 2
            }
            OPCODE.BDV.code -> {
                val decodedOperand = decodeComboOperand(operand)
                println("BDV A=$A decodedOperand = $decodedOperand")
                var denominator = 1
                repeat(decodedOperand) {
                    denominator *= 2
                }
                B = A / denominator
                instructionPointer += 2
            }
            OPCODE.CDV.code -> {
                val decodedOperand = decodeComboOperand(operand)
                println("CDV A=$A decodedOperand = $decodedOperand")
                var denominator = 1
                repeat(decodedOperand) {
                    denominator *= 2
                }
                C = A / denominator
                instructionPointer += 2
            }
            else -> {
                println("Invalid opcode")
                instructionPointer += 2
            }
        }
    }

    private fun decodeComboOperand(operand: Int): Int {
        return when (operand) {
            in (0..3)-> operand
            4 -> A
            5 -> B
            6 -> C
            7 -> throw IllegalArgumentException("Reserved operand found")
            else -> throw IllegalArgumentException("Invalid operand")
        }
    }

    fun getOutput(): List<Int> {
        return output
    }

    fun dumpRegisters(): List<Int> {
        return listOf(A, B, C)
    }
}