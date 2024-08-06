package Instructions

import Memory.Registry_Handlers.MRegisterManager.m

class SwitchMemoryInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    public override fun runTask() {
        // Read the current value of the M register
        val currentMemoryModeValue = m.readRegister()[0].toInt()

        // Determine the new memory mode value based on the current value
        val newMemoryModeValue = if (currentMemoryModeValue == 0) 1 else 0

        // Update the M register with the new memory mode value
        m.operateOnRegister(byteArrayOf(newMemoryModeValue.toByte()))
    }
}
