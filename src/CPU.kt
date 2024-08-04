package com.emulator

import Instructions.CPUInstructionsFactory
import Memory.ROM
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.TRegisterManager.t
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

// Singleton to manage the pause state of the timer
object PauseTimerManager {
    val pause = AtomicBoolean(false)
}

class CPU(
    private val instructionSpeed: Long = 2L,
    private val timerSpeed: Long = 16L
) {
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private val instructionFactory = CPUInstructionsFactory()
    private var rom: ROM? = null

    // Runnable to execute CPU instructions
    private val cpuRunnable = Runnable {
        try {
            val bytes = readNextInstructionBytes()
            require(bytes.size == 2) { "ByteArray must contain exactly 2 bytes." }

            // Shut down executor if both bytes are zero
            if (bytes.all { it.toInt() == 0 }) {
                executor.shutdown()
                return@Runnable
            }

            // Split bytes into nibbles and execute instruction
            val (nibble0, nibble1) = splitByteIntoNibbles(bytes[0])
            val (nibble2, nibble3) = splitByteIntoNibbles(bytes[1])
            val instruction = instructionFactory.createInstruction(nibble0, nibble1, nibble2, nibble3)
            instruction.execute()
        } catch (e: Exception) {
            executor.shutdown() // Shut down on error
        }
    }

    // Runnable to manage timer updates
    private val timerRunnable = Runnable {
        if (!PauseTimerManager.pause.get()) {
            try {
                val currentT = t.read()[0].toInt()
                if (currentT > 0) {
                    t.operate(byteArrayOf((currentT - 1).toByte()))
                }
            } catch (e: Exception) {
                // Handle exception silently
            }
        }
    }

    // Start execution of the program with the provided ROM
    fun executeProgram(rom: ROM) {
        this.rom = rom

        val cpuFuture = executor.scheduleAtFixedRate(cpuRunnable, 0, instructionSpeed, TimeUnit.MILLISECONDS)
        val timerFuture = executor.scheduleAtFixedRate(timerRunnable, 0, timerSpeed, TimeUnit.MILLISECONDS)

        try {
            cpuFuture.get()
            timerFuture.get()
        } catch (e: Exception) {
            println("Exception during execution scheduling: ${e.message}")
        } finally {
            executor.shutdown() // Ensure executor is shut down
            println("Executor service shut down")
        }
    }

    // Read the next two instruction bytes from ROM
    private fun readNextInstructionBytes(): ByteArray {
        return try {
            val pc = byteArrayToInt(p.read())
            val byte1 = rom?.read(pc) ?: 0
            val byte2 = rom?.read(pc + 1) ?: 0
            byteArrayOf(byte1, byte2)
        } catch (e: Exception) {
            println("Exception while reading next instruction bytes: ${e.message}")
            byteArrayOf(0, 0) // Return default if error occurs
        }
    }
}
