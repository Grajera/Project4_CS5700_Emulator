package Memory.Registry_Handlers

object MRegisterManager {
    // Initialize an instance of the M register
    val m = M()
}

// Initialize a byte array of size 1 for the M register
class M : Register(ByteArray(1)) {

    // Writes a byte array to the M register from the provided ByteArray
    override fun write(bytes: ByteArray) {
        // Ensure the ByteArray is of the correct size
        require(bytes.size == 1) { "ByteArray must be of size 1." }

        // Extract the flag value and validate it
        val flag = bytes[0].toInt() and 0xFF
        require(flag == 0 || flag == 1) { "Invalid flag value. Must be 0 or 1." }

        // Copy the bytes from the input array to the M register's byte array
        bytes.copyInto(destination = this.bytes, startIndex = 0, endIndex = 1)
    }
}
