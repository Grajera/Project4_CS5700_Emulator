package Instructions

import com.emulator.PauseTimerManager
import com.emulator.combineNibblesToByte
import Memory.Registry_Handlers.TRegisterManager.t

class SetTRegisterInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    var value: Byte = 0

    public override fun processNibbles() {
        val highNibble = nibbles[0]
        val lowNibble = nibbles[1]
        value = combineNibblesToByte(highNibble, lowNibble)
    }

    public override fun performOperation() {
        PauseTimerManager.pauseTimer.set(true)
        t.operate(byteArrayOf(value))
        PauseTimerManager.pauseTimer.set(false)
    }
}