package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class WriteInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R // Register for the operation

    // Processes the nibbles to identify the register
    public override fun processNibblesForInstruction() {
        // Get the register index from nibbles
        val rxIndex = nibbles[0].toInt()
        // Validate register index
        require(rxIndex in r.indices) { "Invalid register index: $rxIndex" }
        // Assign the corresponding register to rx
        rx = r[rxIndex]
    }

    // Performs the write operation based on the state of RAM or ROM
    public override fun performInstruction() {
        // Read the address from the A register
        val addressBytes = a.readRegister()
        val address = byteArrayToInt(addressBytes)

        // Check if the address is valid
        require(address in 0..<4096) { "Invalid address: $address. Must be in the range [0, 4096)." }

        // Check if the M register indicates usage of ROM
        val mByteArray = m.readRegister()
        val isUsingROM = mByteArray[0].toInt() != 0
        // Ensure M register has valid data
        require(mByteArray.isNotEmpty()) { "M register is empty." }

        // Read the value from the selected register
        val value = rx.readRegister()[0]

        // Write the value to ROM or RAM based on the isUsingROM flag
        if (isUsingROM) {
            RomManager.getRom()?.let {
                // Write value to ROM
                it.write(address, value)
                println("Written value ${value.toUByte()} to ROM address $address")
            } ?: println("ROM is not initialized") // Handle uninitialized ROM
        } else {
            // Write value to RAM
            RAM.write(address, value)
            println("Written value ${value.toUByte()} to RAM address $address")
        }
    }
}
