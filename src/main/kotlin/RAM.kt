package org.example

import Memory

class RAM(size: Int) : Memory(size) {
    override fun read(address: Int): Byte {
        return memory[address]
    }

    override fun write(address: Int, value: Byte) {
        memory[address] = value
    }
}
