package Instructions

import com.emulator.Utils.byteArrayToInteger
import Memory.RamInstance.RAM
import Memory.RomInstance
import Memory.Registry_Handlers.ARegisterInstance.a
import Memory.Registry_Handlers.MRegisterInstance.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class WriteInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R // Register for the operation

    public override fun runTask() {
        // Get the register index from nibbles
        registerX = r[nibbles[0].toInt()]

        // Read the address from the A register
        val addressToWriteTo = byteArrayToInteger(a.readRegister())
        require(addressToWriteTo in 0..4095) { "Invalid address: $addressToWriteTo. Must be in the range [0, 4096)." }

        // Ensure M register has valid data
        require(m.readRegister().isNotEmpty()) { "M register is empty." }

        // Read the value from the selected register
        val valueToWriteOut = registerX.readRegister()[0]

        // Write the value to ROM or RAM based on the M register's state
        if (m.readRegister()[0].toInt() != 0) {
            RomInstance.getRom()?.writeToMemoryLocation(addressToWriteTo, valueToWriteOut) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.writeToMemoryLocation(addressToWriteTo, valueToWriteOut)
        }
    }
}
