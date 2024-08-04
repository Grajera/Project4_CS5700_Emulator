package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

// Class representing a subtract instruction that extends BasicInstruction
class SubtractInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register for the first operand
    private lateinit var registerX: R
    // Register for the second operand
    private lateinit var registerY: R
    // Register for the result
    private lateinit var registerZ: R

    // Processes the nibbles to identify the registers
    public override fun processNibblesForInstruction() {
        // First operand register
        registerX = r[nibbles[0].toInt()]
        // Second operand register
        registerY = r[nibbles[1].toInt()]
        // Result register
        registerZ = r[nibbles[2].toInt()]
    }

    // Performs the subtraction operation
    public override fun performInstruction() {
        // Read values from the registers
        val valueX = registerX.readRegister()[0].toInt()
        val valueY = registerY.readRegister()[0].toInt()

        // Store the result in the result register
        registerZ.operateOnRegister(byteArrayOf((valueX - valueY).toByte()))
    }
}
