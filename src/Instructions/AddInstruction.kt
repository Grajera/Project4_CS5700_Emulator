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
    public override fun processNibbles() {
        rx = r[nibbles[0].toInt()]
        ry = r[nibbles[1].toInt()]
        rz = r[nibbles[2].toInt()]
    }

    // Perform the addition and store the result in the specified register
    public override fun performOperation() {
        val result = (rx.read()[0].toInt() + ry.read()[0].toInt()).toByte()
        rz.operate(byteArrayOf(result))
    }
}
