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

    public override fun runTask() {
        // Get the register index from nibbles
        registerX = r[nibbles[0].toInt()]

        // Read the address from the A register
        val addressToWriteTo = byteArrayToInt(a.readRegister())
        require(addressToWriteTo in 0..4095) { "Invalid address: $addressToWriteTo. Must be in the range [0, 4096)." }

        // Ensure M register has valid data
        require(m.readRegister().isNotEmpty()) { "M register is empty." }

        // Read the value from the selected register
        val valueToWriteOut = registerX.readRegister()[0]

        // Write the value to ROM or RAM based on the M register's state
        if (m.readRegister()[0].toInt() != 0) {
            RomManager.getRom()?.writeToMemory(addressToWriteTo, valueToWriteOut) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.writeToMemory(addressToWriteTo, valueToWriteOut)
        }
    }
}
