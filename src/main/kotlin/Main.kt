package org.example

import Display
import Keyboard

fun main() {
    val rom = ROM(4096) // Initialize ROM with 4KB size
    val ram = RAM(4096) // Initialize RAM with 4KB size
    val display = Display()
    val keyboard = Keyboard()
    val cpu = CPU(rom, ram, display, keyboard)

    // Load a program into ROM (example)
    // rom.loadProgram(byteArrayOf(...))

    cpu.run() // Start the emulator
}
