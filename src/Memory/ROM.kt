package Memory

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
    override fun read(address: Int): Byte = bytes[address]

    // Prevents writing to ROM
    override fun write(address: Int, byte: Byte) {
        throw UnsupportedOperationException("Cannot write to Read Only Memory")
    }
}
