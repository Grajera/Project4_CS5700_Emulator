package Instructions

import com.emulator.PauseTimerManager
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class ReadKeyboardInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
    }

    public override fun performOperation() {
        PauseTimerManager.pauseTimer.set(true)

        println("Enter up to 2 hexadecimal digits (0-F): ")
        val input = readln().trim().uppercase()

        val byte = parseHexInput(input)

        rx.operate(byteArrayOf(byte))

        PauseTimerManager.pauseTimer.set(false)
    }

    private fun parseHexInput(input: String): Byte {
        if (input.isEmpty() || !input.matches(Regex("^[0-9A-F]*$"))) {
            return 0
        }

        return try {
            val hexString = input.take(2)
            hexString.toInt(16).toByte()
        } catch (e: NumberFormatException) {
            0
        }
    }
}