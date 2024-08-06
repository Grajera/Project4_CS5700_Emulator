package Instructions

import com.emulator.DisplayInstance.display
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class DrawInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]

        val asciiValue = registerX.readRegister()[0].toInt()
        require(asciiValue in 0..0x7F) { "ASCII value in registerX must be between 0x00 and 0x7F." }

        display.addToScreenBuffer(asciiValue.toByte(), nibbles[1], nibbles[2])
    }
}
