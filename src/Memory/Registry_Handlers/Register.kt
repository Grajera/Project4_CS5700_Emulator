package Memory.Registry_Handlers

import Memory.Memory

abstract class Register(
    bytes: ByteArray
): Memory(bytes) {

    // Template method for reading
    fun operate(bytes: ByteArray) {
        write(bytes)  // Step that subclasses must implement
    }

    // Common read method
    fun read(): ByteArray {
        return bytes.copyOf()
    }
    // Abstract method for writing, to be implemented by subclasses
    abstract fun write(bytes: ByteArray)
}