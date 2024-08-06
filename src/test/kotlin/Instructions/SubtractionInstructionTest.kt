import Instructions.SubtractInstruction
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SubtractionInstructionTest {

    private lateinit var instruction: SubtractInstruction
    private lateinit var registerX: R
    private lateinit var registerY: R
    private lateinit var registerZ: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x1, 0x2) // Register indices for X, Y, Z
        instruction = SubtractInstruction(nibbles)
        registerX = r[nibbles[0].toInt()]
        registerY = r[nibbles[1].toInt()]
        registerZ = r[nibbles[2].toInt()]

        registerX.operateOnRegister(byteArrayOf(5)) // Set X register value
        registerY.operateOnRegister(byteArrayOf(3)) // Set Y register value
    }

    @Test
    fun testPerformInstruction() {
        instruction.runTask()
        assertEquals(2.toByte(), registerZ.readRegister()[0], "The result register should contain the result of X - Y.")
    }
}
