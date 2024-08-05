package Instructions

import Memory.Registry_Handlers.RRegisterManager
import kotlin.test.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class AddInstructionTest {
    @BeforeTest
    fun setup() {
        // Reset registers before each test
        RRegisterManager.r.forEach { it.writeToRegister(byteArrayOf(0x00)) }
    }

    @Test
    fun testProcessNibbles() {
        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = AddInstruction(nibbles)

        instruction.processNibblesForInstruction()

        assertEquals(RRegisterManager.r[0], instruction.registerX)
        assertEquals(RRegisterManager.r[1], instruction.registerY)
        assertEquals(RRegisterManager.r[2], instruction.registerZ)
    }

    @Test
    fun testPerformInstruction() {
        // Set up initial values
        RRegisterManager.r[0].writeToRegister(byteArrayOf(0x05)) // registerX
        RRegisterManager.r[1].writeToRegister(byteArrayOf(0x03)) // ry
        val rz = RRegisterManager.r[2]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = AddInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        assertEquals(0x08.toByte(), rz.readRegister()[0]) // Expect 5 + 3 = 8
    }

    @Test
    fun testPerformInstructionWithOverflow() {
        // Set up values that will overflow
        RRegisterManager.r[0].writeToRegister(byteArrayOf(0xFF.toByte())) // registerX (255)
        RRegisterManager.r[1].writeToRegister(byteArrayOf(0x01)) // ry (1)
        val rz = RRegisterManager.r[2]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = AddInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        assertEquals(0x00.toByte(), rz.readRegister()[0]) // Expect overflow (255 + 1) % 256 = 0
    }

    @Test
    fun testPerformInstructionWithZeroes() {
        // Test case with both registers as zero
        RRegisterManager.r[0].writeToRegister(byteArrayOf(0x00)) // registerX
        RRegisterManager.r[1].writeToRegister(byteArrayOf(0x00)) // ry
        val rz = RRegisterManager.r[2]

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = AddInstruction(nibbles)
        instruction.processNibblesForInstruction()

        instruction.performInstruction()

        assertEquals(0x00.toByte(), rz.readRegister()[0]) // Expect 0 + 0 = 0
    }
}
