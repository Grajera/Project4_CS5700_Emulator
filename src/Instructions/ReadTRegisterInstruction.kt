package Instructions

import com.emulator.PauseTimerManager
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.TRegisterManager.t

class ReadTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
    }

    public override fun performOperation() {
        PauseTimerManager.pauseTimer.set(true)

        val tValue = t.read()[0]

        rx.operate(byteArrayOf(tValue))

        PauseTimerManager.pauseTimer.set(false)
    }
}