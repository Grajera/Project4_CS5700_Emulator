abstract class Memory(private val size: Int) {
    protected val memory = ByteArray(size)

    abstract fun read(address: Int): Byte

    abstract fun write(address: Int, value: Byte)
}
