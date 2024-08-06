package com.emulator.Utils

// Combines two nibbles into a single Byte
fun mergeNibblesToByte(highNibble: Byte, lowNibble: Byte): Byte {
    // Ensure nibbles are masked to 4 bits and combine them
    return ((highNibble.toInt() and 0x0F) shl 4 or (lowNibble.toInt() and 0x0F)).toByte()
}

// Converts a ByteArray of size 2 into an Integer
fun byteArrayToInteger(byteArray: ByteArray): Int {
    require(byteArray.size == 2) { "ByteArray must be of size 2." }
    // Combine the bytes into an integer by bit manipulation
    return (byteArray[1].toInt() and 0xFF) or ((byteArray[0].toInt() and 0xFF) shl 8)
}

// Converts an Integer (0-65535) into a ByteArray of size 2
fun integerToByteArray(i: Int): ByteArray {
    require(i in 0..0xFFFF) { "Integer value must be between 0 and 65535." }
    return byteArrayOf(
        // High byte
        (i shr 8 and 0xFF).toByte(),
        // Low byte
        (i and 0xFF).toByte()
    )
}
