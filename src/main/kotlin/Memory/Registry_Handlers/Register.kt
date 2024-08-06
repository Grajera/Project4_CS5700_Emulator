package Memory.Registry_Handlers

import Memory.Memory

// Abstract class representing a generic register, extending the Memory class
abstract class Register(
    memoryValues: ByteArray // Holds the register contents
) : Memory(memoryValues) {

    // Template method for writing data to the register
    fun operateOnRegister(inputValues: ByteArray) {
        writeToRegister(inputValues) // Calls the abstract write method
    }

    // Retrieves a copy of the register contents
    fun readRegister(): ByteArray {
        return memoryValues.copyOf()
    }

    // Abstract method for writing data to the register
    abstract fun writeToRegister(inputValues: ByteArray)
}
