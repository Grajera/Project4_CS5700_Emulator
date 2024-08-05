package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class AddInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    lateinit var registerY: R
    lateinit var registerZ: R

    // Process the nibbles to initialize registers
    public override fun processNibblesForInstruction() {
        registerX = r[nibbles[0].toInt()]
        registerY = r[nibbles[1].toInt()]
        registerZ = r[nibbles[2].toInt()]
    }

    // Perform the addition and store the result in the specified register
    public override fun performInstruction() {
        val result = (registerX.readRegister()[0].toInt() + registerY.readRegister()[0].toInt()).toByte()
        registerZ.operateOnRegister(byteArrayOf(result))
    }
}
