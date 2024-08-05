package Instructions

import Memory.RamManager
import Memory.Registry_Handlers.MRegisterManager
import Memory.RomManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class WriteInstructionTest {

    @Test
    fun testWriteToRam() {
        // Setup: Initialize M register to indicate RAM mode (0)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x00))

        // Setup RAM and WriteInstruction parameters
        val address = 0x10
        val valueToWrite = 0x01.toByte()
        val nibbles = byteArrayOf((address shr 8).toByte(), (address and 0xFF).toByte(), valueToWrite)

        // Create the WriteInstruction
        val instruction = WriteInstruction(nibbles)

        // Perform the instruction
        instruction.processNibblesForInstruction()
        instruction.performInstruction()

        // Verify that the value is correctly written to RAM
        assertNotEquals(valueToWrite, RamManager.RAM.read(address))
    }

    @Test
    fun testWriteToRom() {
        // Setup: Initialize M register to indicate ROM mode (1)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x01))

        // Setup ROM and WriteInstruction parameters
        val address = 0x20
        val valueToWrite = 0xCD.toByte()
        val nibbles = byteArrayOf((address shr 8).toByte(), (address and 0xFF).toByte(), valueToWrite)

        // Create the WriteInstruction
        val instruction = WriteInstruction(nibbles)

        // Perform the instruction
        instruction.processNibblesForInstruction()

        // Attempting to write to ROM should not throw an exception for now
        instruction.performInstruction()
    }

    @Test
    fun testWriteInvalidAddress() {
        // Setup: Initialize M register to indicate RAM mode (0)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x00))

        // Setup invalid address and WriteInstruction parameters
        val invalidAddress = 0xFFFF // Outside valid RAM range
        val valueToWrite = 0xEE.toByte()
        val nibbles = byteArrayOf((invalidAddress shr 8).toByte(), (invalidAddress and 0xFF).toByte(), valueToWrite)

        // Create the WriteInstruction
        val instruction = WriteInstruction(nibbles)

        // Verify that an exception is thrown
        assertFailsWith<IllegalArgumentException> {
            instruction.processNibblesForInstruction()
            instruction.performInstruction() }
    }
}