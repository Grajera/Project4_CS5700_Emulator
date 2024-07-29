class Display {
    private val screen = ByteArray(64) // 8x8 ASCII display

    fun draw(char: Char, row: Int, col: Int) {
        if (row !in 0..7 || col !in 0..7) {
            throw IllegalArgumentException("Row and column must be between 0 and 7.")
        }
        screen[row * 8 + col] = char.toByte()
    }

    fun getScreen(): ByteArray {
        return screen.copyOf()
    }
}
