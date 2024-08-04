package Instructions

import com.emulator.PauseTimerManager
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

// Class representing an instruction to read input from the keyboard
class ReadKeyboardInstruction(
    nibbles: ByteArray // Nibbles to initialize the instruction
) : BasicInstruction(nibbles) {

    // Register to hold the value from keyboard input
    private lateinit var targetRegister: R

    // Processes the nibbles to identify the target register
    public override fun processNibbles() {
        val targetRegisterIndex = nibbles[0].toInt()
        targetRegister = r[targetRegisterIndex] // Retrieve the corresponding register
    }

    // Performs the operation of reading from the keyboard
    public override fun performOperation() {
        PauseTimerManager.pause.set(true) // Pause operations

        println("Enter up to 2 hexadecimal digits (0-F): ")
        // Read user input
        val input = readln().trim().uppercase()
        // Parse input to byte
        val byte = parseHexInput(input)

        // Check for invalid input and provide feedback
        if (input.length > 2) {
            println("Input truncated to the first two hexadecimal digits.")
        }

        if (byte.toInt() == 0 && input.isNotEmpty() && input != "00") {
            println("Invalid hexadecimal input. Defaulting to 0.")
        }

        // Set the target register with the parsed byte
        targetRegister.operate(byteArrayOf(byte))
        // Resume operations
        PauseTimerManager.pause.set(false)
    }

    private fun parseHexInput(input: String): Byte {
        if (input.isEmpty() || !input.matches(Regex("^[0-9A-F]*$"))) {
            // Return 0 for invalid input
            return 0
        }

        return try {
            // Take at most 2 characters
            val hexString = input.take(2)
            // Convert to byte
            hexString.toInt(16).toByte()
        } catch (e: NumberFormatException) {
            println("Error: Failed to parse hexadecimal input. Defaulting to 0.")
            // Return 0 on parsing failure
            return 0
        }
    }
}
