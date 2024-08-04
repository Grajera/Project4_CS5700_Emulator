package Memory.Registry_Handlers

object RRegisterManager {
    val r = arrayOf(R(),R(),R(),R(),R(),R(),R(),R())
}

class R : Register(
    ByteArray(1)
) {
    override fun write(bytes: ByteArray) {
        require(bytes.size == 1) { "ByteArray must be of size 1." }
        bytes.copyInto(destination = this.bytes, startIndex = 0, endIndex = 1)
    }
}