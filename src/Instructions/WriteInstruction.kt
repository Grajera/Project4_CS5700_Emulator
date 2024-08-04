package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class WriteInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {
    lateinit var rx: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
    }

    public override fun performOperation() {


        val addressBytes = a.read()
        val address = byteArrayToInt(addressBytes)

        val mByteArray = m.read()
        val isUsingROM = mByteArray[0].toInt() != 0

        val value = rx.read()[0]

        if (isUsingROM) {
            RomManager.getRom()?.let {
                it.write(address, value)
                println("Written value ${value.toUByte()} to ROM address $address")
            } ?: println("ROM is not initialized")
        } else {
            RAM.write(address, value)
        }
    }
}