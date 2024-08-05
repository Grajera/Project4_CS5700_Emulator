package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class ConvertByteToAsciiInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var sourceRegister: R
    lateinit var targetRegister: R

    // Retrieve the source and target registers from the nibbles
    public override fun processNibblesForInstruction() {
        sourceRegister = r[nibbles[0].toInt()]
        targetRegister = r[nibbles[1].toInt()]
    }

    // Convert the byte in the source register to ASCII and store it in the target register
    public override fun performInstruction() {
        val value = sourceRegister.readRegister()[0].toInt()
        require(value in 0..0xF) { "Value in source register is out of range (0-F)." }

        // Convert to ASCII: '0'-'9' for 0-9, 'A'-'F' for 10-15
        val asciiValue = (if (value < 10) value + '0'.code else value - 10 + 'A'.code).toByte()
        targetRegister.operateOnRegister(byteArrayOf(asciiValue))
    }
}
