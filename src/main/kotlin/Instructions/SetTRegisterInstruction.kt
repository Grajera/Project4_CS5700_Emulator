package Instructions

import com.emulator.Clock
import com.emulator.mergeNibblesToByte
import Memory.Registry_Handlers.TRegisterManager.t

class SetTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Value to set in the T register
    private var value: Byte = 0

    // Processes the nibbles to extract the value for the T register
    public override fun processNibblesForInstruction() {
        // Combine high and low nibbles
        value = mergeNibblesToByte(nibbles[1], nibbles[0])
    }

    // Performs the operation of setting the T register
    public override fun performInstruction() {
        // Pause the timer
        Clock.pauseTimer()
        // Set the T register value
        t.operateOnRegister(byteArrayOf(value))
        // Resume the timer
        Clock.resumeTimer()
    }
}
