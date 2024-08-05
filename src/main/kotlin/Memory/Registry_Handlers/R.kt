package Memory.Registry_Handlers

object RRegisterManager {
    // Create an array of 8 R registers
    val r = Array(8) { R() }
}

// Initialize a byte array of size 1 for the R register
class R : Register(ByteArray(1)) {

    // Writes a byte to the R register from the provided ByteArray
    override fun writeToRegister(inputValues: ByteArray) {
        // Ensure the ByteArray is of the correct size
        require(inputValues.size == 1) { "ByteArray must be of size 1." }
        // Copy the byte from the input array to the R register's byte array
        inputValues.copyInto(destination = this.memoryValues, startIndex = 0, endIndex = 1)
    }
}

