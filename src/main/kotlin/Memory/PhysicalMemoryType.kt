package Memory

// Abstract class representing primary memory
abstract class PhysicalMemoryType(
    inputValues: ByteArray
) : Memory(inputValues) {
    abstract fun read(memoryLocation: Int): Byte
    abstract fun writeToMemory(memoryLocation: Int, byte: Byte)
}
