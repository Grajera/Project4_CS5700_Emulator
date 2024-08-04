package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R

class ConvertToBaseTenInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    private lateinit var rx: R

    // Process the nibbles to retrieve the register
    override fun processNibbles() {
        rx = r[nibbles[0].toInt()]
    }

    // Perform the conversion and store the digits in memory
    override fun performOperation() {
        // Get the address from the A register
        val address = byteArrayToInt(a.read())

        // Get the value from the specified register
        val value = rx.read()[0].toInt()

        // Ensure the value is within the valid range (0-255)
        require(value in 0..255) { "Value in register rX must be between 0 and 255." }

        // Calculate the hundreds, tens, and ones digits
        val digits = intArrayOf(value / 100, (value % 100) / 10, value % 10)

        // Check if we are using ROM or RAM
        if (m.read()[0].toInt() != 0) {
            RomManager.getRom()?.let { rom ->
                // Write the digits to ROM
                for (i in digits.indices) {
                    rom.write(address + i, digits[i].toByte())
                }
            } ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            // Write the digits to RAM
            for (i in digits.indices) {
                RAM.write(address + i, digits[i].toByte())
            }
        }
    }
}
