package Instructions

import com.emulator.ScreenManager.screen
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class DrawInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R
    var row: Byte = 0
    var col: Byte = 0

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
        row = nibbles[1]
        col = nibbles[2]
    }

    public override fun performOperation() {
        val asciiValue = rx.read()[0].toInt()

        if (asciiValue > 0x7F) {
            throw IllegalArgumentException("ASCII value in rX is greater than 0x7F.")
        }

        screen.draw(asciiValue.toByte(), row, col)
    }
}