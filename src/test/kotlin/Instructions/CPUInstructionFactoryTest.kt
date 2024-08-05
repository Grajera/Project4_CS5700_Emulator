package Instructions

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CPUInstructionFactoryTest {

    private val instructionFactory = CPUInstructionsFactory()

    @Test
    fun testCreateAddInstruction() {
        val nibble0 = 0x1 // Assuming the opcode for AddInstruction is 1
        val nibble1 = 0x1 // Registers for registerX
        val nibble2 = 0x2 // Registers for ry
        val nibble3 = 0x3 // Registers for rz

        val instruction = instructionFactory.makeInstructions(nibble0.toByte(), nibble1.toByte(), nibble2.toByte(), nibble3.toByte())

        assertTrue(instruction is AddInstruction, "Expected an AddInstruction instance")
    }

    @Test
    fun testCreateDrawInstruction() {
        val nibble0 = 0xF // The opcode for DrawInstruction is 15
        val nibble1 = 0x1 // Register for byte to draw
        val nibble2 = 0x2 // Row
        val nibble3 = 0x3 // Column

        val instruction = instructionFactory.makeInstructions(nibble0.toByte(), nibble1.toByte(), nibble2.toByte(), nibble3.toByte())

        assertTrue(instruction is DrawInstruction, "Expected a DrawInstruction instance")
    }

    @Test
    fun testCreateConvertByteToAsciiInstruction() {
        val nibble0 = 0xE // The opcode for ConvertByteToAsciiInstruction is 14
        val nibble1 = 0x1 // Register for input byte
        val nibble2 = 0x2 // Register for output character
        val nibble3 = 0x3 // Not used

        val instruction = instructionFactory.makeInstructions(nibble0.toByte(), nibble1.toByte(), nibble2.toByte(), nibble3.toByte())

        assertTrue(instruction is ConvertByteToAsciiInstruction, "Expected a ConvertByteToAsciiInstruction instance")
    }

    @Test
    fun testCreateConvertToBaseTenInstruction() {
        val nibble0 = 0xD // The opcode for ConvertToBaseTenInstruction is 13
        val nibble1 = 0x1 // Register for input byte
        val nibble2 = 0x2 // Register for output
        val nibble3 = 0x3 // Not used

        val instruction = instructionFactory.makeInstructions(nibble0.toByte(), nibble1.toByte(), nibble2.toByte(), nibble3.toByte())

        assertTrue(instruction is ConvertToBaseTenInstruction, "Expected a ConvertToBaseTenInstruction instance")
    }

    @Test
    fun testCreateInvalidInstruction() {
        val nibble0 = 0xFF // Invalid opcode
        val nibble1 = 0x1
        val nibble2 = 0x2
        val nibble3 = 0x3
        // Invalid opcode should throw an error.
        assertFailsWith<IllegalArgumentException> {
            instructionFactory.makeInstructions(nibble0.toByte(), nibble1.toByte(), nibble2.toByte(), nibble3.toByte())
        }
    }
}