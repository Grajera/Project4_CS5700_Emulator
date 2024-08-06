import Instructions.SetARegisterInstruction
import Memory.Registry_Handlers.ARegisterManager.a
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SetARegisterTest {

    private lateinit var instruction: SetARegisterInstruction

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x01, 0x02, 0x03) // Example nibbles
        instruction = SetARegisterInstruction(nibbles)
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()

        val expectedAddress = 0x01 // Construct the expected address
        assertEquals(expectedAddress.toByte(), a.readRegister()[0], "The A register should be set to the calculated address.")
    }
}
