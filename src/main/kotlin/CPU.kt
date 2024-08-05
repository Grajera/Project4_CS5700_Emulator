package com.emulator

import Instructions.CPUInstructionsFactory
import Memory.ROM
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.TRegisterManager.t
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object Clock {
    val paused = AtomicBoolean(false)

    // Method to pause the timer
    fun pauseTimer() {
        paused.set(true)
    }
        // Method to resume the timer
    fun resumeTimer() {
        paused.set(false)
    }

    // Method to check if the timer is paused
    fun isPaused(): Boolean {
        return paused.get()
    }
}

class CPU(
    private val instructionSpeed: Long = 2L,
    private val timerSpeed: Long = 16L
) {
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private val instructionFactory = CPUInstructionsFactory()
    private var rom: ROM? = null

    // Runnable to execute CPU instructions
    private val cpu = Runnable {
        try {
            fun splitNibbles(byte: Byte): Pair<Byte, Byte> {
                // Extract the high and low nibbles
                return (byte.toUByte().toInt() shr 4).toByte() to (byte.toUByte().toInt() and 0x0F).toByte()
            }
            val bytes = readNextPairInByteArray()

            // Shut down executor if both bytes are zero
            if (bytes.all { it.toInt() == 0 }) {
                executor.shutdown()
                return@Runnable
            }

            // Split bytes into nibbles and execute instruction
            // Split bytes into nibbles directly here
            val (nibble0, nibble1) = splitNibbles(bytes[0])
            val (nibble2, nibble3) = splitNibbles(bytes[1])
            val instruction = instructionFactory.makeInstructions(nibble0, nibble1, nibble2, nibble3)
            instruction.execute()
        } catch (e: Exception) {
            executor.shutdown() // Shut down on error
        }
    }

    // Runnable to manage timer updates
    private val timer = Runnable {
        if (!Clock.isPaused()) {
            try {
                val currentT = t.readRegister()[0].toInt()
                if (currentT > 0) {
                    t.operateOnRegister(byteArrayOf((currentT - 1).toByte()))
                }
            } catch (e: Exception) {
                // Handle exception silently
            }
        }
    }

    // Start execution of the program with the provided ROM
    fun runBinaryFile(rom: ROM) {
        this.rom = rom
        try {
        executor.scheduleAtFixedRate(cpu, 0, 2L, TimeUnit.MILLISECONDS).get()
        executor.scheduleAtFixedRate(timer, 0, 16L, TimeUnit.MILLISECONDS).get()
        } catch (e: Exception) {
            // end of execution.
        }
        executor.shutdown() // Ensure executor is shut down
        println("Execution of program shut down")
    }

    // Read the next two instruction from ROM
    private fun readNextPairInByteArray(): ByteArray {
        return try {
            val pc = byteArrayToInt(p.readRegister())
            val byte1 = rom?.read(pc) ?: 0
            val byte2 = rom?.read(pc + 1) ?: 0
            byteArrayOf(byte1, byte2)
        } catch (e: Exception) {
            println("Exception while reading next instruction: ${e.message}")
            byteArrayOf(0, 0) // Return default if error occurs
        }
    }
}
