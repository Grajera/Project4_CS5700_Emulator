package Memory

object RomManager {
    private var rom: ROM? = null

    fun initializeRom(bytes: ByteArray) {
        rom = ROM(bytes)
    }

    fun getRom(): ROM? {
        return rom
    }
}

class ROM(
    bytes: ByteArray
) : ContiguousMemory(
    bytes
) {
    override fun read(address: Int): Byte {
        val byte = bytes[address]
        return byte
    }

    override fun write(address: Int, byte: Byte) {
        throw UnsupportedOperationException("Cannot write to ROM")
    }
}