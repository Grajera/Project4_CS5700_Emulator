package org.example

import Memory
import java.io.File

class ROM(size: Int) : Memory(size) {
    override fun read(address: Int): Byte {
        return memory[address]
    }

    override fun write(address: Int, value: Byte) {
        throw UnsupportedOperationException("Cannot write to ROM")
    }

    fun loadProgram(filePath: String) {
        val file = File(filePath)
        val hexStrings = file.readLines()
        var address = 0
        for (hex in hexStrings) {
            val instruction = hex.toInt(16).toShort()
            memory[address] = (instruction.toInt() ushr 8).toByte()
            memory[address + 1] = (instruction.toInt() and 0xFF).toByte()
            address += 2
        }
    }
}
