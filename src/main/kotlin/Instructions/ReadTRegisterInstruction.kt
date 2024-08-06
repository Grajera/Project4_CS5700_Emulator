package Instructions

import com.emulator.Clock
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r
import Memory.Registry_Handlers.TRegisterInstance.t

class ReadTRegisterInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var targetRegister: R

    public override fun runTask() {
        targetRegister = r[nibbles[0].toInt()]
        Clock.pauseTimer() // Pause operations

        val tValue = t.readRegister()[0]
        targetRegister.operateOnRegister(byteArrayOf(tValue))

        Clock.resumeTimer()
    }
}
