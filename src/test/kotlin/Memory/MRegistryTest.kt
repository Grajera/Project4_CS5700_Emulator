package Memory.Registry_Handlers.Memory

import Memory.Registry_Handlers.M
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class MRegistryTest {
    lateinit var m: M

    @BeforeTest
    fun setUp() {
        // Initialize the A register
        m = M()
    }

    @Test
    fun testWriteAndReadRegister() {
        val valueToWrite = 0x01 // Value to write to the register
        val expectedValue = valueToWrite.toByte() // Expected byte value

        // Write value to the M register
        m.writeToRegister(byteArrayOf(expectedValue))

        // Read value back from the M register
        val readValue = m.readRegister()[0]

        // Verify that the read value matches the expected value
        assertEquals(expectedValue, readValue)
    }

    @Test
    fun testReadInitialValue() {
        // Read the initial value from the M register
        val initialValue = m.readRegister()[0]

        // Verify that the initial value is 0 (or whatever your initial value is)
        assertEquals(0.toByte(), initialValue)
    }

    @Test
    fun testWriteAndReadNegativeValue() {
        val negativeValue = (-1).toByte() // Value to write (negative byte value)

        // Write negative value to the M register
        // Verify that we are returned a error
        assertFailsWith<IllegalArgumentException> {
            m.writeToRegister(byteArrayOf(negativeValue))
        }
    }
}