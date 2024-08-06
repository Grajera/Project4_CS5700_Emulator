package Instructions

import com.emulator.Utils.integerToByteArray
import Memory.Registry_Handlers.ARegisterInstance.a

class SetARegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    public override fun runTask() {
        val address = (nibbles[0].toInt() shl 8) or
                (nibbles[1].toInt() shl 4) or
                nibbles[2].toInt()

        // Validate address
        require(address in 0..0xFFF) { "Address must be between 0 and 4095." }

        // Set the A register with the calculated address
        a.operateOnRegister(integerToByteArray(address))
    }
}
