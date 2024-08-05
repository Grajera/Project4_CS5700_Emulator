package Memory

import java.lang.IllegalStateException

object RomManager {
    private var rom: ROM? = null // Holds the initialized ROM instance

    // Initializes the ROM with the provided byte array
    fun initializeRom(bytes: ByteArray) {
        rom = ROM(bytes) // Create a new ROM instance
    }

    // Returns the current ROM instance or null if not initialized
    fun getRom(): ROM? = rom
}

class ROM(bytes: ByteArray) : PrimaryMemory(bytes) {
    // Reads a byte from the specified address in the ROM
    override fun read(address: Int): Byte = memoryValues[address]

    // Prevents writing to ROM
    override fun writeToMemory(address: Int, byte: Byte) {
        throw IllegalStateException("Cannot write to Read Only Memory")
    }
}
