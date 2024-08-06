package Memory.Registry_Handlers

object MRegisterInstance {
    // Initialize an instance of the M register
    val m = M()
}

// Represents the M register
class M : Register(ByteArray(1)) {

    // Writes a byte array to the M register
    override fun writeToRegister(inputValues: ByteArray) {
        require(inputValues.size == 1) { "M register must have exactly 1 value." }

        // Validate the flag value (0 or 1)
        val flag = inputValues[0].toInt() and 0xFF
        require(flag == 0 || flag == 1) { "Invalid flag value. Must be 0 or 1." }

        System.arraycopy(inputValues, 0, this.memoryValues, 0, 1)
    }
}
