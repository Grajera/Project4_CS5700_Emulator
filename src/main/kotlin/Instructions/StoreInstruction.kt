package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r
import com.emulator.Utils.mergeNibblesToByte

class StoreInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    private var byte: Byte = 0

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]

        // Extract the byte from nibbles
        byte = mergeNibblesToByte(nibbles[1], nibbles[2])

        // Store the byte in the register
        registerX.operateOnRegister(byteArrayOf(byte))
    }
}
