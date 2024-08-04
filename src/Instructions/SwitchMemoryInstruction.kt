package Instructions

import Memory.Registry_Handlers.MRegisterManager.m

class SwitchMemoryInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    public override fun processNibbles() {
        // Nothing to process.
    }

    public override fun performOperation() {
        val currentMValue = m.read()[0].toInt()
        val newMValue = if (currentMValue == 0) {
            1
        } else {
            0
        }
        val newMValueBytes = byteArrayOf(newMValue.toByte())

        m.operate(newMValueBytes)
    }
}