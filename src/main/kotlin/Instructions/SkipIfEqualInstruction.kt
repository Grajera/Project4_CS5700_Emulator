package Instructions

import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class SkipIfEqualInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    lateinit var registerY: R
    private var shouldSkipNextInstruction = false

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]
        registerY = r[nibbles[1].toInt()]

        // Compare values of the registers to determine if we should skip
        shouldSkipNextInstruction = registerX.readRegister()[0].toInt() == registerY.readRegister()[0].toInt()
    }

    public override fun incrementProgramCounter() {
        val currentP = byteArrayToInt(p.readRegister())
        println("shouldSkipNextInstruction")
        println(shouldSkipNextInstruction)
        val offset = if (shouldSkipNextInstruction) 4 else 2
        p.operateOnRegister(intToByteArray(currentP + offset))
    }
}
