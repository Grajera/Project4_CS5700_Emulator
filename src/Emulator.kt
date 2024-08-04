package com.emulator

import Memory.ROM
import Memory.RomManager
import java.io.File
import java.io.IOException

class Emulator {

    private val cpu = CPU()

    fun run(filePath: String? = null) {

        var pathToBinaryFile = filePath

        try {
            if (pathToBinaryFile == null) {
                pathToBinaryFile = getPathToBinaryFile()
            }

            val binaryFile = getBinaryFile(pathToBinaryFile)

            val binaryProgram = getBinaryProgramFromBinaryFile(binaryFile)
            val rom = getRomFromBinaryProgram(binaryProgram)

            cpu.executeProgram(rom)

        } catch (e: IOException) {
            println("An I/O error occurred: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("Invalid argument: ${e.message}")
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
        }
    }

    private fun getPathToBinaryFile(): String {
        println("Path to binary file: ")
        val pathToBinaryFile = readlnOrNull() ?: throw IOException("No path provided")
        return pathToBinaryFile
    }

    private fun getBinaryFile(pathToBinaryFile: String): File {
        val file = File(pathToBinaryFile)
        if (!file.exists()) {
            throw IOException("File not found: $pathToBinaryFile")
        }
        return file
    }

    private fun getBinaryProgramFromBinaryFile(binaryFile: File): ByteArray {
        return try {
            binaryFile.readBytes()
        } catch (e: IOException) {
            throw IOException("Failed to read binary file", e)
        }
    }

    private fun getRomFromBinaryProgram(binaryProgram: ByteArray): ROM {
        val memory = ByteArray(4096)
        for (i in binaryProgram.indices) {
            memory[i] = binaryProgram[i]
        }
        RomManager.initializeRom(memory)
        val rom = RomManager.getRom()
        if (rom == null) {
            throw IOException("Failed to initialize ROM")
        }
        return rom
    }
}