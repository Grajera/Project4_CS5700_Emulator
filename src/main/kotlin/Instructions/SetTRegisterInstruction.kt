package Instructions

import com.emulator.Clock
import com.emulator.Utils.mergeNibblesToByte
import Memory.Registry_Handlers.TRegisterInstance.t

class SetTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    // Value to set in the T register
    private var tRegisterValue: Byte = 0

    // Performs the operation of setting the T register
    public override fun runTask() {
        // Combine high and low nibbles to form the value for the T register
        tRegisterValue = mergeNibblesToByte(nibbles[1], nibbles[2])

        Clock.pauseTimer() // Pause operations
        t.operateOnRegister(byteArrayOf(tRegisterValue)) // Set the T register value
        Clock.resumeTimer() // Resume operations
    }
}
