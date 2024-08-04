package com.emulator

fun main(args: Array<String>) {
    val computerEmulator = Emulator()
    if (args.isNotEmpty()) {
        computerEmulator.run(args[0])
    }
    else {
        computerEmulator.run()
    }
}