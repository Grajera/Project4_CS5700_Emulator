package Memory

import java.lang.IllegalStateException

object RomInstance {
    private var rom: ROM? = null // Holds the initialized ROM instance

    // Initializes the ROM with the provided byte array
    fun initializeRom(bytes: ByteArray) {
        rom = ROM(bytes) // Create a new ROM instance
    }

    // Returns the current ROM instance or null if not initialized
    fun getRom(): ROM? = rom
}

// ROM class representing read-only memory
class ROM(bytes: ByteArray) : PrimaryMemory(bytes) {

    // Reads a byte from the specified address in the ROM
    override fun read(memoryLocation: Int): Byte = memoryValues[memoryLocation]

    // Prevents writing to ROM
    override fun writeToMemory(memoryLocation: Int, byte: Byte) {
        throw IllegalStateException("Cannot write to Read Only Memory")
    }
}
