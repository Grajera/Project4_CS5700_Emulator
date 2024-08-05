package Instructions

import Memory.Registry_Handlers.MRegisterManager
import Memory.RomManager
import Memory.Registry_Handlers.RRegisterManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SwitchMemoryInstructionTest {

    @Test
    fun testInitialSwitchToRomMode() {
        // Setup: Initialize M register to RAM mode (0)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x00))

        // Create the SwitchMemoryInstruction
        val instruction = SwitchMemoryInstruction(byteArrayOf(0x00, 0x00, 0x00))

        // Perform the instruction
        instruction.performInstruction()

        // Verify that the M register now holds ROM mode (1)
        assertEquals(1.toByte(), MRegisterManager.m.readRegister()[0])
    }

    @Test
    fun testSwitchToRamMode() {
        // Setup: Initialize M register to ROM mode (1)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x01))

        // Create the SwitchMemoryInstruction
        val instruction = SwitchMemoryInstruction(byteArrayOf(0x00, 0x00, 0x00))

        // Perform the instruction
        instruction.performInstruction()

        // Verify that the M register now holds RAM mode (0)
        assertEquals(0.toByte(), MRegisterManager.m.readRegister()[0])
    }

    @Test
    fun testMultipleSwitches() {
        // Setup: Initialize M register to RAM mode (0)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x00))

        // Create the SwitchMemoryInstruction
        val instruction = SwitchMemoryInstruction(byteArrayOf(0x00, 0x00, 0x00))

        // First switch should change to ROM mode
        instruction.performInstruction()
        assertEquals(1.toByte(), MRegisterManager.m.readRegister()[0])

        // Second switch should change back to RAM mode
        instruction.performInstruction()
        assertEquals(0.toByte(), MRegisterManager.m.readRegister()[0])
    }

    @Test
    fun testNoSideEffects() {
        // Setup: Initialize M register to RAM mode (0)
        MRegisterManager.m.operateOnRegister(byteArrayOf(0x00))

        // Create the SwitchMemoryInstruction
        val instruction = SwitchMemoryInstruction(byteArrayOf(0x00, 0x00, 0x00))

        // Perform the instruction once
        instruction.performInstruction()

        // Save the state of M register
        val stateAfterFirstSwitch = MRegisterManager.m.readRegister()[0]

        // Perform the instruction again
        instruction.performInstruction()

        // Verify that the M register switched back correctly
        val stateAfterSecondSwitch = MRegisterManager.m.readRegister()[0]
        assertEquals(if (stateAfterFirstSwitch == 0.toByte()) 1.toByte() else 0.toByte(), stateAfterSecondSwitch)
    }
}