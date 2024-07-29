package org.example

class CPU {
    private val registers = ByteArray(8)
    private var programCounter = 0
    private var address = 0
    private var memoryFlag = 0
    private val timer = Timer()
    private val ram = ByteArray(4096)
    private val rom = ByteArray(4096)
    private val display = Display()

    init {
        timer.start()
    }

    fun run() {
        while (true) {
            fetch()
            decode()
            execute()
            updateProgramCounter()
        }
    }

    fun pause() {
        // Implementation to pause the CPU
    }

    fun reset() {
        programCounter = 0
        address = 0
        memoryFlag = 0
        timer.setTimer(0)
        registers.fill(0)
    }

    private fun fetch() {
        // Fetch 2 bytes from ROM starting at PC
        val instruction = (rom[programCounter].toInt() shl 8) or rom[programCounter + 1].toInt()
        decode(instruction)
    }

    private fun decode(instruction: Int) {
        // Decode instruction
        val opcode = (instruction ushr 12) and 0xF
        val param1 = (instruction ushr 8) and 0xF
        val param2 = (instruction ushr 4) and 0xF
        val param3 = instruction and 0xF

        when (opcode) {
            0 -> store(param1, param2)   // STORE
            1 -> add(param1, param2, param3)   // ADD
            2 -> sub(param1, param2, param3)   // SUB
            3 -> read(param1)   // READ
            4 -> write(param1)   // WRITE
            5 -> jump(param1)   // JUMP
            6 -> readKeyboard(param1)   // READ_KEYBOARD
            7 -> switchMemory()   // SWITCH_MEMORY
            8 -> skipEqual(param1, param2)   // SKIP_EQUAL
            9 -> skipNotEqual(param1, param2)   // SKIP_NOT_EQUAL
            10 -> setA(param1)   // SET_A
            11 -> setT(param1)   // SET_T
            12 -> readT(param1)   // READ_T
            13 -> convertToBase10(param1)   // CONVERT_TO_BASE_10
            14 -> convertByteToASCII(param1, param2)   // CONVERT_BYTE_TO_ASCII
            15 -> draw(param1, param2, param3)   // DRAW
            else -> throw IllegalArgumentException("Invalid opcode: $opcode")
        }
    }

    private fun execute() {
        // Execution logic specific to the decoded instruction
    }

    private fun updateProgramCounter() {
        // Increment PC based on instruction type
        // For simplicity, assume all instructions increment PC by 2
        programCounter += 2
    }

    // Instructions implementation
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
        val value = if (memoryFlag == 0) ram[address] else rom[address]
        registers[rX] = value
    }

    private fun write(rX: Int) {
        if (memoryFlag == 0) {
            ram[address] = registers[rX]
        } else {
            // Future support for writable ROM
            // This is a placeholder for the future
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

