package Instructions

import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.R

class SkipIfEqualInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Flag indicating whether to skip the next instruction
    private var shouldSkipNextInstruction = false
    private lateinit var registerX: R
    private lateinit var registerY: R

    // Processes the nibbles to identify the registers
    public override fun processNibbles() {
        registerX = r[nibbles[0].toInt()] // First operand register
        registerY = r[nibbles[1].toInt()] // Second operand register
    }

    // Determines if the next instruction should be skipped
    public override fun performOperation() {
        shouldSkipNextInstruction = registerX.read()[0].toInt() == registerY.read()[0].toInt()
    }

    // Increments the program counter based on the skip flag
    public override fun incrementProgramCounter() {
        p.read().let { currentPBytes ->
            val currentPC = byteArrayToInt(currentPBytes)
            val offset = if (shouldSkipNextInstruction) 4 else 2
            p.operate(intToByteArray(currentPC + offset))
        }
    }
}
