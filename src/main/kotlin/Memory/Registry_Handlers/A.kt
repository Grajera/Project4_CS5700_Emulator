package Memory.Registry_Handlers

object ARegisterInstance {
    // Initialize an instance of the A register
    val a = A()
}

// Represents the A register
class A : Register(ByteArray(2)) {

    // Writes a byte array to the A register
    override fun writeToRegister(inputValues: ByteArray) {
        require(inputValues.size == 2) { "A register must have exactly 2 values." }
        System.arraycopy(inputValues, 0, this.memoryValues, 0, 2)
    }
}
