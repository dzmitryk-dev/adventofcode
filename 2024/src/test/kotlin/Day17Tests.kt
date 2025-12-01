import adventofcode2024.day17.VM
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Tests {

    @Test
    fun testExample1() {
        val vm = VM(C = 9).apply {
            loadProgram("2,6")
        }
        vm.runProgram()

        assertThat(vm.dumpRegisters()).containsExactly(0 ,1, 9)
    }

    @Test
    fun testExample2() {
        val vm = VM(A = 10).apply {
            loadProgram("5,0,5,1,5,4")
        }
        vm.runProgram()

        assertThat(vm.getOutput()).containsExactly(0, 1, 2)
    }

    @Test
    fun testExample3() {
        val vm = VM(A = 2024).apply {
            loadProgram("0,1,5,4,3,0")
        }
        vm.runProgram()

        assertThat(vm.getOutput()).containsExactly(4,2,5,6,7,7,7,7,3,1,0)
        assertThat(vm.dumpRegisters()).containsExactly(0, 0, 0)
    }

    @Test
    fun testExample4() {
        val vm = VM(B = 29).apply {
            loadProgram("1,7")
        }
        vm.runProgram()

        assertThat(vm.dumpRegisters()).containsExactly(0, 26, 0)
    }

    @Test
    fun testExample5() {
        val vm = VM(B = 2024, C = 43690).apply {
            loadProgram("4,0")
        }
        vm.runProgram()

        assertThat(vm.dumpRegisters()).containsExactly(0, 44354, 43690)
    }

    @Test
    fun testExample6() {
        val vm = VM(A = 729).apply {
            loadProgram("0,1,5,4,3,0")
        }
        vm.runProgram()

        assertThat(vm.getOutput()).containsExactly(4,6,3,5,6,3,5,2,1,0)
    }
}