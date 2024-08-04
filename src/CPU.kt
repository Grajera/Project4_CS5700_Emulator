package com.emulator

import Instructions.CPUInstructionsFactory
import com.emulator.breakByteIntoNibbles
import com.emulator.byteArrayToInt
import Memory.ROM
import Memory.Registry_Handlers.PRegisterManager.p
import Memory.Registry_Handlers.TRegisterManager.t
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object PauseTimerManager {
    val pauseTimer = AtomicBoolean(false)
}

class CPU(
    val instructionSpeed: Long = 2L,
    val timerSpeed: Long = 16L
) {
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private val instructionFactory = CPUInstructionsFactory()
    private var rom: ROM? = null

    val cpuRunnable = Runnable {
        try {
            val bytes = readNextInstructionBytes()
            require(bytes.size == 2) { "ByteArray must contain exactly 2 bytes." }
            if (bytes[0].toInt() == 0 && bytes[1].toInt() == 0) {
                executor.shutdown()
                return@Runnable
            }

            val nibbles01 = breakByteIntoNibbles(bytes[0])
            val nibbles23 = breakByteIntoNibbles(bytes[1])
            val nibble0 = nibbles01.first
            val nibble1 = nibbles01.second
            val nibble2 = nibbles23.first
            val nibble3 = nibbles23.second
            val instruction = instructionFactory.createInstruction(nibble0, nibble1, nibble2, nibble3)
            instruction.execute()
        } catch (e: Exception) {
            executor.shutdown()
            return@Runnable
        }
    }

    val timerRunnable = Runnable {
        try {
            if (PauseTimerManager.pauseTimer.get()) {
                return@Runnable
            }

            val currentT = t.read()[0].toInt()
            if (currentT > 0) {
                t.operate(byteArrayOf((currentT - 1).toByte()))
            }
        } catch (e: Exception) {
        }
    }

    fun executeProgram(rom: ROM) {
        this.rom = rom

        val cpuFuture = executor.scheduleAtFixedRate(
            cpuRunnable,
            0,
            instructionSpeed,
            TimeUnit.MILLISECONDS
        )

        val timerFuture = executor.scheduleAtFixedRate(
            timerRunnable,
            0,
            timerSpeed,
            TimeUnit.MILLISECONDS
        )

        try {
            cpuFuture.get()
            timerFuture.get()
        } catch (e: Exception) {
            println("Exception during execution scheduling: ${e.message}")
        } finally {
            executor.shutdown()
            println("Executor service shut down")
        }
    }

    private fun readNextInstructionBytes(): ByteArray {
        return try {
            val pc = byteArrayToInt(p.read())
            val byte1 = rom?.read(pc) ?: 0
            val byte2 = rom?.read(pc + 1) ?: 0
            byteArrayOf(byte1, byte2)
        } catch (e: Exception) {
            println("Exception while reading next instruction bytes: ${e.message}")
            byteArrayOf(0, 0)
        }
    }
}