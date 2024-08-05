package Instructions

import com.emulator.byteArrayToInt
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.ARegisterManager.a
import Memory.Registry_Handlers.R

class ReadInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to store the read value
    lateinit var registerX: R

    public override fun processNibblesForInstruction() {
        // Get the index for the register to operate on
        val registerXIndex = nibbles[0].toInt()
        registerX = r[registerXIndex] // Assign the register
    }

    public override fun performInstruction() {
        // Read the mode from M register to determine memory usage
        val mByteArray = m.readRegister()
        val isUsingROM = mByteArray[0].toInt() != 0

        // Read the address from the A register
        val addressBytes = a.readRegister()
        val address = byteArrayToInt(addressBytes)

        // Read the value from the appropriate memory (ROM or RAM)
        val value = if (isUsingROM) {
            RomManager.getRom()?.read(address) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.read(address)
        }

        // Store the read value in the specified register
        registerX.operateOnRegister(byteArrayOf(value))
    }
}
