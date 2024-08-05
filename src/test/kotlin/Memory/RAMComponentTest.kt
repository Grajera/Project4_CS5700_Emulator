package Memory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class RAMComponentTest {

    private val ramSize = 8000 // Define a size outside of the RAM
    private val ram = RAM() // Initialize the RAM component

    @Test
    fun testWriteAndRead() {
        val address = 0x10
        val valueToWrite = 0xAB.toByte()

        // Write the value to RAM
        ram.writeToMemory(address, valueToWrite)

        // Read the value back from RAM
        val readValue = ram.read(address)

        // Verify that the written value matches the read value
        assertEquals(valueToWrite, readValue)
    }

    @Test
    fun testWriteInvalidAddress() {
        // Verify that writing to an invalid address throws an exception
        assertFailsWith<IndexOutOfBoundsException> {
            ram.writeToMemory(ramSize, 0x00)
        }
    }

    @Test
    fun testReadInvalidAddress() {
        // Verify that reading from an invalid address throws an exception
        assertFailsWith<IndexOutOfBoundsException> {
            ram.read(ramSize)
        }
    }

    @Test
    fun testWriteAndReadBoundary() {
        val address = 0xFF // Write to the last valid address
        val valueToWrite = 0xCD.toByte()

        // Write the value to the last valid address
        ram.writeToMemory(address, valueToWrite)

        // Read the value back from the last valid address
        val readValue = ram.read(address)

        // Verify that the written value matches the read value
        assertEquals(valueToWrite, readValue)
    }

    @Test
    fun testWriteBeyondBoundary() {
        val outOfBoundsAddress = ramSize // Example of an out-of-bounds address

        // Verify that writing to an out-of-bounds address throws an exception
        assertFailsWith<IndexOutOfBoundsException> {
            ram.writeToMemory(outOfBoundsAddress, 0x00)
        }
    }

    @Test
    fun testReadBeyondBoundary() {
        val outOfBoundsAddress = ramSize // Example of an out-of-bounds address

        // Verify that reading from an out-of-bounds address throws an exception
        assertFailsWith<IndexOutOfBoundsException>{
            ram.read(outOfBoundsAddress)
        }
    }
}
