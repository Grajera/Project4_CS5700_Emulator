package Memory

import Memory.Registry_Handlers.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class RRegistryTest {
    private lateinit var r: R

    @BeforeTest
    fun setUp() {
        // Initialize the R register
        r = R()
    }

    @Test
    fun testWriteAndReadRegister() {
        val valueToWrite = 0x05 // Value to write to the register
        val expectedValue = valueToWrite.toByte() // Expected byte value

        // Write value to the R register
        r.writeToRegister(byteArrayOf(expectedValue))

        // Read value back from the R register
        val readValue = r.readRegister()[0]

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInitialValue() {
        // Read the initial value from the R register
        val initialValue = r.readRegister()[0]

        // Verify that the initial value is 0 (or whatever your initial value is)
        assertEquals(0.toByte(), initialValue)
    }

    @Test
    fun testOverwriteValue() {
        val initialValue = 0x03.toByte()
        val newValue = 0x08.toByte()

        // Write initial value to the R register
        r.writeToRegister(byteArrayOf(initialValue))
        // Overwrite with a new value
        r.writeToRegister(byteArrayOf(newValue))

        // Read value back from the R register
        val readValue = r.readRegister()[0]

        // Verify that the read value matches the new value
        assertEquals(newValue, readValue)
    }

    @Test
    fun testWriteMultipleValues() {
        val valuesToWrite = byteArrayOf(0x01, 0x02, 0x03)

        // Write multiple values to the R register
        valuesToWrite.forEach { value ->
            r.writeToRegister(byteArrayOf(value))
        }

        // Read the last value written
        val readValue = r.readRegister()[0]

        // Verify that the read value matches the last written value
        assertEquals(valuesToWrite.last(), readValue)
    }
}
