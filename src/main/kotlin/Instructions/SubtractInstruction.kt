package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class SubtractInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    lateinit var registerY: R
    lateinit var registerZ: R

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]
        registerY = r[nibbles[1].toInt()]
        registerZ = r[nibbles[2].toInt()]

        // Read values from the registers
        val valueX = registerX.readRegister()[0].toInt()
        val valueY = registerY.readRegister()[0].toInt()

        // Store the result in the result register
        registerZ.operateOnRegister(byteArrayOf((valueX - valueY).toByte()))
    }
}
