package Instructions

import Memory.Registry_Handlers.ARegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class SetARegisterTest {

    @Test
    fun testSetARegisterValue() {
        // Set a value to be written to the A register
        val valueToSet = 0x42.toByte()

        // Create the instruction to set the A register
        val instruction = SetARegisterInstruction(byteArrayOf(valueToSet, 0x00, 0x00))

        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that the A register has been set correctly
        val aValue = ARegisterManager.a.readRegister()[0]
        assertEquals(valueToSet, aValue)
    }

    @Test
    fun testSetARegisterOutOfBounds() {
        // Attempt to set a value that exceeds the byte range
        val outOfBoundsValue = 0x100.toByte() // This would wrap around to 0 in a byte

        // Create the instruction to set the A register
        val instruction = SetARegisterInstruction(byteArrayOf(outOfBoundsValue, 0x00, 0x00))

        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that the A register has wrapped around correctly
        val aValue = ARegisterManager.a.readRegister()[0]
        assertEquals(0x00.toByte(), aValue) // Expecting it to wrap around to 0
    }

    @Test
    fun testSetARegisterMultipleOperations() {
        // Create a list of values to set in the A register
        val valuesToSet = listOf(0x01.toByte(), 0x02.toByte(), 0x03.toByte())

        // Set and check values sequentially
        for (value in valuesToSet) {
            val instruction = SetARegisterInstruction(byteArrayOf(value, 0x00, 0x00))
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
            assertEquals(value, ARegisterManager.a.readRegister()[0])
        }
    }

    @Test
    fun testSetARegisterWithInvalidInput() {
        // Attempt to create an instruction with an invalid byte array (empty)
        // Expecting an exception due to invalid input
        val exception = assertThrows(IllegalArgumentException::class.java) {
            val instruction = SetARegisterInstruction(byteArrayOf())
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
        }

        assertEquals("Nibbles array must contain exactly 3 elements.", exception.message)
    }
}