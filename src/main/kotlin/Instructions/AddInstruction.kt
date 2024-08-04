package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class AddInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    private lateinit var rx: R
    private lateinit var ry: R
    private lateinit var rz: R

    // Process the nibbles to initialize registers
    public override fun processNibblesForInstruction() {
        rx = r[nibbles[0].toInt()]
        ry = r[nibbles[1].toInt()]
        rz = r[nibbles[2].toInt()]
    }

    // Perform the addition and store the result in the specified register
    public override fun performInstruction() {
        val result = (rx.readRegister()[0].toInt() + ry.readRegister()[0].toInt()).toByte()
        rz.operateOnRegister(byteArrayOf(result))
    }
}
