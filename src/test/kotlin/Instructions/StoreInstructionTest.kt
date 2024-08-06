import Instructions.StoreInstruction
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StoreInstructionTest {

    private lateinit var instruction: StoreInstruction
    private lateinit var registerX: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0xF0.toByte(), 0x0A.toByte()) // Example nibbles
        instruction = StoreInstruction(nibbles)
        registerX = r[nibbles[0].toInt()] // Initialize registerX
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()
        assertEquals(0xF0A.toByte(), registerX.readRegister()[0], "The register should store the combined byte value from the nibbles.")
    }
}
