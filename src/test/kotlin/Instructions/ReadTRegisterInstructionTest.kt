package Instructions

import Memory.Registry_Handlers.RRegisterManager
import Memory.Registry_Handlers.TRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class ReadTRegisterInstructionTest {

    @Test
    fun testPerformInstruction() {
        // Set a value in the T register
        val tValue = 0x42.toByte()
        TRegisterManager.t.operateOnRegister(byteArrayOf(tValue))

        // Set up the instruction to read from T and store it in R0
        val instruction = ReadTRegisterInstruction(byteArrayOf(0x00, 0x00, 0x00)) // Assuming 0x00 refers to R0
        // Process nibbles
        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that the value in R0 matches the value in T
        val r0Value = RRegisterManager.r[0].readRegister()[0]
        assertEquals(tValue, r0Value)
    }

    @Test
    fun testReadTWhenZero() {
        // Set T register to zero
        TRegisterManager.t.operateOnRegister(byteArrayOf(0x00))

        // Set up the instruction to read from T and store it in R0
        val instruction = ReadTRegisterInstruction(byteArrayOf(0x00, 0x00, 0x00)) // Assuming 0x00 refers to R0

        // Process nibbles
        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that R0 is also zero
        val r0Value = RRegisterManager.r[0].readRegister()[0]
        assertEquals(0x00.toByte(), r0Value)
    }

    @Test
    fun testInvalidRegisterIndex() {
        // Set a value in the T register
        val tValue = 0x42.toByte()
        TRegisterManager.t.operateOnRegister(byteArrayOf(tValue))

        // Set up the instruction to read from T and store it in an invalid R register (e.g., R10)
        val instruction = ReadTRegisterInstruction(byteArrayOf(0x0A, 0x00, 0x00)) // Assuming R10 is out of bounds

        // Perform the operation and check for an exception
        val exception = assertFailsWith<IndexOutOfBoundsException> {
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
        }

        assertEquals("Index 10 out of bounds for length 8", exception.message) // Update this message according to your implementation
    }
}