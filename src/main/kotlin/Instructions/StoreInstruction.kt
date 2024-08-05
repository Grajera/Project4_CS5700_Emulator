package Instructions

import com.emulator.mergeNibblesToByte
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class StoreInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    var byte: Byte = 0

    public override fun processNibblesForInstruction() {
        val registerXIndex = nibbles[0].toInt()
        registerX = r[registerXIndex]

        val highNibble = nibbles[1]
        val lowNibble = nibbles[2]

        byte = mergeNibblesToByte(highNibble, lowNibble)
    }

    public override fun performInstruction() {
        registerX.operateOnRegister(byteArrayOf(byte))
    }
}