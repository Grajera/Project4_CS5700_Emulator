package Instructions

import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtensionContext.Store
import kotlin.test.assertNotEquals

class StoreInstructionTest {

    @Test
    fun testStoreValueInRegister() {
        // Initialize registers
        val r1 = RRegisterManager.r[1]

        // Set a value in r0
        r1.writeToRegister(byteArrayOf(0x05))

        // Create the Store instruction, which will store the value of r0 in r1
        val instruction = StoreInstruction(byteArrayOf(0x00, 0x01, 0x00))

        // Execute the instruction
        instruction.execute()

        // Verify that r1 now contains the value from r0
        assertEquals(0x05.toByte(), r1.readRegister()[0])
    }

    @Test
    fun testStoreNegativeValue() {
        // Initialize registers
        val r1 = RRegisterManager.r[1]

        // Set a negative value in r0
        r1.writeToRegister(byteArrayOf((-0x01).toByte()))

        // Create the Store instruction, which will store the value of r0 in r1
        val instruction = StoreInstruction(byteArrayOf(0x00, 0x01, 0x00))

        // Execute the instruction
        instruction.execute()

        // Verify that r1 now contains the negative value from r0
        assertEquals((-0x01).toByte(), r1.readRegister()[0])
    }

    @Test
    fun testStoreValueInDifferentRegisters() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]
        val r2 = RRegisterManager.r[2]

        // Set a value in r0
        r2.writeToRegister(byteArrayOf(0x0A))

        // Create the Store instruction to store from r0 to r2
        val instruction = StoreInstruction(byteArrayOf(0x00, 0x02, 0x00))

        // Execute the instruction
        instruction.execute()

        // Verify that r2 now contains the value from r0
        assertEquals(0x0A.toByte(), r2.readRegister()[0])
    }

    @Test
    fun testStoreWithInvalidRegisterIndex() {
        // Initialize registers
        val r0 = RRegisterManager.r[0]

        // Set a value in r0
        r0.writeToRegister(byteArrayOf(0x05))

        // Create a Store instruction with an invalid register index
        val instruction = StoreInstruction(byteArrayOf(0x00, 0xFF.toByte(), 0x00)) // Assuming 0xFF is invalid
        instruction.processNibblesForInstruction()
        instruction.performInstruction()
        assertNotEquals(0x00.toByte(), instruction.registerX.readRegister()[0])
    }
}