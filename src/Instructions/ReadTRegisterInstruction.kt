package Instructions

import com.emulator.PauseTimerManager
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.TRegisterManager.t

class ReadTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to hold the result from the T register
    private lateinit var targetRegister: R

    // Processes the nibbles to identify the target register
    public override fun processNibbles() {
        val targetRegisterIndex = nibbles[0].toInt()
        // Retrieve the corresponding register
        targetRegister = r[targetRegisterIndex]
    }

    // Performs the operation of reading the T register
    public override fun performOperation() {
        // Pause operations
        PauseTimerManager.pause.set(true)

        // Read the value from the T register
        val tValue = t.read()[0]

        // Set the target register with the T value
        targetRegister.operate(byteArrayOf(tValue))

        // Resume operations
        PauseTimerManager.pause.set(false)
    }
}
