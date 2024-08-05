package Memory

object RamManager {
    val RAM = RAM() // Instance of the RAM class
}

class RAM : PrimaryMemory(ByteArray(4096)) { // 4096 bytes for RAM
    // Reads a byte from the specified address
    override fun read(address: Int): Byte = memoryValues[address]

    // Writes a byte to the specified address
    override fun writeToMemory(address: Int, byte: Byte) {
        // Store the byte at the address
        memoryValues[address] = byte
    }
}
