package Memory.Registry_Handlers

import Memory.Memory

// Abstract class representing a generic register, extending the Memory class
abstract class Register(
    bytes: ByteArray // ByteArray to hold the register contents
) : Memory(bytes) {

    // Template method for writing data to the register
    fun operate(bytes: ByteArray) {
        write(bytes)  // Call the abstract write method, which must be implemented by subclasses
    }

    // Common read method to retrieve the contents of the register
    fun read(): ByteArray {
        // Return a copy of the byte array to prevent external modification
        return bytes.copyOf()
    }

    // Abstract method for writing data to the register, to be implemented by the subclasses
    abstract fun write(bytes: ByteArray)
}
