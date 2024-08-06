import Instructions.SetTRegisterInstruction
import Memory.Registry_Handlers.TRegisterManager.t
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SetTRegisterTest {

    private lateinit var instruction: SetTRegisterInstruction

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x00, 0x0F, 0xA) // Example nibbles (high and low)
        instruction = SetTRegisterInstruction(nibbles)
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()

        val expectedValue = 0xFA.toByte() // Combine the nibbles to form the expected value
        assertEquals(expectedValue, t.readRegister()[0], "The T register should be set to the combined value from nibbles.")
    }
}
