import Instructions.SkipIfEqualInstruction
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.R
import com.emulator.byteArrayToInt
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SkipIfEqualInstructionTest {

    private lateinit var instruction: SkipIfEqualInstruction
    private lateinit var registerX: R
    private lateinit var registerY: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x1, 0x2) // Example nibbles
        instruction = SkipIfEqualInstruction(nibbles)

        registerX = r[0]
        registerY = r[1]
        registerX.operateOnRegister(byteArrayOf(0x5)) // Set value for X
        registerY.operateOnRegister(byteArrayOf(0x5)) // Set value for Y
    }

    @Test
    fun testPerformInstruction_Skip() {
        instruction.runTask()
        instruction.incrementProgramCounter()

        val currentPBytes = p.readRegister()
        assertEquals(currentPBytes[0] + 6, byteArrayToInt(p.readRegister()), "The program counter should be incremented by 4 when registers are equal.")
    }

    @Test
    fun testPerformInstruction_NoSkip() {
        registerY.operateOnRegister(byteArrayOf(0x3)) // Change Y value
        instruction.runTask()
        instruction.incrementProgramCounter()

        val currentPBytes = p.readRegister()
        assertEquals(currentPBytes[0] + 2, byteArrayToInt(p.readRegister()), "The program counter should be incremented by 2 when registers are not equal.")
    }
}
