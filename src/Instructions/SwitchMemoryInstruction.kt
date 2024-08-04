package Instructions

import Memory.Registry_Handlers.MRegisterManager.m

class SwitchMemoryInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Processes the nibbles; no processing required for this instruction
    public override fun processNibbles() {
        // Nothing to process here... Move along.
    }

    // Performs the operation to switch the memory mode (RAM/ROM)
    public override fun performOperation() {
        // Read the current value of the M register
        val currentMemoryModeValue = m.read()[0].toInt()

        // Determine the new memory mode value based on the current value
        val newMemoryModeValue = if (currentMemoryModeValue == 0) {
            1 // Switch to ROM mode
        } else {
            0 // Switch to RAM mode
        }

        // Convert the new memory mode value to a ByteArray
        val newMemoryModeBytes = byteArrayOf(newMemoryModeValue.toByte())

        // Update the M register with the new memory mode value
        m.operate(newMemoryModeBytes)
    }
}
