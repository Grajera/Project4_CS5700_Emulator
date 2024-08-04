package Instructions

import com.emulator.TimerManager
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
        value = mergeNibblesToByte(nibbles[0], nibbles[1])
    }

    // Performs the operation of setting the T register
    public override fun performInstruction() {
        // Pause the timer
        TimerManager.pause.set(true)
        // Set the T register value
        t.operateOnRegister(byteArrayOf(value))
        // Resume the timer
        TimerManager.pause.set(false)
    }
}
