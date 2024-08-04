package com.emulator

// Singleton object to manage the screen
object ScreenManager {
    val screen = Screen() // Initialize the screen instance
}

// Class representing the screen and its buffer
class Screen {
    companion object {
        // Width of the screen buffer
        const val BUFFER_WIDTH = 8
        // Height of the screen buffer
        const val BUFFER_HEIGHT = 8
    }

    // Byte array representing the screen buffer
    val buffer: ByteArray = ByteArray(BUFFER_WIDTH * BUFFER_HEIGHT)

    private fun display() {
        for (row in 0 until BUFFER_HEIGHT) {
            for (col in 0 until BUFFER_WIDTH) {
                // Calculate the index in the buffer for the current row and column
                val index = row * BUFFER_WIDTH + col
                // Convert the byte at the index to a character and print it
                val char = buffer[index].toInt().toChar()
                print(char)
            }
            println() // Move to the next line after printing a row
        }
        // Print a separator line after displaying the buffer
        println("=".repeat(BUFFER_WIDTH))
    }

    // Function to draw a byte at a specified row and column in the buffer
    fun draw(byte: Byte, row: Byte, col: Byte) {
        val rowIdx = row.toInt() // Convert row from Byte to Int
        val colIdx = col.toInt() // Convert column from Byte to Int

        // Check if the specified row and column are within bounds
        if (rowIdx in 0 until BUFFER_HEIGHT && colIdx in 0 until BUFFER_WIDTH) {
            // Calculate the address in the buffer and assign the byte value
            val address = rowIdx * BUFFER_WIDTH + colIdx
            buffer[address] = byte
            display() // Display the updated buffer
        } else {
            throw IllegalArgumentException("Row or column out of bounds.") // Throw an error if out of bounds
        }
    }
}
