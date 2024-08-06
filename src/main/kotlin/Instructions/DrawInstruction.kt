package Instructions

import com.emulator.ScreenManager.screen
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class DrawInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R
    private var row: Byte = 0
    private var col: Byte = 0

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]
        row = nibbles[1]
        col = nibbles[2]

        val asciiValue = registerX.readRegister()[0].toInt()
        require(asciiValue in 0..0x7F) { "ASCII value in registerX must be between 0x00 and 0x7F." }

        screen.draw(asciiValue.toByte(), row, col)
    }
}
