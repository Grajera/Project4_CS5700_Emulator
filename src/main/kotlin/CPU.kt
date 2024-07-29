package org.example

import Display
import Instruction
import Keyboard

class CPU(
    private val rom: ROM,
    private val ram: RAM,
    private val display: Display,
    private val keyboard: Keyboard
) {
    private val registers = ByteArray(8)
    private var programCounter = 0
    private var address = 0
    private var memoryFlag = 0
    private val timer = Timer()

    init {
        timer.start()
    }

    fun run() {
        while (true) {
            fetch()
            val instruction = decode()
            execute(instruction)
            updateProgramCounter()
        }
    }

    fun pause() {
        timer.stop()
    }

    fun reset() {
        programCounter = 0
        address = 0
        memoryFlag = 0
        timer.setTimer(0)
        registers.fill(0)
    }

    private fun fetch(): Int {
        val instruction = (rom.read(programCounter).toInt() shl 8) or rom.read(programCounter + 1).toInt()
        return instruction
    }

    private fun decode(): Instruction {
        val instruction = fetch()
        val opcode = (instruction ushr 12) and 0xF
        val param1 = (instruction ushr 8) and 0xF
        val param2 = (instruction ushr 4) and 0xF
        val param3 = instruction and 0xF

        return when (opcode) {
            0 -> Instruction.Store(param1, param2)   // STORE
            1 -> Instruction.Add(param1, param2, param3)   // ADD
            2 -> Instruction.Sub(param1, param2, param3)   // SUB
            3 -> Instruction.Read(param1)   // READ
            4 -> Instruction.Write(param1)   // WRITE
            5 -> Instruction.Jump(param1)   // JUMP
            6 -> Instruction.ReadKeyboard(param1)   // READ_KEYBOARD
            7 -> Instruction.SwitchMemory  // SWITCH_MEMORY
            8 -> Instruction.SkipEqual(param1, param2)   // SKIP_EQUAL
            9 -> Instruction.SkipNotEqual(param1, param2)   // SKIP_NOT_EQUAL
            10 -> Instruction.SetA(param1)   // SET_A
            11 -> Instruction.SetT(param1)   // SET_T
            12 -> Instruction.ReadT(param1)   // READ_T
            13 -> Instruction.ConvertToBase10(param1)   // CONVERT_TO_BASE_10
            14 -> Instruction.ConvertByteToASCII(param1, param2)   // CONVERT_BYTE_TO_ASCII
            15 -> Instruction.Draw(param1, param2, param3)   // DRAW
            else -> throw IllegalArgumentException("Invalid opcode: $opcode")
        }
    }

    private fun execute(instruction: Instruction) {
        when (instruction) {
            is Instruction.Store -> store(instruction.register, instruction.value)
            is Instruction.Add -> add(instruction.rX, instruction.rY, instruction.rZ)
            is Instruction.Sub -> sub(instruction.rX, instruction.rY, instruction.rZ)
            is Instruction.Read -> read(instruction.rX)
            is Instruction.Write -> write(instruction.rX)
            is Instruction.Jump -> jump(instruction.address)
            is Instruction.ReadKeyboard -> readKeyboard(instruction.rX)
            is Instruction.SwitchMemory -> switchMemory()
            is Instruction.SkipEqual -> skipEqual(instruction.rX, instruction.rY)
            is Instruction.SkipNotEqual -> skipNotEqual(instruction.rX, instruction.rY)
            is Instruction.SetA -> setA(instruction.value)
            is Instruction.SetT -> setT(instruction.value)
            is Instruction.ReadT -> readT(instruction.rX)
            is Instruction.ConvertToBase10 -> convertToBase10(instruction.rX)
            is Instruction.ConvertByteToASCII -> convertByteToASCII(instruction.rX, instruction.rY)
            is Instruction.Draw -> draw(instruction.rX, instruction.rY, instruction.rZ)
        }
    }

    private fun updateProgramCounter() {
        // Increment PC based on instruction type
        programCounter += 2
    }

    private fun store(register: Int, value: Int) {
        registers[register] = value.toByte()
    }

    private fun add(rX: Int, rY: Int, rZ: Int) {
        registers[rZ] = (registers[rX].toInt() + registers[rY].toInt()).toByte()
    }

    private fun sub(rX: Int, rY: Int, rZ: Int) {
        registers[rZ] = (registers[rX].toInt() - registers[rY].toInt()).toByte()
    }

    private fun read(rX: Int) {
        val value = if (memoryFlag == 0) ram.read(address) else rom.read(address)
        registers[rX] = value
    }

    private fun write(rX: Int) {
        if (memoryFlag == 0) {
            ram.write(address, registers[rX])
        } else {
            // Future support for writable ROM
        }
    }

    private fun jump(address: Int) {
        if (address % 2 != 0) throw IllegalArgumentException("Address must be even")
        programCounter = address
    }

    private fun readKeyboard(rX: Int) {
        val input = keyboard.readInput()
        registers[rX] = input.toByte()
    }

    private fun switchMemory() {
        memoryFlag = 1 - memoryFlag
    }

    private fun skipEqual(rX: Int, rY: Int) {
        if (registers[rX].toInt() == registers[rY].toInt()) {
            programCounter += 2
        }
    }

    private fun skipNotEqual(rX: Int, rY: Int) {
        if (registers[rX].toInt() != registers[rY].toInt()) {
            programCounter += 2
        }
    }

    private fun setA(value: Int) {
        address = value
    }

    private fun setT(value: Int) {
        timer.setTimer(value)
    }

    private fun readT(rX: Int) {
        registers[rX] = timer.readTimer().toByte()
    }

    private fun convertToBase10(rX: Int) {
        val value = registers[rX].toInt() and 0xFF
        registers[0] = (value / 100).toByte()
        registers[1] = ((value % 100) / 10).toByte()
        registers[2] = (value % 10).toByte()
    }

    private fun convertByteToASCII(rX: Int, rY: Int) {
        val value = registers[rX].toInt() and 0xFF
        if (value > 0x0F) throw IllegalArgumentException("Value out of range")
        registers[rY] = (value + '0'.toInt()).toByte()
    }

    private fun draw(rX: Int, rY: Int, rZ: Int) {
        val char = registers[rX].toInt().toChar()
        display.draw(char, rY, rZ)
    }
}

