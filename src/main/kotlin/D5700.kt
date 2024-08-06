package com.emulator

import Memory.ROM
import Memory.RomInstance
import java.io.File
import java.io.FileNotFoundException

// Main emulator class for the D5700
class D5700 {

    // Starts the emulator
    fun startExecution() {
        try {
            // Load the binary file and run the CPU
            CPU().run(loadFileIntoMemory(readFile()))
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
        }
    }

    // Reads the binary file after validating its existence and content
    private fun readFile(): ByteArray {
        println("Path to .out file (Example: roms\\addition.out): ")
        val path = readlnOrNull() ?: throw FileNotFoundException("No path provided")
        val file = File(path)

        // Check if the file exists and is not empty
        require(file.exists()) { "File not found: $path" }
        val binaryData = file.readBytes()
        require(binaryData.isNotEmpty()) { "Binary file is empty" }

        return binaryData.copyOf(4096) // Ensure memory size is 4096 bytes
    }

    // Converts the binary data into a ROM object
    private fun loadFileIntoMemory(file: ByteArray): ROM {
        RomInstance.initializeRom(file) // Initialize ROM with memory
        return RomInstance.getRom() ?: throw UninitializedPropertyAccessException("Failed to get instance of ROM")
    }
}
