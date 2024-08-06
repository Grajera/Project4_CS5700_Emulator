import Instructions.ReadTRegisterInstruction
import Memory.Registry_Handlers.TRegisterManager.t
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReadTRegisterInstructionTest {

    private lateinit var instruction: ReadTRegisterInstruction
    private lateinit var testRegister: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x0, 0x0) // Target register index
        instruction = ReadTRegisterInstruction(nibbles)
        testRegister = r[nibbles[0].toInt()]
        t.operateOnRegister(byteArrayOf(0xB)) // Set value for T register
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()

        assertEquals(0xB.toByte(), testRegister.readRegister()[0], "The target register should contain the value from the T register.")
    }
}
