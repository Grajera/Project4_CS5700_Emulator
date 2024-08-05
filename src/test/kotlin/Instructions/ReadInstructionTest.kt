package Instructions

import Memory.RamManager
import Memory.Registry_Handlers.*
import Memory.RomManager
import com.emulator.intToByteArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ReadInstructionTest {

    @Test
    fun testPerformInstruction() {
        val registerXIndex = 0x2
        val nibbles = byteArrayOf(registerXIndex.toByte(), 0x00, 0x00)

        val instruction = ReadInstruction(nibbles)
        instruction.processNibblesForInstruction()
        instruction.performInstruction()

        assertEquals(RRegisterManager.r[registerXIndex], instruction.registerX)
    }

    @Test
    fun testReadFromZeroRegister() {
        // Set the zero register (e.g., R0) to zero
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(0x00))

        // Set up the instruction to read from R0 and store it in T
        val instruction = ReadInstruction(byteArrayOf(0x00,0x00,0x00)) // Assuming 0x00 refers to R0

        instruction.processNibblesForInstruction()

        // Perform the operation
        instruction.performInstruction()

        // Check that T is also zero
        val tValue = TRegisterManager.t.readRegister()[0]
        assertEquals(0x00.toByte(), tValue)
    }

    @Test
    fun testInvalidRegisterIndex() {
        // Set up the instruction to read from an invalid R register (e.g., R10)
        val instruction = ReadInstruction(byteArrayOf(0x0A,0x00,0x00)) // Assuming R10 is out of bounds

        // Perform the operation and check for an exception
        val exception = assertThrows(IndexOutOfBoundsException::class.java) {
            instruction.processNibblesForInstruction()
            instruction.performInstruction()
        }

        assertEquals("Index 10 out of bounds for length 8", exception.message) // Update this message according to your implementation
    }

    @Test
    fun testPerformInstructionWithMultipleRegisters() {
        // Set values in R3 and R4
        RRegisterManager.r[3].operateOnRegister(byteArrayOf(0x10))
        RRegisterManager.r[4].operateOnRegister(byteArrayOf(0x20))

        // Read from R3 and store it in T
        var instruction = ReadInstruction(byteArrayOf(0x03,0x00,0x00)) // Assuming 0x03 refers to R3
        instruction.processNibblesForInstruction()
        instruction.performInstruction()
        assertEquals(RRegisterManager.r[3], instruction.registerX)

        // Read from R4 and store it in T
        instruction = ReadInstruction(byteArrayOf(0x04, 0x00, 0x00)) // Assuming 0x04 refers to R4
        instruction.processNibblesForInstruction()
        instruction.performInstruction()
        assertEquals(RRegisterManager.r[4], instruction.registerX)
    }
    @Test
    fun testPerformInstructionReadFromRAM() {
        val ram = RamManager.RAM
        val address = 0x100
        ram.writeToMemory(address, 0x42.toByte())

        val registerX = R()
        val nibbles = byteArrayOf(0x02, 0x01, 0x00)
        val instruction = ReadInstruction(nibbles)
        instruction.registerX = registerX

        MRegisterManager.m.writeToRegister(byteArrayOf(0x00))

        ARegisterManager.a.writeToRegister(intToByteArray(address))

        instruction.performInstruction()

        assertEquals(0x42.toByte(), registerX.readRegister()[0])
    }

    @Test
    fun testPerformInstructionReadFromROM() {
        val address = 0x200
        val memory = ByteArray(4096)
        memory[address] = 0x84.toByte()
        RomManager.initializeRom(memory)

        val registerX = R()
        val nibbles = byteArrayOf(0x02, 0x02, 0x00)
        val instruction = ReadInstruction(nibbles)
        instruction.registerX = registerX

        MRegisterManager.m.writeToRegister(byteArrayOf(0x01))

        ARegisterManager.a.writeToRegister(intToByteArray(address))

        instruction.performInstruction()

        assertEquals(0x84.toByte(), registerX.readRegister()[0])
    }

    @Test
    fun testPerformInstructionInvalidMemoryType() {
        val nibbles = byteArrayOf(0x02, 0x01, 0x00)
        val instruction = ReadInstruction(nibbles)

        assertThrows(IllegalArgumentException::class.java) {
            MRegisterManager.m.writeToRegister(byteArrayOf(0x02))
            instruction.performInstruction()
        }
    }
}