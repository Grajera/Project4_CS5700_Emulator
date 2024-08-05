package Instructions

import com.emulator.Clock
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.TRegisterManager.t

class ReadTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Register to hold the result from the T register
    private lateinit var targetRegister: R

    // Processes the nibbles to identify the target register
    public override fun processNibblesForInstruction() {
        val targetRegisterIndex = nibbles[0].toInt()
        // Retrieve the corresponding register
        targetRegister = r[targetRegisterIndex]
    }

    // Performs the operation of reading the T register
    public override fun performInstruction() {
        // Pause operations
        Clock.pauseTimer()

        // Read the value from the T register
        val tValue = t.readRegister()[0]

        // Set the target register with the T value
        targetRegister.operateOnRegister(byteArrayOf(tValue))

        // Resume operations
        Clock.resumeTimer()
    }
}
