import Instructions.ReadKeyboardInstruction
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.test.assertEquals

class ReadKeyboardInstructionTest {

    private lateinit var instruction: ReadKeyboardInstruction
    private lateinit var testRegister: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x0, 0x0) // Target register index
        instruction = ReadKeyboardInstruction(nibbles)
        testRegister = r[nibbles[0].toInt()]
    }

    @Test
    fun testPerformInstruction_ValidInput() {
        // Simulate user input
        val input: InputStream = ByteArrayInputStream("1A\n".toByteArray())
        System.setIn(input)

        instruction.runTask()

        assertEquals(0x1A.toByte(), testRegister.readRegister()[0], "The target register should contain the parsed byte from input.")
    }

    @Test
    fun testPerformInstruction_InvalidInput() {
        // Simulate user input
        val input: InputStream = ByteArrayInputStream("XYZ\n".toByteArray())
        System.setIn(input)

        instruction.runTask()

        assertEquals(0x0.toByte(), testRegister.readRegister()[0], "The target register should default to 0 for invalid input.")
    }
}
