package org.example

class D5700 {
    private val cpu = CPU()
    private val ram = ByteArray(4096)
    private val rom = ByteArray(4096)

    fun loadProgram(program: ByteArray) {
        require(program.size <= rom.size) { "Program size exceeds ROM capacity." }
        program.copyInto(rom)
    }

    fun run() {
        cpu.run()
    }

    fun pause() {
        cpu.pause()
    }

    fun reset() {
        cpu.reset()
    }
}