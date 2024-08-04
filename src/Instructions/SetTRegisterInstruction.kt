package Instructions

import com.emulator.PauseTimerManager
import com.emulator.mergeNibblesToByte
import Memory.Registry_Handlers.TRegisterManager.t

class SetTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Value to set in the T register
    private var value: Byte = 0

    // Processes the nibbles to extract the value for the T register
    public override fun processNibbles() {
        // Combine high and low nibbles
        value = mergeNibblesToByte(nibbles[0], nibbles[1])
    }

    // Performs the operation of setting the T register
    public override fun performOperation() {
        // Pause the timer
        PauseTimerManager.pause.set(true)
        // Set the T register value
        t.operate(byteArrayOf(value))
        // Resume the timer
        PauseTimerManager.pause.set(false)
    }
}
