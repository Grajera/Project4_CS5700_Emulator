package Instructions

import com.emulator.Clock
import com.emulator.mergeNibblesToByte
import Memory.Registry_Handlers.TRegisterManager.t

class SetTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Value to set in the T register
    private var value: Byte = 0

    // Performs the operation of setting the T register
    public override fun runTask() {
        // Combine high and low nibbles to form the value for the T register
        value = mergeNibblesToByte(nibbles[1], nibbles[2])

        Clock.pauseTimer() // Pause operations
        t.operateOnRegister(byteArrayOf(value)) // Set the T register value
        Clock.resumeTimer() // Resume operations
    }
}
