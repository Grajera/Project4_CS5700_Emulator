package Memory

import Memory.Registry_Handlers.T
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class TRegistryTest {
    private lateinit var t: T

    @BeforeTest
    fun setUp() {
        // Initialize the T register
        t = T()
    }

    @Test
    fun testWriteAndReadRegister() {
        val valueToWrite = 0x07 // Value to write to the register
        val expectedValue = valueToWrite.toByte() // Expected byte value

        // Write value to the T register
        t.writeToRegister(byteArrayOf(expectedValue))

        // Read value back from the T register
        val readValue = t.readRegister()[0]

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInitialValue() {
        // Read the initial value from the T register
        val initialValue = t.readRegister()[0]

        // Verify that the initial value is 0 (or whatever your initial value is)
        assertEquals(0.toByte(), initialValue)
    }

    @Test
    fun testOverwriteValue() {
        val initialValue = 0x04.toByte()
        val newValue = 0x09.toByte()

        // Write initial value to the T register
        t.writeToRegister(byteArrayOf(initialValue))
        // Overwrite with a new value
        t.writeToRegister(byteArrayOf(newValue))

        // Read value back from the T register
        val readValue = t.readRegister()[0]

        // Verify that the read value matches the new value
        assertEquals(newValue, readValue)
    }

    @Test
    fun testWriteMultipleValues() {
        val valuesToWrite = byteArrayOf(0x01, 0x02, 0x03)

        // Write multiple values to the T register
        valuesToWrite.forEach { value ->
            t.writeToRegister(byteArrayOf(value))
        }

        // Read the last value written
        val readValue = t.readRegister()[0]

        // Verify that the read value matches the last written value
        assertEquals(valuesToWrite.last(), readValue)
    }
}
