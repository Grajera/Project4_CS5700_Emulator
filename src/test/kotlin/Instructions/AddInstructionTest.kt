package Instructions

import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AddInstructionTest {

    private lateinit var instruction: AddInstruction

    @BeforeEach
    fun setUp() {
        // Initialize the instruction with sample nibbles (assuming it points to the correct registers)
        val nibbles = byteArrayOf(0, 1, 2) // Example: Using registers 0, 1 for X and Y, and 2 for result
        instruction = AddInstruction(nibbles)

        // Set initial values in registers for testing
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(10)) // Register X
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(20)) // Register Y
    }

    @Test
    fun testAddRegisters() {
        instruction.runTask() // Perform the addition

        // Read the result from the result register
        val result = RRegisterManager.r[2].readRegister()[0].toInt()

        // Check that the result is the sum of the two registers
        assertEquals(30, result) // 10 + 20 = 30
    }

    @Test
    fun testAddWithNegativeResult() {
        // Set values to produce a negative result
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(10)) // Register X
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(-15)) // Register Y

        instruction.runTask() // Perform the addition

        // Read the result from the result register
        val result = RRegisterManager.r[2].readRegister()[0].toInt()

        // Check that the result is correctly stored
        assertEquals(-5, result) // 10 + (-15) = -5
    }

    @Test
    fun testAddWithZero() {
        // Add zero to a number
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(10)) // Register X
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(0)) // Register Y

        instruction.runTask() // Perform the addition

        // Read the result from the result register
        val result = RRegisterManager.r[2].readRegister()[0].toInt()

        // Check that the result is unchanged
        assertEquals(10, result) // 10 + 0 = 10
    }
}
