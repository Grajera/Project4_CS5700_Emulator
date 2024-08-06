package Memory

// Abstract class representing primary memory
abstract class PhysicalMemoryType(
    inputValues: ByteArray
) : Memory(inputValues) {
    abstract fun readMemoryAddress(memoryLocation: Int): Byte
    abstract fun writeToMemoryLocation(memoryLocation: Int, byte: Byte)
}
