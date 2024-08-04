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

    fun execute() {
        processNibbles()
        performOperation()
        incrementProgramCounter()
    }

    protected open fun incrementProgramCounter() {
        val currentPC = byteArrayToInt(p.read())
        val newPC = currentPC + 2
        p.operate(intToByteArray(newPC))
    }

    protected abstract fun processNibbles()
    protected abstract fun performOperation()
}