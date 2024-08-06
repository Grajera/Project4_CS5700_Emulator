package Memory.Registry_Handlers.Memory

import Memory.Registry_Handlers.P
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class PRegistryTest {
    lateinit var p: P

    @BeforeTest
    fun setUp() {
        // Initialize the P register
        p = P()
    }

    @Test
    fun testWriteAndReadRegister() {
        val valueToWrite = 0x01 // Value to write to the register
        val expectedValue = valueToWrite.toByte() // Expected byte value

        // Write value to the P register
        p.writeToRegister(byteArrayOf(expectedValue, 0x00))

        // Read value back from the P register
        val readValue = p.readRegister()[0]

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInitialValue() {
        // Read the initial value from the P register
        val initialValue = p.readRegister()[0]

        // Verify that the initial value is 0 (or whatever your initial value is)
        assertEquals(0.toByte(), initialValue)
    }

    @Test
    fun testWriteAndReadNegativeValue() {
        val negativeValue = (-1).toByte() // Value to write (negative byte value)

        // Write negative value to the P register
        // Verify that we are returned an error
        assertFailsWith<IllegalArgumentException> {
            p.writeToRegister(byteArrayOf(negativeValue))
        }
    }

    @Test
    fun testOverwriteValue() {
        val initialValue = 0x05.toByte()
        val newValue = 0x10.toByte()

        // Write initial value to the P register
        p.writeToRegister(byteArrayOf(initialValue, 0x00))
        // Overwrite with a new value
        p.writeToRegister(byteArrayOf(newValue, 0x00))

        // Read value back from the P register
        val readValue = p.readRegister()[0]

        // Verify that the read value matches the new value
        assertEquals(newValue, readValue)
    }

    @Test
    fun testWriteMultipleValues() {
        val valuesToWrite = byteArrayOf(0x01, 0x02, 0x03)

        // Write multiple values to the P register
        valuesToWrite.forEach { value ->
            p.writeToRegister(byteArrayOf(value, 0x00))
        }

        // Read the last value written
        val readValue = p.readRegister()[0]

        // Verify that the read value matches the last written value
        assertEquals(valuesToWrite.last(), readValue)
    }
}
