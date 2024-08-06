package Memory

object RamInstance {
    val RAM = RAM() // Singleton instance of the RAM class
}

// RAM class representing the primary memory
class RAM : PhysicalMemoryType(ByteArray(4096)) { // 4096 bytes for RAM

    // Reads a byte from the specified address
    override fun readMemoryAddress(memoryLocation: Int): Byte = memoryValues[memoryLocation]

    // Writes a byte to the specified address
    override fun writeToMemoryLocation(memoryLocation: Int, byte: Byte) {
        memoryValues[memoryLocation] = byte // Store the byte at the address
    }
}
