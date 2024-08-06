import Instructions.JumpInstruction
import Memory.Registry_Handlers.PRegisterManager.p
import com.emulator.byteArrayToInt
import com.emulator.intToByteArray
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class JumpInstructionTest {

    private lateinit var instruction: JumpInstruction

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x1, 0x2) // Example nibbles
        instruction = JumpInstruction(nibbles)
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()

        val expectedPC = intToByteArray(0x0012) // Expected address calculated from nibbles

        assertContentEquals(expectedPC, p.readRegister(), "Program counter should be set correctly.")
    }

    @Test
    fun testIncrementProgramCounter() {
        instruction.incrementProgramCounter() //should do nothing.
        val initialPC = byteArrayToInt(p.readRegister())
        instruction.incrementProgramCounter() //should do nothing.
        assertEquals(initialPC, byteArrayToInt(p.readRegister()))
    }
}