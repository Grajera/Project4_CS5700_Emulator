package Instructions

import com.emulator.ScreenManager.screen
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class DrawInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to hold the ASCII value
    lateinit var rx: R

    // Row and column for drawing on the screen
    var row: Byte = 0
    var col: Byte = 0

    public override fun processNibbles() {
        // Get the register index and assign the register
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]

        // Get the row and column from nibbles
        row = nibbles[1]
        col = nibbles[2]
    }

    public override fun performOperation() {
        // Read the ASCII value from the register
        val asciiValue = rx.read()[0].toInt()

        // Validate the ASCII value
        if (asciiValue < 0 || asciiValue > 0x7F) {
            throw IllegalArgumentException("ASCII value in rX must be between 0x00 and 0x7F, but was: $asciiValue.")
        }

        // Draw the character on the screen at the specified position
        screen.draw(asciiValue.toByte(), row, col)
    }
}
