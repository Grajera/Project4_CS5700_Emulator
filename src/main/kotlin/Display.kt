package com.emulator

// Singleton object to manage the screen
object DisplayInstance {
    val display = Display() // Initialize the screen instance
}

// Class representing the screen and its buffer
class Display {
    companion object {
        // Width of the screen buffer
        const val WIDTH = 8
        // Height of the screen buffer
        const val HEIGHT = 8
    }

    // Byte array representing the screen buffer
    val displayBuffer: ByteArray = ByteArray(WIDTH * HEIGHT)

    // Function to draw a byte at a specified row and column in the buffer
    fun addToScreenBuffer(inputBuffer: Byte, screenRowSize: Byte, screenColSize: Byte) {
        require(screenRowSize in 0 until HEIGHT && screenColSize in 0 until WIDTH) { "Row or column out of bounds." }
        displayBuffer[screenRowSize * WIDTH + screenColSize] = inputBuffer
        display() // Display your screen buffer
    }

    private fun display() {
        displayBuffer.forEachIndexed { index, byte ->
            print(byte.toInt().toChar())
            if ((index + 1) % WIDTH == 0) println() // New line after each row
        }
        println("=".repeat(WIDTH)) // Separator line
    }
}
