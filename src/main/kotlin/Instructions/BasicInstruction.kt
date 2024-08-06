package Instructions

import com.emulator.Utils.byteArrayToInteger
import com.emulator.Utils.integerToByteArray
import Memory.Registry_Handlers.PRegisterInstance.p

abstract class BasicInstruction(
    protected val nibbles: ByteArray
) {
    init {
        require(nibbles.size == 3) { "Nibbles array must contain exactly 3 elements." }
    }

    // Execute the instruction: perform operation and update the program counter
    fun execute() {
        runTask()
        incrementProgramCounter()
    }

    // Increment the program counter by 2
    protected open fun incrementProgramCounter() {
        p.operateOnRegister(integerToByteArray(byteArrayToInteger(p.readRegister()) + 2))
    }

    // Abstract method to be implemented by subclasses
    protected abstract fun runTask()
}
