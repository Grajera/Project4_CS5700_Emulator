package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class ConvertByteToAsciiInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    private lateinit var sourceRegister: R
    private lateinit var targetRegister: R

    public override fun runTask() {
        sourceRegister = r[nibbles[0].toInt()]
        targetRegister = r[nibbles[1].toInt()]

        // Convert the byte in the source register to ASCII and store it in the target register
        val value = sourceRegister.readRegister()[0].toInt()
        require(value in 0..0xF) { "Value in source register is out of range (0-127)." }

        val asciiValue = (if (value < 10) value + '0'.code else value - 10 + 'A'.code).toByte()
        targetRegister.operateOnRegister(byteArrayOf(asciiValue))
    }
}
