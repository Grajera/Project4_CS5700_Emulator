package Memory.Registry_Handlers

import Memory.Memory

// Abstract class representing a generic register, extending the Memory class
abstract class Register(
    bytes: ByteArray // ByteArray to hold the register contents
) : Memory(bytes) {

    // Template method for writing data to the register
    fun operateOnRegister(inputValues: ByteArray) {
        writeToRegister(inputValues)  // Call the abstract write method, which must be implemented by subclasses
    }

    // Common read method to retrieve the contents of the register
    fun readRegister(): ByteArray {
        // Return a copy of the byte array to prevent external modification
        return memoryValues.copyOf()
    }

    // Abstract method for writing data to the register, to be implemented by the subclasses
    abstract fun writeToRegister(inputValues: ByteArray)
}
