package Instructions

import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubtractionInstructionTest {

    @Test
    fun testSubtractionPositiveResult() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]
        val r1 = RRegisterManager.r[1]
        val r2 = RRegisterManager.r[2]

        // Set values in registers
        r0.writeToRegister(byteArrayOf(0x05)) // r0 = 5
        r1.writeToRegister(byteArrayOf(0x03)) // r1 = 3

        // Create the Subtraction instruction (r2 = r0 - r1)
        val instruction = SubtractInstruction(byteArrayOf(0x00, 0x01, 0x02))
        instruction.processNibblesForInstruction() // Set registerX, ry, rz based on nibbles

        // Execute the instruction
        instruction.performInstruction()

        // Verify that r2 now contains the result (5 - 3 = 2)
        assertEquals(0x02.toByte(), r2.readRegister()[0])
    }

    @Test
    fun testSubtractionNegativeResult() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]
        val r1 = RRegisterManager.r[1]
        val r2 = RRegisterManager.r[2]

        // Set values in registers
        r0.writeToRegister(byteArrayOf(0x02)) // r0 = 2
        r1.writeToRegister(byteArrayOf(0x05)) // r1 = 5

        // Create the Subtraction instruction (r2 = r0 - r1)
        val instruction = SubtractInstruction(byteArrayOf(0x00, 0x01, 0x02))
        instruction.processNibblesForInstruction() // Set registerX, ry, rz based on nibbles

        // Execute the instruction
        instruction.performInstruction()

        // Verify that r2 now contains the result (2 - 5 = -3, represented in two's complement)
        assertEquals((-0x03).toByte(), r2.readRegister()[0])
    }

    @Test
    fun testSubtractionWithZero() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]
        val r1 = RRegisterManager.r[1]
        val r2 = RRegisterManager.r[2]

        // Set values in registers
        r0.writeToRegister(byteArrayOf(0x00)) // r0 = 0
        r1.writeToRegister(byteArrayOf(0x05)) // r1 = 5

        // Create the Subtraction instruction (r2 = r0 - r1)
        val instruction = SubtractInstruction(byteArrayOf(0x00, 0x01, 0x02))
        instruction.processNibblesForInstruction() // Set registerX, ry, rz based on nibbles

        // Execute the instruction
        instruction.performInstruction()

        // Verify that r2 now contains the result (0 - 5 = -5, represented in two's complement)
        assertEquals((-0x05).toByte(), r2.readRegister()[0])
    }

    @Test
    fun testSubtractionWithNegativeResultInRegister() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]
        val r1 = RRegisterManager.r[1]
        val r2 = RRegisterManager.r[2]

        // Set a negative value in r1
        r0.writeToRegister(byteArrayOf(0x03)) // r0 = 3
        r1.writeToRegister(byteArrayOf((-0x02).toByte())) // r1 = -2

        // Create the Subtraction instruction (r2 = r0 - r1)
        val instruction = SubtractInstruction(byteArrayOf(0x00, 0x01, 0x02))
        instruction.processNibblesForInstruction() // Set registerX, ry, rz based on nibbles

        // Execute the instruction
        instruction.performInstruction()

        // Verify that r2 now contains the result (3 - (-2) = 3 + 2 = 5)
        assertEquals(0x05.toByte(), r2.readRegister()[0])
    }

    @Test
    fun testSubtractionWithInvalidRegisterIndex() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]

        // Set a value in r0
        r0.writeToRegister(byteArrayOf(0x05))

        // Create a Subtraction instruction with an invalid register index for ry
        val instruction = SubtractInstruction(byteArrayOf(0x00, 0xFF.toByte(), 0x02)) // Assuming 0xFF is invalid

        // Execute the instruction and expect an exception
        val exception = org.junit.jupiter.api.assertThrows<IndexOutOfBoundsException> {
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
        }
        assertEquals("Index -1 out of bounds for length 8", exception.message) // Adjust the expected message as necessary
    }
}