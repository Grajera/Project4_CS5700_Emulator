package Memory

abstract class PrimaryMemory(
    bytes: ByteArray
): Memory(bytes) {
    abstract fun read(address: Int): Byte
    abstract fun writeToMemory(address: Int, byte: Byte)
}