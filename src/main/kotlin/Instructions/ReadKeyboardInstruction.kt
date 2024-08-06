package Instructions

import com.emulator.Clock
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class ReadKeyboardInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var targetRegister: R

    public override fun runTask() {
        targetRegister = r[nibbles[0].toInt()]
        Clock.pauseTimer() // Pause operations

        println("Enter your input: ")
        val input = readln().trim().uppercase()
        val formattedInput = validateAndParseInput(input)

        targetRegister.operateOnRegister(byteArrayOf(formattedInput))
        Clock.resumeTimer()
    }

    private fun validateAndParseInput(input: String): Byte {
        if (input.isEmpty() || !input.matches(Regex("^[0-9A-F]*$"))) {
            println("Invalid input. Defaulting to 0.")
            return 0
        }

        return try {
            input.take(2).toInt(16).toByte()
        } catch (e: NumberFormatException) {
            println("Error: Failed to parse hexadecimal input. Defaulting to 0.")
            0 // Return 0 on parsing failure
        }
    }
}
