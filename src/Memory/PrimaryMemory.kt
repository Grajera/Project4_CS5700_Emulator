package Memory

abstract class ContiguousMemory(
    bytes: ByteArray
): Memory(bytes) {
    abstract fun read(address: Int): Byte
    abstract fun write(address: Int, byte: Byte)
}