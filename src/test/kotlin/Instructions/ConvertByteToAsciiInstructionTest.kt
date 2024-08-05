package Instructions

import Memory.Registry_Handlers.RRegisterManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConvertByteToAsciiInstructionTest {
    @Test
    fun testProcessNibbles() {
        val nibbles = byteArrayOf(0x00, 0x01, 0x02) // registerX = 0, rY = 1
        val instruction = ConvertByteToAsciiInstruction(nibbles)

        instruction.processNibblesForInstruction()

        // Verify that the correct registers are set
        assertEquals(RRegisterManager.r[0], instruction.sourceRegister)
        assertEquals(RRegisterManager.r[1], instruction.targetRegister)
    }

    @Test
    fun testPerformInstructionNormal() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0x05)) // 5 in register
        val ry = RRegisterManager.r[1]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertByteToAsciiInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        // Expect ASCII '5' in the output register (0x35)
        assertEquals(0x35.toByte(), ry.readRegister()[0])
    }

    @Test
    fun testPerformInstructionNormalForUpperBound() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0x0A)) // 10 in register
        val ry = RRegisterManager.r[1]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertByteToAsciiInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        // Expect ASCII 'A' in the output register (0x41)
        assertEquals(0x41.toByte(), ry.readRegister()[0])
    }

    @Test
    fun testPerformInstructionOutOfBounds() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0x10)) // 16 in register (out of range)
        val ry = RRegisterManager.r[1]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertByteToAsciiInstruction(nibbles)
        instruction.processNibblesForInstruction()

        // Expect an exception when trying to convert an out of range value
        assertFailsWith <IllegalArgumentException> {
            instruction.performInstruction()
        }
    }
}