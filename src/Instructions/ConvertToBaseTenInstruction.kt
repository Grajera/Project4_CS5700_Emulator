package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.Registry_Handlers.AManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R

class ConvertToBaseTenInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {
    lateinit var rx: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        rx = r[rxIndex]
    }

    public override fun performOperation() {
        val address = byteArrayToInt(a.read())

        val value = rx.read()[0].toInt()

        val hundreds = value / 100
        val tens = (value % 100) / 10
        val ones = value % 10

        val mByteArray = m.read()
        val isUsingROM = mByteArray[0].toInt() != 0

        if (isUsingROM) {
            RomManager.getRom()!!.write(address, hundreds.toByte())
            RomManager.getRom()!!.write(address + 1, tens.toByte())
            RomManager.getRom()!!.write(address + 2, ones.toByte())
        } else {
            RAM.write(address, hundreds.toByte())
            RAM.write(address + 1, tens.toByte())
            RAM.write(address + 2, ones.toByte())
        }
    }
}