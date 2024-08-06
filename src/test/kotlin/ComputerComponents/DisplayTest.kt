package com.emulator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class DisplayTest {
    lateinit var display: Display

    @BeforeTest
    fun setUp() {
        // Initialize the Screen instance
        display = Display()
    }

    @Test
    fun testAddToScreenBufferValidPosition() {
        val byteToDraw = 0xFF.toByte() // Byte to draw (example value)
        val row = 1.toByte() // Row index
        val col = 2.toByte() // Column index

        // Draw the byte at the specified position
        display.addToScreenBuffer(byteToDraw, row, col)

        // Read the value from the screen buffer at the specified position
        val drawnValue = display.displayBuffer[row.toInt() * Display.WIDTH + col.toInt()]

        // Verify that the drawn value matches the expected byte
        assertEquals(byteToDraw, drawnValue)
    }

    @Test
    fun testAddToScreenBufferOutOfBounds() {
        val byteToDraw = 0xFF.toByte() // Byte to draw (example value)
        val row = 8.toByte() // Out of bounds row index
        val col = 8.toByte() // Out of bounds column index

        // Verify that drawing out of bounds throws an exception
        assertFailsWith<IllegalArgumentException> {
            display.addToScreenBuffer(byteToDraw, row, col)
        }
    }

    @Test
    fun testInitialBufferValues() {
        // Verify that all initial values in the buffer are 0
        for (i in display.displayBuffer.indices) {
            assertEquals(0.toByte(), display.displayBuffer[i])
        }
    }
}
