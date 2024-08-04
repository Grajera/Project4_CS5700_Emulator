package Instructions

import com.emulator.intToByteArray
import Memory.Registry_Handlers.PRegisterManager.p

class JumpInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Byte array to hold the calculated address
    lateinit var addressBytes: ByteArray

    public override fun processNibbles() {
        val address = (nibbles[0].toInt() shl 8) or (nibbles[1].toInt() shl 4) or nibbles[2].toInt()
        // Convert the address to a byte array
        addressBytes = intToByteArray(address)
    }

    public override fun performOperation() {
        // Store the calculated address in the program counter
        p.operate(addressBytes)
    }

    public override fun incrementProgramCounter() {
        // Program counter is not incremented after this instruction
        // It is set directly by the address calculated in the previous step
    }
}