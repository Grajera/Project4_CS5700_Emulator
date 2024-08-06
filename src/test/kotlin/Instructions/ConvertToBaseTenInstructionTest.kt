package Instructions
import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertFailsWith

class ConvertToBaseTenInstructionTest {

    private lateinit var instruction: ConvertToBaseTenInstruction

    @BeforeEach
    fun setUp() {
        // Initialize the instruction with sample nibbles (assuming it points to the correct registers)
        val nibbles = byteArrayOf(0, 1, 0) // Example: Using registers 0 for input and 1 for output
        instruction = ConvertToBaseTenInstruction(nibbles)

        // Set initial values in registers for testing
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(0b1010)) // Binary 1010 (10 in decimal)
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(0)) // Initialize output register
    }

    @Test
    fun testConvertToBaseTen() {
        instruction.runTask() // Perform the conversion

        // Read the result from the output register
        val result = RRegisterManager.r[0].readRegister()[0].toInt()

        // Check that the result is correct (10 in base ten)
        assertEquals(10, result)
    }

    @Test
    fun testConvertZero() {
        // Set the input register to 0
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(0))

        instruction.runTask() // Perform the conversion

        // Read the result from the output register
        val result = RRegisterManager.r[1].readRegister()[0].toInt()

        // Check that the result is 0
        assertEquals(0, result)
    }

    @Test
    fun testConvertNegative() {
        // Set the input register to a negative number (represented in two's complement)
        RRegisterManager.r[0].operateOnRegister(byteArrayOf((-10).toByte()))
        assertFailsWith<IllegalArgumentException> {
            instruction.runTask()
        }
    }

    @Test
    fun testConvertOverflow() {
        // Set the input register to the maximum byte value
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(127)) // 127 in decimal

        instruction.runTask() // Perform the conversion

        // Read the result from the output register
        val result = RRegisterManager.r[0].readRegister()[0].toInt()

        // Check that the result is correct (127 in base ten)
        assertEquals(127, result)
    }
}
