package Instructions

import com.emulator.Utils.integerToByteArray
import Memory.Registry_Handlers.PRegisterInstance.p

class JumpInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var newLocationInMemory: ByteArray

    public override fun runTask() {
        val address = (nibbles[0].toInt() shl 8) or (nibbles[1].toInt() shl 4) or nibbles[2].toInt()
        newLocationInMemory = integerToByteArray(address)

        // Store the calculated address in the program counter
        p.operateOnRegister(newLocationInMemory)
    }

    public override fun updateProgramCounter() {
        // Program counter is set directly by the address calculated in the previous step
    }
}
