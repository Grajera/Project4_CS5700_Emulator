package Instructions

import Memory.Registry_Handlers.RRegisterManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConvertToBaseTenInstructionTest {

    @Test
    fun testProcessNibbles() {
        val nibbles = byteArrayOf(0x00, 0x01, 0x02) // Assuming registerX = 0, rY = 1
        val instruction = ConvertToBaseTenInstruction(nibbles)

        instruction.processNibblesForInstruction()

        // Verify that the correct register is set
        assertEquals(RRegisterManager.r[0], instruction.registerX)
    }

    @Test
    fun testPerformInstructionNormal() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0b00000011)) // 3 in binary

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertToBaseTenInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        // Expect decimal '3' in the output register
        assertEquals(0x03.toByte(), registerX.readRegister()[0])
    }

    @Test
    fun testPerformInstructionMaxValue() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0b11111111.toByte())) // 255 in binary

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertToBaseTenInstruction(nibbles)
        instruction.processNibblesForInstruction()
        // Expect decimal '255' in the output register so throw error.
        assertFailsWith<IllegalArgumentException> {
            instruction.performInstruction()
        }
    }

    @Test
    fun testPerformInstructionOutOfBounds() {
        val registerX = RRegisterManager.r[0]
        registerX.writeToRegister(byteArrayOf(0b100000000.toByte())) // 256 in binary (out of range)

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = ConvertToBaseTenInstruction(nibbles)
        instruction.processNibblesForInstruction()

        // Expect decimal '0' in the output register
        assertEquals(0x00.toByte(), registerX.readRegister()[0])
    }
}