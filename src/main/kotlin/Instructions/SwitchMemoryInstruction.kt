package Instructions

import Memory.Registry_Handlers.MRegisterInstance.m

class SwitchMemoryInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    public override fun runTask() {
        // Determine the new memory mode value based on the current value
        val newMemoryModeValue = if (m.readRegister()[0].toInt() == 0) 1 else 0

        // Update the M register with the new memory mode value
        m.operateOnRegister(byteArrayOf(newMemoryModeValue.toByte()))
    }
}
