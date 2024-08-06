package Memory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ROMComponentTest {

    private val romSize = 256 // Define the size of the ROM
    private val rom = ROM(ByteArray(romSize).apply {
        // Initialize ROM with some test data
        for (i in indices) {
            this[i] = i.toByte()
        }
    }) // Initialize the ROM component with test data

    @Test
    fun testReadValidAddress() {
        val address = 0x10 // A valid address to read from
        val expectedValue = address.toByte() // The expected value at that address

        // Read the value back from ROM
        val readValue = rom.readMemoryAddress(address)

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInvalidAddress() {
        val invalidAddress = romSize // Example of an invalid address (out of bounds)

        // Verify that reading from an invalid address throws an exception
        assertFailsWith<IndexOutOfBoundsException> {
            rom.readMemoryAddress(invalidAddress)
        }
    }

    @Test
    fun testWriteToRom() {
        val address = 0x10 // A valid address to write to

        // Verify that writing to ROM throws an exception
        assertFailsWith<IllegalStateException> {
            rom.writeToMemoryLocation(address, 0x00)
        }
    }

    @Test
    fun testWriteBeyondBoundary() {
        val outOfBoundsAddress = romSize // Example of an out-of-bounds address

        // Verify that writing to an out-of-bounds address throws an exception
        assertFailsWith<IllegalStateException> {
            rom.writeToMemoryLocation(outOfBoundsAddress, 0x00)
        }
    }

    @Test
    fun testReadBoundary() {
        val address = romSize - 1 // Read from the last valid address
        val expectedValue = (romSize - 1).toByte() // The expected value at that address

        // Read the value back from ROM
        val readValue = rom.readMemoryAddress(address)

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }
}