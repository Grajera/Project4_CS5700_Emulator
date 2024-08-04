package Memory.Registry_Handlers

import com.emulator.byteArrayToInt

object PRegisterManager {
    // Initialize an instance of the P register
    val p = P()
}

// Initialize a byte array of size 2 for the P register
class P : Register(ByteArray(2)) {

    // Writes a byte array to the P register from the provided ByteArray
    override fun write(bytes: ByteArray) {
        // Ensure the ByteArray is of the correct size
        require(bytes.size == 2) { "ByteArray must be of size 2." }

        // Convert the byte array to an integer and ensure it is divisible by 2
        val intValue = byteArrayToInt(bytes)
        require(intValue % 2 == 0) { "Integer value must be divisible by 2." }

        // Copy the bytes from the input array to the P register's byte array
        bytes.copyInto(destination = this.bytes, startIndex = 0, endIndex = 2)
    }
}
