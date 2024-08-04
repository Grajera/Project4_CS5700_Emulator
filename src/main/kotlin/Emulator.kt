package com.emulator

import Memory.ROM
import Memory.RomManager
import java.io.File
import java.io.IOException

class Emulator {

    private val cpu = CPU() // Initialize the CPU

    fun run(filePath: String? = null) {
        try {
            // Determine the path to the binary file
            val pathToBinaryFile = filePath ?: promptForBinaryFilePath()
            // Read the binary program and execute it using the CPU
            val binaryProgram = readBinaryProgram(pathToBinaryFile)
            cpu.executeProgram(getRomFromBinaryProgram(binaryProgram))

        } catch (e: IOException) {
            println("An I/O error occurred: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Invalid argument: ${e.message}")
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
        }
    }

    // Prompt the user for the path to the binary file
    private fun promptForBinaryFilePath(): String {
        println("Path to binary file: ")
        return readlnOrNull() ?: throw IOException("No path provided")
    }

    // Read the binary program from the given file path
    private fun readBinaryProgram(path: String): ByteArray {
        val file = File(path).apply {
            // Check if the file exists
            require(exists()) { "File not found: $path" }
        }

        return file.readBytes().also {
            // Check if the file is empty
            require(it.isNotEmpty()) { "Binary file is empty" }
        }
    }

    // Convert the binary program to a ROM object
    private fun getRomFromBinaryProgram(binaryProgram: ByteArray): ROM {
        // Ensure memory size is 4096 bytes
        val memory = binaryProgram.copyOf(4096)
        // Initialize the ROM with the memory
        RomManager.initializeRom(memory)
        // Get the ROM or throw an error
        return RomManager.getRom() ?: throw IOException("Failed to initialize ROM")
    }
}
