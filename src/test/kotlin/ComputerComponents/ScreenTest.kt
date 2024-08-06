package com.emulator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class ScreenTest {
    lateinit var screen: Screen

    @BeforeTest
    fun setUp() {
        // Initialize the Screen instance
        screen = Screen()
    }

    @Test
    fun testDrawValidPosition() {
        val byteToDraw = 0xFF.toByte() // Byte to draw (example value)
        val row = 1.toByte() // Row index
        val col = 2.toByte() // Column index

        // Draw the byte at the specified position
        screen.draw(byteToDraw, row, col)

        // Read the value from the screen buffer at the specified position
        val drawnValue = screen.buffer[row.toInt() * Screen.BUFFER_WIDTH + col.toInt()]

        // Verify that the drawn value matches the expected byte
        assertEquals(byteToDraw, drawnValue)
    }

    @Test
    fun testDrawOutOfBounds() {
        val byteToDraw = 0xFF.toByte() // Byte to draw (example value)
        val row = 8.toByte() // Out of bounds row index
        val col = 8.toByte() // Out of bounds column index

        // Verify that drawing out of bounds throws an exception
        assertFailsWith<IllegalArgumentException> {
            screen.draw(byteToDraw, row, col)
        }
    }

    @Test
    fun testInitialBufferValues() {
        // Verify that all initial values in the buffer are 0
        for (i in screen.buffer.indices) {
            assertEquals(0.toByte(), screen.buffer[i])
        }
    }
}
