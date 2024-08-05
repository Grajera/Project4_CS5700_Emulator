package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.ARegisterManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R

class ConvertToBaseTenInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R

    // Process the nibbles to retrieve the register
    public override fun processNibblesForInstruction() {
        registerX = r[nibbles[0].toInt()]
    }

    // Perform the conversion and store the digits in memory
    public override fun performInstruction() {
        // Get the address from the A register
        val address = byteArrayToInt(a.readRegister())

        // Get the value from the specified register
        val value = registerX.readRegister()[0].toInt()

        // Ensure the value is within the valid range (0-255)
        require(value in 0..0XFF) { "Value in register registerX must be between 0 and 255." }

        // Calculate the hundreds, tens, and ones digits
        val digits = intArrayOf(value / 100, (value % 100) / 10, value % 10)

        // Check if we are using ROM or RAM
        if (m.readRegister()[0].toInt() != 0) {
            RomManager.getRom()?.let { rom ->
                // Write the digits to ROM
                for (i in digits.indices) {
                    rom.writeToMemory(address + i, digits[i].toByte())
                }
            } ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            // Write the digits to RAM
            for (i in digits.indices) {
                RAM.writeToMemory(address + i, digits[i].toByte())
            }
        }
    }
}
