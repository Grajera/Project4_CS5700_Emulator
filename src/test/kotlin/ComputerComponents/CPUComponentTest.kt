package com.emulator

import Memory.ROM
import Memory.RomInstance
import Memory.Registry_Handlers.TRegisterInstance.t
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class CPUComponentTest {
    lateinit var cpu: CPU
    lateinit var rom: ROM

    @BeforeEach
    fun setUp() {
        // Initialize the CPU and a mock ROM with sample instructions
        cpu = CPU()
        val mockRomData = byteArrayOf(0x00, 0x01, 0x02, 0x00) // Replace with your instruction bytes
        RomInstance.initializeRom(mockRomData)
        rom = RomInstance.getRom()!!
    }

    @Test
    fun testPauseTimer() {
        // Start execution in a separate thread
        val executionThread = thread {
            cpu.run(rom)
        }

        // Allow the CPU to run for a short time
        Thread.sleep(50)

        // Pause the timer
        Clock.pauseTimer()

        // Capture the current value of T
        val initialT = t.readRegister()[0].toInt()
        Thread.sleep(50) // Wait and check if T value has changed
        val finalT = t.readRegister()[0].toInt()

        // Verify that the T value has not changed
        assertEquals(initialT, finalT)
    }

    @Test
    fun testShutDownOnZeroBytes() {
        // Create ROM with zero bytes to test shutdown behavior
        val mockRomData = byteArrayOf(0x00, 0x00)
        RomInstance.initializeRom(mockRomData)
        rom = RomInstance.getRom()!!

        // Start execution in a separate thread
        val executionThread = thread {
            cpu.run(rom)
        }

        // Allow some time for execution to check if it shuts down
        Thread.sleep(50)

        // Clean up
        executionThread.interrupt()
    }
}
