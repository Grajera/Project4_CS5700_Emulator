package Instructions

import Memory.Registry_Handlers.RRegisterInstance
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class ConvertByteToAsciiInstructionTest {

    private lateinit var instruction: ConvertByteToAsciiInstruction

    @BeforeEach
    fun setUp() {
        // Initialize the instruction with a sample nibble (assuming it points to the correct register)
        val nibbles = byteArrayOf(0,0,0) // Assume the first nibble points to the target register
        instruction = ConvertByteToAsciiInstruction(nibbles)

        // Set a valid ASCII byte in the R register for testing
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(65)) // 'A' in ASCII
    }

    @Test
    fun testConvertByteToAscii() {
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(0x01))
        instruction.runTask() // Perform the conversion

        // Read the converted ASCII value from the register
        val result = RRegisterInstance.r[0].readRegister()[0].toInt()

        // Check that the result is the correct ASCII value for '0'
        assertEquals(49, result) // ASCII value for '0'
    }

    @Test
    fun testConvertInvalidByteToAscii() {
        // Set an invalid ASCII value (greater than 127)
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(200.toByte())) // Out of ASCII range

        // Execute the conversion and expect an exception
        val exception = assertThrows(IllegalArgumentException::class.java) {
            instruction.runTask()
        }

        // Check that the exception message is appropriate
        assertEquals("Value in source register is out of range (0-127).", exception.message)
    }

    @Test
    fun testConvertByteToAsciiHandlesNegativeValue() {
        // Set a negative value (if applicable)
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(-1)) // Assuming -1 is represented as 255 in a byte

        // Execute the conversion and expect an exception
        val exception = assertThrows(IllegalArgumentException::class.java) {
            instruction.runTask()
        }

        // Check that the exception message is appropriate
        assertEquals("Value in source register is out of range (0-127).", exception.message)
    }
}
