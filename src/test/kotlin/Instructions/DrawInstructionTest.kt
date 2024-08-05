package Instructions.Instructions

import Instructions.DrawInstruction
import com.emulator.ScreenManager
import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class DrawInstructionTest {

    @Test
    fun testPerformInstructionNormal() {
        val byteToDraw = 0x0F.toByte() // Example byte to draw (could represent a character)
        val row = 0.toByte()
        val col = 0.toByte()

        // Set the registers for the instruction
        RRegisterManager.r[0].writeToRegister(byteArrayOf(byteToDraw)) // Value to draw
        RRegisterManager.r[1].writeToRegister(byteArrayOf(row)) // Row
        RRegisterManager.r[2].writeToRegister(byteArrayOf(col)) // Column

        val nibbles = byteArrayOf(0x00, 0x01, 0x02) // Assuming nibbles correspond to registerX, rY, rZ
        val instruction = DrawInstruction(nibbles)

        instruction.processNibblesForInstruction()
        instruction.performInstruction()

        // Verify that the buffer has been updated correctly
        assertEquals(byteToDraw, ScreenManager.screen.buffer[10]) // Check the value at (0,0)
    }

    @Test
    fun testPerformInstructionOverwrite() {
        val initialByte = 0x01.toByte()
        val byteToDraw = 0x7F.toByte() // New byte to draw
        val row = 0.toByte()
        val col = 0.toByte()

        // Set the initial value in the buffer
        ScreenManager.screen.draw(initialByte, row, col)

        // Set registers for the new drawing
        RRegisterManager.r[0].writeToRegister(byteArrayOf(byteToDraw)) // Value to draw
        RRegisterManager.r[1].writeToRegister(byteArrayOf(row)) // Row
        RRegisterManager.r[2].writeToRegister(byteArrayOf(col)) // Column

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = DrawInstruction(nibbles)

        instruction.processNibblesForInstruction()
        instruction.performInstruction()

        // Verify that the buffer has been overwritten correctly
        assertEquals(byteToDraw, ScreenManager.screen.buffer[10]) // Check the value at (0,0)
    }

    @Test
    fun testPerformInstructionOutOfBounds() {
        val byteToDraw = 0xFF.toByte()
        val row = 8.toByte() // Out of bounds row
        val col = 8.toByte() // Out of bounds column

        // Set registers for the drawing
        RRegisterManager.r[0].writeToRegister(byteArrayOf(byteToDraw)) // Value to draw
        RRegisterManager.r[1].writeToRegister(byteArrayOf(row)) // Row
        RRegisterManager.r[2].writeToRegister(byteArrayOf(col)) // Column

        val nibbles = byteArrayOf(0x00, 0x01, 0x02)
        val instruction = DrawInstruction(nibbles)

        instruction.processNibblesForInstruction()

        // Expect an exception when trying to draw out of bounds
        assertThrows<IllegalArgumentException> {
            instruction.performInstruction()
        }
    }
}
