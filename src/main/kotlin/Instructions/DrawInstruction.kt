package Instructions

import com.emulator.ScreenManager.screen
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class DrawInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to hold the ASCII value
    lateinit var registerX: R

    // Row and column for drawing on the screen
    var row: Byte = 0
    var col: Byte = 0

    public override fun processNibblesForInstruction() {
        // Get the register index and assign the register
        val registerXIndex = nibbles[0].toInt()
        registerX = r[registerXIndex]

        // Get the row and column from nibbles
        row = nibbles[1]
        col = nibbles[2]
    }

    public override fun performInstruction() {
        // Read the ASCII value from the register
        val asciiValue = registerX.readRegister()[0].toInt()

        // Validate the ASCII value
        if (asciiValue < 0 || asciiValue > 0x7F) {
            throw IllegalArgumentException("ASCII value in registerX must be between 0x00 and 0x7F, but was: $asciiValue.")
        }

        // Draw the character on the screen at the specified position
        screen.draw(asciiValue.toByte(), row, col)
    }
}
