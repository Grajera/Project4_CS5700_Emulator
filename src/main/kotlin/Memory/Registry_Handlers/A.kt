package Memory.Registry_Handlers

object AManager {
    val a = A() // Initialize an instance of the A register
}
// Initialize a byte array of size 2 for the A register
class A : Register(ByteArray(2)) {

    // Writes a byte array to the A register from the provided ByteArray
    override fun writeToRegister(bytes: ByteArray) {
        // Ensure the ByteArray is of the correct size
        require(bytes.size == 2) { "ByteArray must be of size 2." }

        // Copy the bytes from the input array to the A register's byte array
        bytes.copyInto(destination = this.bytes, startIndex = 0, endIndex = 2)
    }
}