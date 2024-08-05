package Instructions

import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.RRegisterManager
import Memory.Registry_Handlers.RRegisterManager.r
import com.emulator.byteArrayToInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SkipIfEqualInstructionTest {

    @Test
    fun testPerformOperation() {
        val nibbles = byteArrayOf(0x00, 0x01, 0x00)
        val instruction = SkipIfEqualInstruction(nibbles)

        instruction.processNibblesForInstruction()

        r[0].writeToRegister(byteArrayOf(0x42))
        r[1].writeToRegister(byteArrayOf(0x42))
        val previousPointerLocation = p.readRegister()
        instruction.performInstruction()

        r[0].writeToRegister(byteArrayOf(0x42))
        r[1].writeToRegister(byteArrayOf(0x43))

        instruction.performInstruction()

        assertEquals(p.readRegister()[0], previousPointerLocation[0])
    }
}