package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.ARegisterManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class WriteInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R // Register for the operation

    // Processes the nibbles to identify the register
    public override fun processNibblesForInstruction() {
        // Get the register index from nibbles
        val registerXIndex = nibbles[0].toInt()
        // Validate register index
        require(registerXIndex in r.indices) { "Invalid register index: $registerXIndex" }
        // Assign the corresponding register to registerX
        registerX = r[registerXIndex]
    }

    // Performs the write operation based on the state of RAM or ROM
    public override fun performInstruction() {
        // Read the address from the A register
        val addressToWriteTo = byteArrayToInt(a.readRegister())
        // Check if the address is valid
        require(addressToWriteTo in 0..<4096) { "Invalid address: $addressToWriteTo. Must be in the range [0, 4096)." }
        // Ensure M register has valid data
        require(m.readRegister().isNotEmpty()) { "M register is empty." }
        // Read the value from the selected register
        val valueToWriteOut = registerX.readRegister()[0]

        // Write the value to ROM or RAM based on the isUsingROM flag
        if (m.readRegister()[0].toInt() != 0) {
            RomManager.getRom()?.writeToMemory(addressToWriteTo, valueToWriteOut) ?: println("ROM is not initialized") // Handle uninitialized ROM
        } else {
            // Write value to RAM
            RAM.writeToMemory(addressToWriteTo, valueToWriteOut)
        }
    }
}
