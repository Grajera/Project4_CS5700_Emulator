package com.emulator

import Memory.ROM
import Memory.RomInstance
import java.io.File
import java.io.IOException

class D5700 {

    private val cpu = CPU() // Initialize the CPU

    fun run() {
        try {
            cpu.runBinaryFile(loadBinary(readBinary(getFilePath())))
        }
         catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
         }
    }

    // Prompt the user for the path to the binary file
    private fun getFilePath(): String {
        println("Path to .out file (Example: roms\\addition.out): ")
        return readlnOrNull() ?: throw IOException("No path provided")
    }

    // Read the binary program from the given file path
    private fun readBinary(path: String): ByteArray {
        val file = File(path).apply {
            // Check if the file exists
            require(exists()) { "File not found: $path" }
        }

        return file.readBytes().also {
            // Check if the file is empty
            require(it.isNotEmpty()) { "Binary file is empty" }
        }
    }

    // Convert the binary to a ROM object
    private fun loadBinary(file: ByteArray): ROM {
        // Ensure memory size is 4096 bytes
        val memory = file.copyOf(4096)
        // Initialize the ROM with the memory
        RomInstance.initializeRom(memory)
        // Get the ROM or throw an error
        return RomInstance.getRom() ?: throw IOException("Failed to initialize ROM")
    }
}
