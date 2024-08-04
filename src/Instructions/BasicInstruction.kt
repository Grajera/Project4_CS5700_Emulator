package Instructions

import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import Memory.Registry_Handlers.PRegisterManager.p

abstract class BasicInstruction(
    protected val nibbles: ByteArray
) {
    init {
        require(nibbles.size == 3) { "Nibbles array must contain exactly 3 elements." }
    }

    // Execute the instruction: process nibbles, perform operation, and update the program counter
    fun execute() {
        processNibbles()
        performOperation()
        incrementProgramCounter()
    }

    // Increment the program counter by 2
    protected open fun incrementProgramCounter() {
        val newPC = byteArrayToInt(p.read()) + 2
        p.operate(intToByteArray(newPC))
    }

    // Abstract methods to be implemented by subclasses
    protected abstract fun processNibbles()
    protected abstract fun performOperation()
}
