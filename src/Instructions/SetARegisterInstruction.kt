package Instructions

import com.emulator.intToByteArray
import Memory.Registry_Handlers.AManager.a

class SetARegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Byte array to hold the address for the A register
    private lateinit var addressBytes: ByteArray

    // Processes the nibbles to construct the address
    public override fun processNibblesForInstruction() {
        val address = (nibbles[0].toInt() shl 8) or
                (nibbles[1].toInt() shl 4) or
                nibbles[2].toInt()
        // Convert the address to a byte array
        addressBytes = intToByteArray(address)
    }

    // Performs the operation of setting the A register
    public override fun performInstruction() {
        // Set the A register with the calculated address
        a.operateOnRegister(addressBytes)
    }
}
