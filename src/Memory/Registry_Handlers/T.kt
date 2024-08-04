package Memory.Registry_Handlers

object TRegisterManager {
    val t = T() // Initialize an instance of the T register
}

class T : Register(
    ByteArray(1) // Create a byte array of size 1 for the T register
) {
    // Writes a byte to the T register from the provided ByteArray
    override fun write(bytes: ByteArray) {
        // Ensure the ByteArray is of the correct size
        require(bytes.size == 1) { "ByteArray must be of size 1." }
        // Copy the byte from the input array to the T register's byte array
        bytes.copyInto(destination = this.bytes, startIndex = 0, endIndex = 1)
    }
}
