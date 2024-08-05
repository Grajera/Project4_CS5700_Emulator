package Instructions

import Memory.Registry_Handlers.TRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class SetTRegisterTest {

    @Test
    fun testSetTRegisterValue() {
        // Set a value to be written to the T register
        val valueToSet = 0x02.toByte()

        // Create the instruction to set the T register
        val instruction = SetTRegisterInstruction(byteArrayOf(valueToSet, 0x00, 0x00))

        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that the T register has been set correctly
        val tValue = TRegisterManager.t.readRegister()[0]
        assertEquals(valueToSet, tValue)
    }

    @Test
    fun testSetTRegisterOutOfBounds() {
        // Attempt to set a value that exceeds the byte range
        val outOfBoundsValue = 0x100.toByte() // This would wrap around to 0 in a byte

        // Create the instruction to set the T register
        val instruction = SetTRegisterInstruction(byteArrayOf(outOfBoundsValue, 0x00, 0x00))

        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that the T register has wrapped around correctly
        val tValue = TRegisterManager.t.readRegister()[0]
        assertEquals(0x00.toByte(), tValue) // Expecting it to wrap around to 0
    }

    @Test
    fun testSetTRegisterMultipleOperations() {
        // Create a list of values to set in the T register
        val valuesToSet = listOf(0x01.toByte(), 0x02.toByte(), 0x03.toByte())

        // Set and check values sequentially
        for (value in valuesToSet) {
            val instruction = SetTRegisterInstruction(byteArrayOf(value, 0x00, 0x00))
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
            assertEquals(value, TRegisterManager.t.readRegister()[0])
        }
    }

    @Test
    fun testSetTRegisterWithInvalidInput() {
        // Attempt to create an instruction with an invalid byte array (empty)
        // Expecting an exception due to invalid input
        val exception = assertThrows(IllegalArgumentException::class.java) {
            val instruction = SetTRegisterInstruction(byteArrayOf())
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
        }

        assertEquals("Nibbles array must contain exactly 3 elements.", exception.message)
    }
}