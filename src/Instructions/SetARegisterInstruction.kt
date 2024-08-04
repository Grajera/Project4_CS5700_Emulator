package Instructions

import com.emulator.intToByteArray
import Memory.Registry_Handlers.AManager.a

class SetARegisterInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {
    lateinit var addressBytes: ByteArray

    public override fun processNibbles() {
        val highNibble = nibbles[0].toInt()
        val middleNibble = nibbles[1].toInt()
        val lowNibble = nibbles[2].toInt()

        val address = (highNibble shl 8) or (middleNibble shl 4) or lowNibble
        addressBytes = intToByteArray(address)
    }

    public override fun performOperation() {
        a.operate(addressBytes)}
}