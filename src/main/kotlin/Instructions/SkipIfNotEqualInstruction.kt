package Instructions

import com.emulator.Utils.byteArrayToInteger
import com.emulator.Utils.integerToByteArray
import Memory.Registry_Handlers.PRegisterInstance.p
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class SkipIfNotEqualInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    lateinit var registerY: R
    private var shouldSkipNextInstruction = false

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]
        registerY = r[nibbles[1].toInt()]

        // Compare values of the registers to determine if we should skip
        shouldSkipNextInstruction = registerX.readRegister()[0].toInt() != registerY.readRegister()[0].toInt()
    }

    public override fun incrementProgramCounter() {
        val currentP = byteArrayToInteger(p.readRegister())
        val offset = if (shouldSkipNextInstruction) 4 else 2
        p.operateOnRegister(integerToByteArray(currentP + offset))
    }
}
