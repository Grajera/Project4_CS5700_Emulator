package Instructions

import com.emulator.combineNibblesToByte
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class StoreInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R
    var byte: Byte = 0

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]

        val highNibble = nibbles[1]
        val lowNibble = nibbles[2]

        byte = combineNibblesToByte(highNibble, lowNibble)
    }

    public override fun performOperation() {
        rx.operate(byteArrayOf(byte))
    }
}