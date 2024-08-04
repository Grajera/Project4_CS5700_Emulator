package Instructions

import com.emulator.byteArrayToInt
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.R

class ReadInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to store the read value
    lateinit var rx: R

    public override fun processNibbles() {
        // Get the index for the register to operate on
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex] // Assign the register
    }

    public override fun performOperation() {
        // Read the mode from M register to determine memory usage
        val mByteArray = m.read()
        val isUsingROM = mByteArray[0].toInt() != 0

        // Read the address from the A register
        val addressBytes = a.read()
        val address = byteArrayToInt(addressBytes)

        // Read the value from the appropriate memory (ROM or RAM)
        val value = if (isUsingROM) {
            RomManager.getRom()?.read(address) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.read(address)
        }

        // Store the read value in the specified register
        rx.operate(byteArrayOf(value))
    }
}
