package Instructions

import Memory.Registry_Handlers.PRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class JumpInstructionTest {
    private lateinit var instruction: JumpInstruction
    private val initialPC = 0x0000 // Initial Program Counter

    @BeforeTest
    fun setUp() {
        PRegisterManager.p.operateOnRegister(byteArrayOf(initialPC.toByte(), (initialPC shr 8).toByte())) // Set initial Program Counter
    }

    @Test
    fun testProcessNibbles() {
        val nibbles = byteArrayOf(0x02, 0x00, 0x00) // Jump to address 0x0002
        instruction = JumpInstruction(nibbles)

        instruction.processNibblesForInstruction() // Process nibbles to extract the jump address

        // Check if the address is correctly set (in this case, the address should be 0x0002)
        assertEquals(0x0002, instruction.newLocationInMemory[0])
    }

    @Test
    fun testPerformJump() {
        val nibbles = byteArrayOf(0x05, 0x00, 0x00) // Jump to address 0x0005
        instruction = JumpInstruction(nibbles)
        instruction.processNibblesForInstruction() // Process nibbles to extract the jump address

        instruction.performInstruction() // Perform the jump operation

        // Verify that the Program Counter has been updated correctly
        val newP = PRegisterManager.p.readRegister()
        val expectedP = byteArrayOf(0x05.toByte(), 0x00.toByte()) // Expected new PC value
        assertEquals(expectedP.toList(), newP.toList()) // Check if the PC is now pointing to 0x0005
    }
}