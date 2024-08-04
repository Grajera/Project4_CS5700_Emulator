package Instructions

import com.emulator.byteArrayToInt
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.R

class ReadInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var rx: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
    }

    public override fun performOperation() {
        val mByteArray = m.read()
        val isUsingROM = mByteArray[0].toInt() != 0

        val addressBytes = a.read()
        val address = byteArrayToInt(addressBytes)

        val value = if (isUsingROM) {
            RomManager.getRom()!!.read(address)
        } else {
            RAM.read(address)
        }

        rx.operate(byteArrayOf(value))

    }
}