package Instructions

import com.emulator.Clock
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

// Class representing an instruction to read input from the keyboard
class ReadKeyboardInstruction(
    nibbles: ByteArray // Nibbles to initialize the instruction
) : BasicInstruction(nibbles) {

    // Register to hold the value from keyboard input
    private lateinit var targetRegister: R

    // Processes the nibbles to identify the target register
    public override fun processNibblesForInstruction() {
        val targetRegisterIndex = nibbles[0].toInt()
        targetRegister = r[targetRegisterIndex] // Retrieve the corresponding register
    }

    // Performs the operation of reading from the keyboard
    public override fun performInstruction() {
        Clock.pauseTimer() // Pause operations

        println("Enter your input: ")
        // Read user input
        val input = readln().trim().uppercase()
        // Parse input to byte
        // Validate input and convert to byte
        val formattedInput = if (input.isEmpty() || !input.matches(Regex("^[0-9A-F]*$"))) {
            // Return 0 for invalid input
            0.toByte()
        } else {
            try {
                // Take at most 2 characters and convert to byte
                input.take(2).toInt(16).toByte()
            } catch (e: NumberFormatException) {
                println("Error: Failed to parse hexadecimal input. Defaulting to 0.")
                0.toByte() // Return 0 on parsing failure
            }
        }

        // Check for invalid input and provide feedback
        if (input.length > 2) {
            println("Input truncated to the first two hexadecimal digits.")
        }

        if (formattedInput.toInt() == 0 && input.isNotEmpty() && input != "00") {
            println("Invalid hexadecimal input. Defaulting to 0.")
        }

        // Set the target register with the parsed byte
        targetRegister.operateOnRegister(byteArrayOf(formattedInput))
        // Resume operations
        Clock.resumeTimer()
    }
}
