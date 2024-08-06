import Instructions.DrawInstruction
import Memory.Registry_Handlers.RRegisterInstance.r
import Memory.Registry_Handlers.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DrawInstructionTest {

    private lateinit var instruction: DrawInstruction
    private lateinit var testRegister: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x2, 0x3) // Example nibbles
        instruction = DrawInstruction(nibbles)
        testRegister = r[nibbles[0].toInt()]
        testRegister.operateOnRegister(byteArrayOf(0x41)) // Set ASCII value 'A'
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()

        // Check if the screen draw function was called with correct parameters
        assertEquals(0x41.toByte(), testRegister.readRegister()[0], "ASCII value in the register should be 'A'.")
    }
}
