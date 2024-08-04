package com.emulator

fun main(args: Array<String>) {
    val emulator = Emulator()
    // Determine if a ROM file path was provided as an argument
    val romFilePath = args.getOrNull(0) // Get the first argument or null if none
    // Run the emulator with the provided ROM file path or without one
    emulator.run(romFilePath)
}
