package Instructions

import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.R

class SkipIfEqualInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {
    var shouldSkip = false

    lateinit var rx: R
    lateinit var ry: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        val ryIndex = nibbles[1].toInt()
        rx = r[rxIndex]
        ry = r[ryIndex]
    }

    public override fun performOperation() {
        val rxValue = rx.read()[0].toInt()
        val ryValue = ry.read()[0].toInt()

        shouldSkip = (rxValue == ryValue)
    }

    public override fun incrementProgramCounter() {
        val currentPC = byteArrayToInt(p.read())
        val offset = if (shouldSkip) 4 else 2
        val newPC = currentPC + offset
        p.operate(intToByteArray(newPC))
    }
}