package Memory

object RamInstance {
    val RAM = RAM() // Singleton instance of the RAM class
}

// RAM class representing the primary memory
class RAM : PrimaryMemory(ByteArray(4096)) { // 4096 bytes for RAM

    // Reads a byte from the specified address
    override fun read(memoryLocation: Int): Byte = memoryValues[memoryLocation]

    // Writes a byte to the specified address
    override fun writeToMemory(memoryLocation: Int, byte: Byte) {
        memoryValues[memoryLocation] = byte // Store the byte at the address
    }
}
