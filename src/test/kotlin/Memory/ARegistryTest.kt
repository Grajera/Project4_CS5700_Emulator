package Memory

import Memory.Registry_Handlers.A
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class ARegistryTest {
    lateinit var a: A

    @BeforeTest
    fun setUp() {
        // Initialize the A register
        a = A()
    }

    @Test
    fun testWriteAndReadRegister() {
        val valueToWrite = 0xAB // Value to write to the register
        val expectedValue = valueToWrite.toByte() // Expected byte value

        // Write value to the A register
        a.writeToRegister(byteArrayOf(expectedValue, 0x00))

        // Read value back from the A register
        val readValue = a.readRegister()[0]

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInitialValue() {
        // Read the initial value from the A register
        val initialValue = a.readRegister()[0]

        // Verify that the initial value is 0 (or whatever your initial value is)
        assertEquals(0.toByte(), initialValue)
    }

    @Test
    fun testWriteMultipleValues() {
        val valuesToWrite = byteArrayOf(0x01, 0x02) // Values to write to the register

        // Write the values to the A register
        a.writeToRegister(valuesToWrite)

        // Read the value back from the A register
        val readValue = a.readRegister()

        // Verify that the read values match the expected values
        assertEquals(valuesToWrite.toList(), readValue.toList())
    }

    @Test
    fun testWriteAndReadNegativeValue() {
        val negativeValue = (-1).toByte() // Value to write (negative byte value)

        // Write negative value to the A register
        a.writeToRegister(byteArrayOf(negativeValue, 0x00))

        // Read value back from the A register
        val readValue = a.readRegister()[0]

        // Verify that the read value matches the expected negative value
        assertEquals(negativeValue, readValue)
    }
}
