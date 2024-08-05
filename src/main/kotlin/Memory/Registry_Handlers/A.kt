package Memory.Registry_Handlers

object ARegisterManager {
    val a = A() // Initialize an instance of the A register
}
// Initialize a byte array of size 2 for the A register
class A : Register(ByteArray(2)) {

    // Writes a byte array to the A register from the provided ByteArray
    override fun writeToRegister(inputValues: ByteArray) {
        require(inputValues.size == 2) { "registerValues must have 2 values" }
        // Directly assign the bytes from the input array to the A register's byte array
        this.memoryValues[0] = inputValues[0]
        this.memoryValues[1] = inputValues[1]
    }
}