package Instructions

import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class SkipIfNotEqualInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Flag indicating whether to skip the next instruction
    private var shouldSkipNextInstruction = false
    private lateinit var registerX: R
    private lateinit var registerY: R

    // Processes the nibbles to identify the registers
    public override fun processNibblesForInstruction() {
        registerX = r[nibbles[0].toInt()] // First operand register
        registerY = r[nibbles[1].toInt()] // Second operand register
    }

    // Determines if the next instruction should be skipped
    public override fun performInstruction() {
        shouldSkipNextInstruction = registerX.readRegister()[0].toInt() != registerY.readRegister()[0].toInt()
    }

    // Increments the program counter based on the skip flag
    public override fun incrementProgramCounter() {
        p.readRegister().let { currentPBytes ->
            val currentP = byteArrayToInt(currentPBytes)
            val offset = if (shouldSkipNextInstruction) 4 else 2
            p.operateOnRegister(intToByteArray(currentP + offset))
        }
    }
}
